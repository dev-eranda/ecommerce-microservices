package com.eranda.ecommerce.order;

import com.eranda.ecommerce.customer.CustomerClient;
import com.eranda.ecommerce.exception.BusinessException;
import com.eranda.ecommerce.kafka.OrderConfirmation;
import com.eranda.ecommerce.kafka.OrderProducer;
import com.eranda.ecommerce.orderline.OrderLineRequest;
import com.eranda.ecommerce.orderline.OrderLineService;
import com.eranda.ecommerce.payment.PaymentClient;
import com.eranda.ecommerce.payment.PaymentRequest;
import com.eranda.ecommerce.product.ProductClient;
import com.eranda.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        // check customer => openFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
            .orElseThrow(() -> new BusinessException("Cannot create order:: No customer found"));

        // purchase product => product microservice (RestTemplate)
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        // persist-order
        var order = this.repository.save(mapper.toOrder(request));

        // persist order lines
        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // start payment process
        var paymentRequest = new PaymentRequest(
            request.amount(),
            request.paymentMethod(),
            order.getId(),
            order.getReference(),
            customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation to notification-ms (Kafka)
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
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %s not found", orderId)));
    }
}
