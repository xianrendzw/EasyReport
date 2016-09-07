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
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (request.getAttribute(Constants.CONTEXT_PATH) == null) {
            request.setAttribute(Constants.CONTEXT_PATH, req.getContextPath());
        }
        if (request.getAttribute(Constants.VERSION) == null) {
            request.setAttribute(Constants.VERSION, Constants.VERSION_CODE);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
