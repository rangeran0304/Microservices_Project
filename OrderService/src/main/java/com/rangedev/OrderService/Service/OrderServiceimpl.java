package com.rangedev.OrderService.Service;

import com.rangedev.OrderService.Entity.Order;
import com.rangedev.OrderService.Exception.CustomException;
import com.rangedev.OrderService.Model.OrderRequest;
import com.rangedev.OrderService.Model.OrderResponse;
import com.rangedev.OrderService.Repository.OrderRepository;
import com.rangedev.OrderService.external.client.PaymentService;
import com.rangedev.OrderService.external.client.ProductService;
import com.rangedev.OrderService.external.request.PaymentRequest;
import com.rangedev.OrderService.external.response.PaymentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceimpl implements  OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // create the order entity and return the order_id
        // Product Services -> Block Product(Reduce the Quantity)
        // Payment Service -> successs -> complete; else->
        // Cancel
        log.info("placing order...");
        productService.reducequantity(orderRequest.getProductId(),orderRequest.getQuantity());
        Order order = Order.builder().
                quantity(orderRequest.getQuantity()).
                amount(orderRequest.getTotalAmount()).
                orderStatus("CREATED").
                productId(orderRequest.getProductId()).
                orderDate(Instant.now()).
                build();
        order = orderRepository.save(order);
        log.info("Calling Payment Service to complete payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentmode())
                .amount(order.getAmount())
                .build();
        String orderStatus = null;
        try{
            paymentService.dopayment(paymentRequest);
            log.info("Payment done successfully");
            orderStatus = "PLACED";
        } catch (Exception e){
            log.info("Error occurred in payment");
            orderStatus = "PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order created with order id {}", order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Getting order details for Order Id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new CustomException("Order not found for order Id:" + orderId,
                        "NOT_FOUND"
                        ,404));
        log.info("Invoking Product service to fetch the product of id:{}",order.getProductId());
        OrderResponse.ProductDetails productDetails
                = restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(), OrderResponse.ProductDetails.class);
        log.info("Getting payment info from payment service");
        PaymentResponse paymentResponse
                =restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/"+orderId,PaymentResponse.class);

        OrderResponse orderResponse = OrderResponse.builder()
                .orderDate(order.getOrderDate())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .productDetails(productDetails)
                .paymentDetails(paymentResponse)
                .build();
        return orderResponse;
    }
}
