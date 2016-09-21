package com.easytoolsoft.easyreport.web.spring;

import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC全局异常处理器
 *
 * @author Tom Deng
 */
@Slf4j
@Component("handlerExceptionResolver")
public class GlobalExceptionHandler implements HandlerExceptionResolver {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        String detailMessage = (ex.getCause() == null ? ex.getMessage() : ex.getCause().getMessage());
        JsonResult<String> result = new JsonResult<>(false, StringEscapeUtils.escapeHtml4(detailMessage));
        if (ex instanceof UnauthorizedException) {
            result.setMsg("对不起，您没有权限，不能访问！");
        }
        try {
            String str = mapper.writeValueAsString(result);
            log.error(str, ex);
            response.getOutputStream().write(str.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ModelAndView();
    }
}
