package com.eranda.ecommerce.payment;

import com.eranda.ecommerce.customer.CustomerResponse;
import com.eranda.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
