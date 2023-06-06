package com.rangedev.OrderService.Controller;


import com.rangedev.OrderService.Model.OrderRequest;
import com.rangedev.OrderService.Model.OrderResponse;
import com.rangedev.OrderService.Service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
    @Autowired
    private OrderService orderservice;

    @PreAuthorize("hasAuthority('Customer')")
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long orderId = orderservice.placeOrder(orderRequest);
        log.info("Order Id: {}", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);

    }
    @PreAuthorize("hasAuthority('Customer') || hasAuthority('Admin')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){
        OrderResponse orderResponse = orderservice.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse,HttpStatus.OK);

    }
}
