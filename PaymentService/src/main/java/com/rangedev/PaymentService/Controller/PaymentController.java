package com.rangedev.PaymentService.Controller;

import com.rangedev.PaymentService.Model.PaymentRequest;
import com.rangedev.PaymentService.Model.PaymentResponse;
import com.rangedev.PaymentService.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentservice;

    @PostMapping
    public ResponseEntity<Long> dopayment(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(
                paymentservice.dopayment(paymentRequest),
                HttpStatus.OK
        );

    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable Long orderId){
        return new ResponseEntity<>(paymentservice.getPaymentDetailsByOrderId(orderId)
        ,HttpStatus.OK);

    }
}
