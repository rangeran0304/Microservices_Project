package com.rangedev.OrderService.external.client;

import com.rangedev.OrderService.Exception.CustomException;
import com.rangedev.OrderService.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PostMapping
    public ResponseEntity<Long> dopayment(@RequestBody PaymentRequest paymentRequest);


    default void fallback(Exception e){
        throw new CustomException("Payment Service is not availible",
                "UNAVAILIBLE",
                500);
    }
    }
