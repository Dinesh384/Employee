package com.org.employee.exception;

import com.org.employee.entity.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = FieldNotFoundException.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError error = new ApiError(status, "Please fill all the required fields", "filed cannot be null");
        return new ResponseEntity<ApiError>(error, status);
    }
}
