package com.rangedev.PaymentService.Service;

import com.rangedev.PaymentService.Model.PaymentRequest;
import com.rangedev.PaymentService.Model.PaymentResponse;

public interface PaymentService {
    long dopayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(Long orderId);
}
