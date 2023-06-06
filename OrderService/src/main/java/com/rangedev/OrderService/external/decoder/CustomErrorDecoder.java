package com.rangedev.OrderService.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangedev.OrderService.Exception.CustomException;
import com.rangedev.OrderService.external.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("::{}",response.request().url());
        log.info("::{}",response.request().headers());
        try{
            ErrorResponse errorresponse = objectMapper.readValue(response.body().asInputStream(),
                    ErrorResponse.class);
            return new CustomException(errorresponse.getErrorMessage(),
                    errorresponse.getErrorCode(),
                    response.status());
        } catch (IOException e){
            throw new CustomException("Internal Server Error",
                    "INTERNAL_SERVER_ERROR",
                   500);

        }

    }
}
