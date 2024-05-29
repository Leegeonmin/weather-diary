package com.zerobase.weather.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    //요청 데이터 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occurred", e);

        BindingResult bindingResult = e.getBindingResult();
        FieldError error = bindingResult.getFieldError();
        String fieldError = String.format("Field: %s, Error: %s", error.getField(), error.getDefaultMessage());

        return new ErrorResponse(ErrorCode.INVALID_REQUEST,
                ErrorCode.INVALID_REQUEST.getDescription(),
                fieldError);
    }
    //자주 발생되는 오류
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException is occurred", e);
        return new ErrorResponse(ErrorCode.INVALID_REQUEST, ErrorCode.INVALID_REQUEST.getDescription());
    }

    @ResponseStatus
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error("Exception is occurred", e);
        return new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
