package com.tony.exceptionhandler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ProductControllerExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ProductControllerExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeException(RuntimeException e) {


        logger.error("RuntimeException occurred in ProductController：\n ", e.getStackTrace());
//        e.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("RuntimeException occurred in ProductController：\n" + e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> iOException(IOException e) {


        logger.error("IOException occurred in ProductController：\n ", e.getStackTrace());
//        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("IOException occurred in ProductController：\n" + e.getMessage());
    }

}
