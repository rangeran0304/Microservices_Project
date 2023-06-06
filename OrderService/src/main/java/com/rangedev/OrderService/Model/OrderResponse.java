package com.rangedev.OrderService.Model;

import com.rangedev.OrderService.external.response.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private long orderId;
    private Instant orderDate;
    private String orderStatus;
    private long amount;
    private ProductDetails productDetails;
    private PaymentResponse paymentDetails;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetails {
        private String productName;
        private long productId;
        private long price;
    }

}


