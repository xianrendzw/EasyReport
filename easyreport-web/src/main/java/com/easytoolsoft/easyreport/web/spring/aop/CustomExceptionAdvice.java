package com.easytoolsoft.easyreport.web.spring.aop;

import com.easytoolsoft.easyreport.support.aop.ExceptionAdvice;
import com.easytoolsoft.easyreport.support.enums.SystemErrorCode;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
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
public class CustomExceptionAdvice extends ExceptionAdvice {
    /**
     * 访问拒绝
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult handleException(final AccessDeniedException e) {
        log.error(HttpStatus.FORBIDDEN.getReasonPhrase(), e);
        return ResponseResult.failure(SystemErrorCode.FORBIDDEN, e);
    }

    /**
     * 认证异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult handleException(final AuthenticationException e) {
        log.error(HttpStatus.UNAUTHORIZED.getReasonPhrase(), e);
        return ResponseResult.failure(SystemErrorCode.UNAUTHORIZED, e);
    }
}