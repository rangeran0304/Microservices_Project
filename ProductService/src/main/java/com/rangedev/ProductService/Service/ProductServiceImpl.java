package com.rangedev.ProductService.Service;

import com.rangedev.ProductService.Entity.Product;
import com.rangedev.ProductService.Exception.ProductServiceCustomException;
import com.rangedev.ProductService.Model.ProductRequest;
import com.rangedev.ProductService.Model.ProductResponse;
import com.rangedev.ProductService.Repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productrquest) {
        log.info("adding product...");
        Product product = Product.builder().
                productName(productrquest.getName()).
                quantity(productrquest.getQuantity()).
                price(productrquest.getPrice()).build();
        productRepository.save(product);
        log.info("product added");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("getting the product of: {}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceCustomException("can not find the specified product","Product Not Found"));
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product,response);
        return response;
    }
}
