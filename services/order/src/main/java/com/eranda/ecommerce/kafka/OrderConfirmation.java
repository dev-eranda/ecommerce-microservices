package com.eranda.ecommerce.kafka;

import com.eranda.ecommerce.customer.CustomerResponse;
import com.eranda.ecommerce.order.PaymentMethod;
import com.eranda.ecommerce.product.PurchaseResponse;

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
