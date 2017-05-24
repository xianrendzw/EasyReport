package com.easytoolsoft.easyreport.membership.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ajax shiro form auth处理类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(AjaxFormAuthenticationFilter.class);

    @Override
    protected boolean onAccessDenied(final ServletRequest request, final ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                // allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                    "Authentication url [" + getLoginUrl() + "]");
            }

            final HttpServletRequest req = (HttpServletRequest)request;
            final HttpServletResponse res = (HttpServletResponse)response;

            // 如果是ajax请求响应头会有，x-requested-with 在响应头设置session状态
            if (req.getHeader("x-requested-with") != null
                && "XMLHttpRequest".equalsIgnoreCase(req.getHeader("x-requested-with"))) {
                res.setHeader("sessionstatus", "timeout");
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }
}
