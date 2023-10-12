package com.crust.backendproject.controllers;

import com.crust.backendproject.dto.response.ApiResponse;
import com.crust.backendproject.dto.response.MessageResponse;
import com.crust.backendproject.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException genericException,
                                                          HttpServletRequest httpServletRequest) {
        ApiResponse errorResponse = ApiResponse.builder().isSuccessful(false)
                .data(MessageResponse.builder().message((genericException.getMessage())).build())
                .status(HttpStatus.BAD_REQUEST.value()).path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest httpServletRequest) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }
        ApiResponse errorResponse = ApiResponse.builder()
                .isSuccessful(false)
                .data(errorMessages)
                .status(HttpStatus.BAD_REQUEST.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleValidationException(ConstraintViolationException ex,
                                                                 HttpServletRequest httpServletRequest) {
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> fieldError : ex.getConstraintViolations()) {
            errorMessages.add(fieldError.getMessage());
        }
        ApiResponse errorResponse = ApiResponse.builder()
                .isSuccessful(false)
                .data(errorMessages)
                .status(HttpStatus.BAD_REQUEST.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
                                                                                 HttpServletRequest httpServletRequest) {
        String message = "Value: '" + exception.getValue() + "' incorrect datatype given for " + exception.getParameter().getParameterName() + " expected datatype: " + Objects.requireNonNull(exception.getRequiredType()).getSimpleName();
        ApiResponse errorResponse = ApiResponse.builder().isSuccessful(false)
                .data(MessageResponse.builder().message((message)).build())
                .status(HttpStatus.BAD_REQUEST.value()).path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex, HttpServletRequest httpServletRequest) {

        log.error("GlobalExceptionHandler | handleGenericException" + ex.getMessage());
        ex.printStackTrace();

        ApiResponse errorResponse = ApiResponse.builder().isSuccessful(false)
                .data(MessageResponse.builder().message("Internal server error").build())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now()).build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
