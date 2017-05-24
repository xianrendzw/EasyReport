package com.easytoolsoft.easyreport.membership.shiro.security;

import javax.annotation.Resource;

import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.support.security.MembershipFacade;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 系统模块服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private MembershipFacade<User> membershipFacade;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        final String account = (String)principals.getPrimaryPrincipal();
        final User user = this.membershipFacade.getUser(account);

        final SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(this.membershipFacade.getRoleSet(user.getRoles()));
        authorizationInfo.setStringPermissions(this.membershipFacade.getPermissionSet(user.getRoles()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token)
        throws AuthenticationException {
        final String account = (String)token.getPrincipal();
        final User user = this.membershipFacade.getUser(account);

        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getStatus() == 0) {
            throw new LockedAccountException();
        }

        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        return new SimpleAuthenticationInfo(
            user.getAccount(), user.getPassword(),
            ByteSource.Util.bytes(user.getCredentialsSalt()),
            getName());
    }

    @Override
    public void clearCachedAuthorizationInfo(final PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(final PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(final PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
