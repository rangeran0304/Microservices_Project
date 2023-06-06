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

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce quantity {} for product {}", quantity, productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductServiceCustomException(
                        "Given product id can not found",
                        "PRODUCT_NOT_FOUND"));
        if (product.getQuantity() < quantity){
            throw new ProductServiceCustomException(
                    "Product doesn't have enough quantity",
                    "INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product quantity updated");
    }
}
