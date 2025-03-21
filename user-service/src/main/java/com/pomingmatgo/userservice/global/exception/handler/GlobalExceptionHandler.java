package com.pomingmatgo.userservice.global.exception.handler;

import com.pomingmatgo.userservice.global.exception.BusinessException;
import com.pomingmatgo.userservice.global.exception.ErrorCode;
import com.pomingmatgo.userservice.global.exception.SystemException;
import com.pomingmatgo.userservice.global.exception.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDto> handleBindException(BindException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ErrorCode.INVALID_FORM_INPUT);
        return ResponseEntity
                .status(errorResponse.getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getErrorCode());
        return ResponseEntity
                .status(e.getErrorCode().getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(SystemException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getErrorCode());
        return ResponseEntity
                .status(e.getErrorCode().getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ErrorCode.SYSTEM_ERROR);
        log.error("에러 발생: "+e.getClass() + ' ' + e.getMessage());
        return ResponseEntity
                .status(errorResponse.getHttpStatus())
                .body(errorResponse);
    }
}
