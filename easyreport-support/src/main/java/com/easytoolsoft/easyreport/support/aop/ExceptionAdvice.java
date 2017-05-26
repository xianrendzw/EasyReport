package com.easytoolsoft.easyreport.support.aop;

import java.sql.SQLException;

import javax.validation.ValidationException;

import com.easytoolsoft.easyreport.support.enums.SystemErrorCode;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理器
 *
 * @author Tom Deng
 * @date 2017-04-11
 **/
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionAdvice {
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        return ResponseResult.failure(SystemErrorCode.HTTP_MESSAGE_NOT_READABLE, e);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseResult handleValidationException(final ValidationException e) {
        log.error("ValidationException", e);
        return ResponseResult.failure(SystemErrorCode.DATA_VALIDATION_FAILURE, e);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseResult handleBindException(final BindException e) {
        log.error("BindException", e);
        return ResponseResult.failure(SystemErrorCode.DATA_BIND_VALIDATION_FAILURE, e);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public ResponseResult handleSQLException(final SQLException e) {
        log.error("SQLException", e);
        return ResponseResult.failure(SystemErrorCode.SQL_EXECUTE_FAILURE, e);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        return ResponseResult.failure(SystemErrorCode.DATA_BIND_VALIDATION_FAILURE, e);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), e);
        return ResponseResult.failure(SystemErrorCode.METHOD_NOT_ALLOWED, e);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseResult handleHttpMediaTypeNotSupportedException(final Exception e) {
        log.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), e);
        return ResponseResult.failure(SystemErrorCode.UNSUPPORTED_MEDIA_TYPE, e);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(final Exception e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return ResponseResult.failure(SystemErrorCode.INTERNAL_SERVER_ERROR, e);
    }
}