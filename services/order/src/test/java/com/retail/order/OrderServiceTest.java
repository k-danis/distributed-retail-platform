package com.retail.order;

import com.retail.customer.CustomerClient;
import com.retail.customer.CustomerResponse;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CustomerClient customerClient;
    @Mock
    private ProductClient productClient;
    @Mock
    private OrderRepository repository;
    @Mock
    private OrderMapper mapper;
    @Mock
    private OrderLineService orderLineService;
    @Mock
    private OrderProducer orderProducer;
    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private OrderService service;

    private static final CustomerResponse CUSTOMER =
            new CustomerResponse("cust-1", "Ada", "Lovelace", "ada@example.com");

    private OrderRequest request(List<PurchaseRequest> products) {
        return new OrderRequest(
                null,
                "ORDER-REF-1",
                BigDecimal.valueOf(100),
                PaymentMethod.CREDIT_CARD,
                "cust-1",
                products
        );
    }

    private Order savedOrder() {
        return Order.builder()
                .id(42)
                .reference("ORDER-REF-1")
                .totalAmount(BigDecimal.valueOf(100))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .customerId("cust-1")
                .build();
    }

    @Test
    @DisplayName("create() rejects the order when the customer does not exist")
    void createFailsWhenCustomerMissing() {
        when(customerClient.findCustomerById("cust-1")).thenReturn(Optional.empty());

        var request = request(List.of(new PurchaseRequest(1, 2)));

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("cust-1");

        verifyNoInteractions(productClient, repository, paymentClient, orderProducer);
    }

    @Test
    @DisplayName("create() reserves stock, persists the order, requests payment and publishes a confirmation")
    void createRunsTheFullFlow() {
        var products = List.of(new PurchaseRequest(1, 2));
        var request = request(products);
        var order = savedOrder();

        when(customerClient.findCustomerById("cust-1")).thenReturn(Optional.of(CUSTOMER));
        when(productClient.purchaseProducts(products)).thenReturn(List.of());
        when(mapper.toOrder(request)).thenReturn(order);
        when(repository.save(order)).thenReturn(order);

        var orderId = service.create(request);

        assertThat(orderId).isEqualTo(42);
        verify(productClient).purchaseProducts(products);
        verify(repository).save(order);
        verify(paymentClient).requestOrderPayment(any(PaymentRequest.class));
        verify(orderProducer).sendOrderConfirmation(any(OrderConfirmation.class));
    }

    @Test
    @DisplayName("create() saves one order line per requested product")
    void createSavesOneOrderLinePerProduct() {
        var products = List.of(new PurchaseRequest(1, 2), new PurchaseRequest(2, 1));
        var request = request(products);
        var order = savedOrder();

        when(customerClient.findCustomerById("cust-1")).thenReturn(Optional.of(CUSTOMER));
        when(productClient.purchaseProducts(products)).thenReturn(List.of());
        when(mapper.toOrder(request)).thenReturn(order);
        when(repository.save(order)).thenReturn(order);

        service.create(request);

        var captor = ArgumentCaptor.forClass(OrderLineRequest.class);
        verify(orderLineService, times(2)).saveOrderLine(captor.capture());
        assertThat(captor.getAllValues())
                .extracting(OrderLineRequest::productId)
                .containsExactly(1, 2);
        assertThat(captor.getAllValues())
                .allMatch(line -> line.orderId().equals(42));
    }

    @Test
    @DisplayName("create() sends the payment request with the order reference and customer")
    void createSendsCorrectPaymentRequest() {
        var products = List.of(new PurchaseRequest(1, 2));
        var request = request(products);
        var order = savedOrder();

        when(customerClient.findCustomerById("cust-1")).thenReturn(Optional.of(CUSTOMER));
        when(productClient.purchaseProducts(products)).thenReturn(List.of());
        when(mapper.toOrder(request)).thenReturn(order);
        when(repository.save(order)).thenReturn(order);

        service.create(request);

        var captor = ArgumentCaptor.forClass(PaymentRequest.class);
        verify(paymentClient).requestOrderPayment(captor.capture());
        assertThat(captor.getValue().orderId()).isEqualTo(42);
        assertThat(captor.getValue().orderReference()).isEqualTo("ORDER-REF-1");
        assertThat(captor.getValue().customer()).isEqualTo(CUSTOMER);
    }

    @Test
    @DisplayName("findById() throws when the order does not exist")
    void findByIdThrowsWhenMissing() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(mapper, never()).fromOrder(any(Order.class));
    }
}
