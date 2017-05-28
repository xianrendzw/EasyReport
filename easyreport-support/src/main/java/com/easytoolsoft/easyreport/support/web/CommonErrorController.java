package com.easytoolsoft.easyreport.support.web;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easytoolsoft.easyreport.support.enums.SystemErrorCode;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CommonErrorController extends AbstractErrorController {
    private ErrorProperties errorProperties;

    public CommonErrorController(final ErrorAttributes errorAttributes,
                                 ErrorProperties errorProperties) {
        super(errorAttributes);
        this.errorProperties = errorProperties;
    }

    @ResponseBody
    @RequestMapping(value = "/401")
    public ResponseResult error401(final HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> responseEntity = this.getResponseEntity(request);
        return ResponseResult.failure(SystemErrorCode.UNAUTHORIZED, responseEntity.getBody());
    }

    @RequestMapping(value = "/401", produces = "text/html")
    public ModelAndView error401Html(final HttpServletRequest request,
                                     final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/401", model);
    }

    @ResponseBody
    @RequestMapping(value = "/403")
    public ResponseResult error403(final HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> responseEntity = this.getResponseEntity(request);
        return ResponseResult.failure(SystemErrorCode.FORBIDDEN, responseEntity.getBody());
    }

    @RequestMapping(value = "/403", produces = "text/html")
    public ModelAndView error403Html(final HttpServletRequest request,
                                     final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/403", model);
    }

    @ResponseBody
    @RequestMapping(value = "/404")
    public ResponseResult error404(final HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> responseEntity = this.getResponseEntity(request);
        return ResponseResult.failure(SystemErrorCode.NOT_FOUND, responseEntity.getBody());
    }

    @RequestMapping(value = "/404", produces = "text/html")
    public ModelAndView error404Html(final HttpServletRequest request,
                                     final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/404", model);
    }

    @ResponseBody
    @RequestMapping
    public ResponseResult error(final HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> responseEntity = this.getResponseEntity(request);
        return ResponseResult.failure(
            SystemErrorCode.valueOf(responseEntity.getStatusCode().value()),
            responseEntity.getBody()
        );
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(final HttpServletRequest request,
                                  final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/global", model);
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    protected Map<String, Object> getViewModel(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpStatus status = getStatus(request);
        response.setStatus(status.value());
        return this.getErrorModel(request);
    }

    protected ResponseEntity<Map<String, Object>> getResponseEntity(final HttpServletRequest request) {
        final HttpStatus status = getStatus(request);
        final Map<String, Object> model = this.getErrorModel(request);
        return new ResponseEntity<>(model, status);
    }

    protected Map<String, Object> getErrorModel(final HttpServletRequest request) {
        final HttpStatus status = getStatus(request);
        final Map<String, Object> model = Collections.unmodifiableMap(
            getErrorAttributes(request, isIncludeStackTrace(request)));
        log.error("path:{},status:{},trace:{}", model.get("path"), status.value(), model.get("trace"));
        return model;
    }

    protected boolean isIncludeStackTrace(HttpServletRequest request) {
        IncludeStacktrace include = this.errorProperties.getIncludeStacktrace();
        if (include == IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }
}
