package com.eranda.ecommerce.orderline;

import com.eranda.ecommerce.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> order(Order order);

    List<OrderLine> findAllByOrderId(Integer orderId);
}
