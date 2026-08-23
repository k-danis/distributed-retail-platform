package com.retail.product;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.CacheManager;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the Redis cache layer against a real Redis and a real PostgreSQL instance.
 * Mocks cannot prove that caching actually happens, or that a write commits — both are
 * behaviours of the infrastructure, not of the service class in isolation.
 */
@SpringBootTest
@Testcontainers
class ProductCacheIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Container
    @ServiceConnection(name = "redis")
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
            .withExposedPorts(6379);

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CacheManager cacheManager;

    private Integer productId;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("products").clear();
        // The Flyway migrations seed the catalogue, so any existing product works here.
        productId = repository.findAll().getFirst().getId();
    }

    @Test
    @DisplayName("findById() stores the product in Redis on first read")
    void findByIdPopulatesCache() {
        assertThat(cacheManager.getCache("products").get(productId)).isNull();

        service.findById(productId);

        Awaitility.await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() ->
                        assertThat(cacheManager.getCache("products").get(productId)).isNotNull());
    }

    @Test
    @DisplayName("purchase() commits the new stock level to the database")
    void purchasePersistsStockChange() {
        var before = repository.findById(productId).orElseThrow().getAvailableQuantity();

        service.purchase(List.of(new ProductPurchaseRequest(productId, 2)));

        var after = repository.findById(productId).orElseThrow().getAvailableQuantity();
        assertThat(after).isEqualTo(before - 2);
    }

    @Test
    @DisplayName("purchase() evicts the cached product so reads do not return stale stock")
    void purchaseEvictsCache() {
        service.findById(productId);
        Awaitility.await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() ->
                        assertThat(cacheManager.getCache("products").get(productId)).isNotNull());

        service.purchase(List.of(new ProductPurchaseRequest(productId, 1)));

        Awaitility.await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() ->
                        assertThat(cacheManager.getCache("products").get(productId)).isNull());
    }
}
