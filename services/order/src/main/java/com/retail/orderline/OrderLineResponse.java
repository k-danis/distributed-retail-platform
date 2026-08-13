package com.retail.orderline;

public record OrderLineResponse(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity
) {
}
