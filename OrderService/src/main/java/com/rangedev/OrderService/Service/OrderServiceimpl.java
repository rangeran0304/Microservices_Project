package com.rangedev.OrderService.Service;

import com.rangedev.OrderService.Entity.Order;
import com.rangedev.OrderService.Model.OrderRequest;
import com.rangedev.OrderService.Repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceimpl implements  OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // create the order entity and return the order_id
        // Product Services -> Block Product(Reduce the Quantity)
        // Payment Service -> successs -> complete; else->
        // Cancel
        log.info("placing order...");
        Order order = Order.builder().
                quantity(orderRequest.getQuantity()).
                amount(orderRequest.getTotalAmount()).
                orderStatus("CREATED").
                productId(orderRequest.getProductId()).
                orderDate(Instant.now()).
                build();
        order = orderRepository.save(order);
        log.info("Order created with order id {}", order.getId());
        return order.getId();
    }
}
