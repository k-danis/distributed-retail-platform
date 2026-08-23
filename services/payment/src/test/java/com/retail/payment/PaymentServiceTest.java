package com.retail.payment;

import com.retail.notification.NotificationProducer;
import com.retail.notification.PaymentNotificationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;
    @Mock
    private PaymentMapper mapper;
    @Mock
    private NotificationProducer notificationProducer;

    @InjectMocks
    private PaymentService service;

    private static final Customer CUSTOMER =
            new Customer("cust-1", "Ada", "Lovelace", "ada@example.com");

    private PaymentRequest request() {
        return new PaymentRequest(
                null,
                BigDecimal.valueOf(250),
                PaymentMethod.CREDIT_CARD,
                42,
                "ORDER-REF-1",
                CUSTOMER
        );
    }

    @Test
    @DisplayName("create() persists the payment and returns its id")
    void createPersistsPayment() {
        var request = request();
        var mapped = Payment.builder().orderId(42).amount(BigDecimal.valueOf(250)).build();
        var saved = Payment.builder().id(7).orderId(42).amount(BigDecimal.valueOf(250)).build();

        when(mapper.toPayment(request)).thenReturn(mapped);
        when(repository.save(mapped)).thenReturn(saved);

        assertThat(service.create(request)).isEqualTo(7);
        verify(repository).save(mapped);
    }

    @Test
    @DisplayName("create() publishes a notification carrying the order reference and customer details")
    void createPublishesNotification() {
        var request = request();
        var mapped = Payment.builder().orderId(42).build();
        var saved = Payment.builder().id(7).orderId(42).build();

        when(mapper.toPayment(request)).thenReturn(mapped);
        when(repository.save(mapped)).thenReturn(saved);

        service.create(request);

        var captor = ArgumentCaptor.forClass(PaymentNotificationRequest.class);
        verify(notificationProducer).sendNotification(captor.capture());

        var notification = captor.getValue();
        assertThat(notification.orderReference()).isEqualTo("ORDER-REF-1");
        assertThat(notification.amount()).isEqualByComparingTo(BigDecimal.valueOf(250));
        assertThat(notification.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
        assertThat(notification.customerEmail()).isEqualTo("ada@example.com");
        assertThat(notification.customerFirstName()).isEqualTo("Ada");
        assertThat(notification.customerLastName()).isEqualTo("Lovelace");
    }
}