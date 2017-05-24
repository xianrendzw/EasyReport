package com.easytoolsoft.easyreport.membership.shiro.filter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.support.consts.UserAuthConsts;
import com.easytoolsoft.easyreport.support.security.MembershipFacade;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

/**
 * 自定义的Shiro Filter,用于处理用户是否登录
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public class MembershipFilter extends PathMatchingFilter {
    @Resource
    private MembershipFacade<User> membershipFacade;

    @Override
    protected boolean onPreHandle(final ServletRequest request, final ServletResponse response,
                                  final Object mappedValue)
        throws Exception {
        final String account = (String)SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(UserAuthConsts.CURRENT_USER, this.membershipFacade.getUser(account));
        return true;
    }
}
