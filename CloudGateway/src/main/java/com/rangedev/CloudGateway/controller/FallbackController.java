package com.rangedev.CloudGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/orderServiceFallBack")
    public String orderServiceFallBack(){
        return "Order Service Is Down!";
    }
    @GetMapping("/productServiceFallBack")
    public String productServiceFallBack(){
        return "Product Service Is Down!";
    }
    @GetMapping("/paymentServiceFallBack")
    public String paymentServiceFallBack(){
        return "Payment Service Is Down!";
    }
}
