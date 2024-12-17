package com.eranda.ecommerce.order;

import com.eranda.ecommerce.customer.CustomerClient;
import com.eranda.ecommerce.exception.BusinessException;
import com.eranda.ecommerce.orderline.OrderLineRequest;
import com.eranda.ecommerce.orderline.OrderLineService;
import com.eranda.ecommerce.product.ProductClient;
import com.eranda.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;


    public Integer createOrder(OrderRequest request) {
        // check customer => openFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
            .orElseThrow(() -> new BusinessException("Cannot create order:: No customer found"));

        // purchase product => product microservice (RestTemplate)
        this.productClient.purchaseProducts(request.products());

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

        // send the order confirmation to notification-ms (Kafka)
        return null;
    }
}
