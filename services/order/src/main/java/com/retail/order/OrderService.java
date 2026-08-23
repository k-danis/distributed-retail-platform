package com.retail.order;

import com.retail.customer.CustomerClient;
import com.retail.exception.BusinessException;
import com.retail.kafka.OrderConfirmation;
import com.retail.kafka.OrderProducer;
import com.retail.orderline.OrderLineRequest;
import com.retail.orderline.OrderLineService;
import com.retail.payment.PaymentClient;
import com.retail.payment.PaymentRequest;
import com.retail.product.ProductClient;
import com.retail.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public Integer create(@Valid OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order: no customer with id " + request.customerId()));

        var purchasedProducts = productClient.purchaseProducts(request.products());

        var order = repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("No order with id " + id));
    }
}
