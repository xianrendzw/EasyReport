package com.easytoolsoft.easyreport.support.web;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class ErrorController extends BasicErrorController {
    public ErrorController(final ErrorAttributes errorAttributes,
                           final ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    @RequestMapping(value = "/404", produces = "text/html")
    public ModelAndView error404Html(final HttpServletRequest request,
                                     final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/404", model);
    }

    @ResponseBody
    @RequestMapping(value = "/404")
    public ResponseEntity<Map<String, Object>> error404(final HttpServletRequest request) {
        return getResponseEntity(request);
    }

    @Override
    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(final HttpServletRequest request,
                                  final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/global", model);
    }

    @Override
    @ResponseBody
    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(final HttpServletRequest request) {
        return this.getResponseEntity(request);
    }

    protected Map<String, Object> getViewModel(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpStatus status = getStatus(request);
        final Map<String, Object> model = Collections.unmodifiableMap(
            getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        log.error("path:{},status:{},trace:{}", model.get("path"), status.value(), model.get("trace"));
        return model;
    }

    protected ResponseEntity<Map<String, Object>> getResponseEntity(final HttpServletRequest request) {
        final HttpStatus status = getStatus(request);
        final Map<String, Object> model = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        log.error("ath:{},status:{},trace:{}", model.get("path"), status.value(), model.get("trace"));
        return new ResponseEntity<>(model, status);
    }
}

