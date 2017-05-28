package com.easytoolsoft.easyreport.support.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.easytoolsoft.easyreport.support.consts.AppEnvConsts;

/**
 * ServletContext 初始化数据 Filter
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public class ContextInitDataFilter implements Filter {
    private String version = AppEnvConsts.VERSION;
    private String envName = AppEnvConsts.ENV_NAME;
    private String appName = AppEnvConsts.APP_NAME;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        this.appName = filterConfig.getInitParameter(AppEnvConsts.APP_NAME_ITEM);
        this.version = filterConfig.getInitParameter(AppEnvConsts.VERSION_ITEM);
        this.envName = filterConfig.getInitParameter(AppEnvConsts.ENV_NAME_ITEM);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
        throws IOException, ServletException {
        this.setAppEnvAttributes((HttpServletRequest)request);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private void setAppEnvAttributes(final HttpServletRequest request) {
        if (request.getAttribute(AppEnvConsts.CONTEXT_PATH) == null) {
            request.setAttribute(AppEnvConsts.CONTEXT_PATH, request.getContextPath());
        }
        if (request.getAttribute(AppEnvConsts.APP_NAME_ITEM) == null) {
            request.setAttribute(AppEnvConsts.APP_NAME_ITEM, this.appName);
            AppEnvConsts.setAppName(this.appName);
        }
        if (request.getAttribute(AppEnvConsts.VERSION_ITEM) == null) {
            request.setAttribute(AppEnvConsts.VERSION_ITEM, this.version);
            AppEnvConsts.setVersion(this.version);
        }
        if (request.getAttribute(AppEnvConsts.ENV_NAME_ITEM) == null) {
            request.setAttribute(AppEnvConsts.ENV_NAME_ITEM, this.envName);
            AppEnvConsts.setEnvName(this.envName);
        }
    }
}
