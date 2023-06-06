package com.rangedev.OrderService.Service;

import com.rangedev.OrderService.Model.OrderRequest;
import com.rangedev.OrderService.Model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
