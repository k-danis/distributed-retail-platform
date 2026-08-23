package com.retail.product;

import com.retail.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Transactional
    public Integer create(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }



    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public List<ProductPurchaseResponse> purchase(@Valid List<ProductPurchaseRequest> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        if (productIds.size() != productIds.stream().distinct().count()) {
            throw new ProductPurchaseException("Duplicate product IDs in purchase request");
        }

        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedRequest.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity())
                throw new ProductPurchaseException("Insufficient quantity for product with ID : " + productRequest.productId());

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }

    @Cacheable(value = "products", key = "#productId")
    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID : " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    @Transactional
    @CacheEvict(value = "products", key = "#id")
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Transactional
    @CacheEvict(value = "products", key = "#request.id()")
    public ProductResponse update(@Valid ProductRequest request) {
        var product = repository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID : " + request.id()));
        product.setAvailableQuantity(request.availableQuantity());
        product.setName(request.name());
        product.setPrice(request.price());
        product.setDescription(request.description());
        product.setCategory(
                Category.builder()
                        .id(request.categoryId())
                        .build()
        );

        return mapper.toProductResponse(repository.save(product));
    }
}
