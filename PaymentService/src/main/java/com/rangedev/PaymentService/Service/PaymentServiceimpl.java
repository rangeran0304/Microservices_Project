package com.rangedev.PaymentService.Service;

import com.rangedev.PaymentService.Entity.TransactionDetails;
import com.rangedev.PaymentService.Model.PaymentMode;
import com.rangedev.PaymentService.Model.PaymentRequest;
import com.rangedev.PaymentService.Model.PaymentResponse;
import com.rangedev.PaymentService.Repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceimpl implements PaymentService{
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public long dopayment(PaymentRequest paymentRequest) {
        log.info("recording payment details");
        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();
        transactionDetailsRepository.save(transactionDetails);
        log.info("Transanction recorded with Id:{}",transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(Long orderId) {
        log.info("Getting paymentInfo of Id: {}",orderId);
        TransactionDetails transactionDetails =
                transactionDetailsRepository.findByOrderId(orderId);
        log.info(transactionDetails);
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .orderId(orderId)
                .paymentId(transactionDetails.getId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate())
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .build();
        return paymentResponse;
    }
}
