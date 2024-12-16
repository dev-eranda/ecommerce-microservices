package com.eranda.ecommerce.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productId,
        String name,
        String Description,
        BigDecimal price,
        double quantity
) {
}
