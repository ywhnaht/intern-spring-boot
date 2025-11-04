package org.example.employeemanagement.exception;

import org.example.employeemanagement.entity.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handleUncategorizedException(Exception exception) {
        return createErrorResponse(ErrorCode.UNCATEGORIZED_EXCEPTION, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse(ErrorCode.INVALID_INPUT.getMessage());

        return createErrorResponse(ErrorCode.INVALID_INPUT, errorMessage);
    }

    private ResponseEntity<ApiResponse<?>> createErrorResponse(ErrorCode errorCode, String errorMessage) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.error(errorMessage, errorCode.name()));
    }
}
