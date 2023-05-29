package com.rangedev.ProductService.Exception;

import lombok.Data;

@Data
public class ProductServiceCustomException extends RuntimeException{
    private String errorcode;
    public  ProductServiceCustomException(String message, String errorcode){
        super(message);
        this.errorcode = errorcode;
    };

}
