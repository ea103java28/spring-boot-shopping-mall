package com.tony.exceptionhandler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class ShoppingMallExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ShoppingMallExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeException(RuntimeException e) {


        logger.error("RuntimeException ：\n ", e);
//        e.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("RuntimeException ：\n" + e);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> iOException(IOException e) {


        logger.error("IOException ：\n ", e);
//        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("IOException ：\n" + e);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> fileNotFoundException(IOException e) {


        logger.error("FileNotFoundException：\n ", e);
//        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("FileNotFoundException：\n" + e);
    }


}
