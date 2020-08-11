package com.pks.demo;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pks.demo.exception.ProductNotFoundException;
import com.pks.demo.exception.RestRequestException;
import com.pks.demo.model.RestException;
@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(value= {RestRequestException.class})
	public ResponseEntity<Object> handle(RestRequestException e){
	RestException restException=	new RestException(e.getMessage(), HttpStatus.BAD_REQUEST , LocalDateTime.now());
	return new ResponseEntity<Object>(restException,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value= {ProductNotFoundException.class})
	public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException e){
	RestException restException=	new RestException(e.getMessage(), HttpStatus.NOT_FOUND , LocalDateTime.now());
	return new ResponseEntity<Object>(restException,HttpStatus.NOT_FOUND);
	}

}
