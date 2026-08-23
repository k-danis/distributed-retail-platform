package com.retail.product;

import com.retail.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    private Product product(Integer id, double availableQuantity) {
        return Product.builder()
                .id(id)
                .name("Product " + id)
                .description("Description " + id)
                .availableQuantity(availableQuantity)
                .price(BigDecimal.TEN)
                .category(Category.builder().id(1).name("Category").description("Category description").build())
                .build();
    }

    @Test
    @DisplayName("purchase() reduces available quantity and persists the change")
    void purchaseReducesAvailableQuantity() {
        var stored = product(1, 10);
        when(repository.findAllByIdInOrderById(List.of(1))).thenReturn(List.of(stored));

        service.purchase(List.of(new ProductPurchaseRequest(1, 3)));

        assertThat(stored.getAvailableQuantity()).isEqualTo(7);
        verify(repository).save(stored);
    }

    @Test
    @DisplayName("purchase() handles several products in one request")
    void purchaseHandlesMultipleProducts() {
        var first = product(1, 10);
        var second = product(2, 5);
        when(repository.findAllByIdInOrderById(List.of(1, 2))).thenReturn(List.of(first, second));

        service.purchase(List.of(
                new ProductPurchaseRequest(1, 4),
                new ProductPurchaseRequest(2, 5)
        ));

        assertThat(first.getAvailableQuantity()).isEqualTo(6);
        assertThat(second.getAvailableQuantity()).isZero();
        verify(repository, times(2)).save(any(Product.class));
    }

    @Test
    @DisplayName("purchase() rejects a request containing the same product twice")
    void purchaseRejectsDuplicateProductIds() {
        var request = List.of(
                new ProductPurchaseRequest(1, 2),
                new ProductPurchaseRequest(1, 3)
        );

        assertThatThrownBy(() -> service.purchase(request))
                .isInstanceOf(ProductPurchaseException.class)
                .hasMessage("Duplicate product IDs in purchase request");

        verify(repository, never()).findAllByIdInOrderById(anyList());
        verify(repository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("purchase() fails when one of the requested products is missing")
    void purchaseFailsWhenProductMissing() {
        when(repository.findAllByIdInOrderById(List.of(1, 2))).thenReturn(List.of(product(1, 10)));

        var request = List.of(
                new ProductPurchaseRequest(1, 1),
                new ProductPurchaseRequest(2, 1)
        );

        assertThatThrownBy(() -> service.purchase(request))
                .isInstanceOf(ProductPurchaseException.class)
                .hasMessage("One or more products does not exist");

        verify(repository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("purchase() fails when the requested quantity exceeds stock")
    void purchaseFailsOnInsufficientQuantity() {
        when(repository.findAllByIdInOrderById(List.of(1))).thenReturn(List.of(product(1, 2)));

        var request = List.of(new ProductPurchaseRequest(1, 5));

        assertThatThrownBy(() -> service.purchase(request))
                .isInstanceOf(ProductPurchaseException.class)
                .hasMessageContaining("Insufficient quantity");

        verify(repository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("findById() returns the mapped product")
    void findByIdReturnsProduct() {
        var stored = product(1, 10);
        var expected = new ProductResponse(1, "Product 1", "Description 1", 10, BigDecimal.TEN, 1, "Category", "Category description");
        when(repository.findById(1)).thenReturn(Optional.of(stored));
        when(mapper.toProductResponse(stored)).thenReturn(expected);

        assertThat(service.findById(1)).isEqualTo(expected);
    }

    @Test
    @DisplayName("findById() throws when the product does not exist")
    void findByIdThrowsWhenMissing() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }
}
