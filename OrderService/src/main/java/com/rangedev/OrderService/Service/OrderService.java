package com.rangedev.OrderService.Service;

import com.rangedev.OrderService.Model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
