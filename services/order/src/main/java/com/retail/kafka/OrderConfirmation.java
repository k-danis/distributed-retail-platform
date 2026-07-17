package com.retail.kafka;

import com.retail.customer.CustomerResponse;
import com.retail.order.PaymentMethod;
import com.retail.product.PurchaseRequest;
import com.retail.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
