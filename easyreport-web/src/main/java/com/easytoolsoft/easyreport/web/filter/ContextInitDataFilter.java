package com.easytoolsoft.easyreport.web.filter;

import com.easytoolsoft.easyreport.web.common.Constants;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ServletContext 初始化数据 Filter
 */
public class ContextInitDataFilter implements Filter {
    private String version = Constants.VERSION_CODE;
    private String env = Constants.ENV_CODE;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.version = filterConfig.getInitParameter(Constants.VERSION);
        this.env = filterConfig.getInitParameter(Constants.ENV);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (request.getAttribute(Constants.CONTEXT_PATH) == null) {
            request.setAttribute(Constants.CONTEXT_PATH, req.getContextPath());
        }
        if (request.getAttribute(Constants.VERSION) == null) {
            request.setAttribute(Constants.VERSION, this.version);
        }
        if (request.getAttribute(Constants.ENV) == null) {
            request.setAttribute(Constants.ENV, this.env);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
