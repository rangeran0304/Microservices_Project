package com.rangedev.ProductService.Service;

import com.rangedev.ProductService.Model.ProductRequest;
import com.rangedev.ProductService.Model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productrquest);
    ProductResponse getProductById(long productId);
}
