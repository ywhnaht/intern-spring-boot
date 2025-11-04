package org.example.employeemanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR),

    // Authentication & Authorization Errors (1xxx)
    EMPLOYEE_EXISTS(1001, "Nhiên viên với email này đã tồn tại", HttpStatus.BAD_REQUEST),
    EMPLOYEE_NOT_FOUND(1002, "Không tìm thấy nhân viên", HttpStatus.NOT_FOUND),
    INVALID_INPUT(1003, "Đầu vào không hợp lệ", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
