package com.capstone.stayahead.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)              // when exceptions occur,GlobalExceptionHandler takes precedence
@ControllerAdvice                           // addressing exceptions in the app globally
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // GlobalExceptionHandler extends the ResponseEntityExceptionHandler
    // to inherits the built-in methods from it

    //1. When the user sens data that is not readable ,throw the error: handleHttpMessageNotReadableException

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request){
        //return super.handleHttpMessageNotReadable(ex, handler, status, request);

        // Prepare the custom message using MessageNotReadableException
        MessageNotReadableException messageNotReadableException = new MessageNotReadableException();

        // Store the various error response in a HashMap, to returning as part of the exception handling response
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error:", messageNotReadableException.getMessage());

        return  new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((err)->{
            String field = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            errors.put(field, message);
        });

        // store the various error responses in a HashMap, to returning as part of the exception handling response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error:", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> httpEntityNotFound(ResourceNotFoundException ex){


        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error:", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
