package com.easytoolsoft.easyreport.web.spring.security.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author zhiwei.deng
 * @date 2017-05-12
 **/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        request.setAttribute("spring.security.auth.status_code", HttpServletResponse.SC_UNAUTHORIZED);
        request.setAttribute("spring.security.auth.request_uri", request.getRequestURI());

        // 此处比较坑:由于spring security的http上下文与error controller异常处理的http上下文不一样，
        // 所以在使用cors(跨域)时在response中设置不了跨域相关属性(allow-orgion，allow-method等)
        // 因此用dispatcher.forward进行内部转发处理，使error controller与spring security上下文一致
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customError/401");
        dispatcher.forward(request, response);
    }
}