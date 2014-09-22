package org.easyframework.report.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxSessionTimeoutFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		//		if (!SsoClient.isHasLogin(req)) {
		//			// 如果是ajax请求响应头会有，x-requested-with；
		//			if (req.getHeader("x-requested-with") != null
		//					&& req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
		//				res.setStatus(911);// 表示session timeout
		//				return;
		//			}
		//		}

		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}
}
