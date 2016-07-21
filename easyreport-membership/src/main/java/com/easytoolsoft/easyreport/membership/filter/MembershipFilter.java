package com.easytoolsoft.easyreport.membership.filter;

import com.easytoolsoft.easyreport.membership.common.Constants;
import com.easytoolsoft.easyreport.membership.service.MembershipFacade;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义的Shiro Filter,用于处理用户是否登录
 *
 * @author Tom Deng
 */
public class MembershipFilter extends PathMatchingFilter {
    @Resource
    private MembershipFacade membershipFacade;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        String account = (String) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(Constants.CURRENT_USER, membershipFacade.getUser(account));
        return true;
    }
}
