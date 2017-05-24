package com.easytoolsoft.easyreport.web.config.shiro;

import java.util.Map;

import javax.servlet.Filter;

import com.easytoolsoft.easyreport.membership.shiro.filter.AjaxFormAuthenticationFilter;
import com.easytoolsoft.easyreport.membership.shiro.filter.MembershipFilter;
import com.easytoolsoft.easyreport.membership.shiro.security.MyShiroRealm;
import com.easytoolsoft.easyreport.membership.shiro.security.RetryLimitHashedCredentialsMatcher;
import com.easytoolsoft.easyreport.web.config.properties.ConfigProperties;
import com.google.common.collect.Maps;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tom Deng
 * @date 2017-04-08
 **/
@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class ShiroConfig {
    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        final ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/member/login");
        shiroFilterFactoryBean.setSuccessUrl("/home/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/401");

        final Map<String, Filter> filters = Maps.newHashMap();
        filters.put("authc", this.authcFilter());
        filters.put("membership", this.membershipFilter());
        shiroFilterFactoryBean.setFilters(filters);

        final Map<String, String> chains = Maps.newLinkedHashMap();
        chains.put("/member/logout", "logout");
        chains.put("/", this.configProperties.getShiro().getFilters());
        chains.put("/home/**", this.configProperties.getShiro().getFilters());
        chains.put("/views/**", this.configProperties.getShiro().getFilters());
        chains.put("/rest/**", this.configProperties.getShiro().getFilters());
        chains.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);

        return shiroFilterFactoryBean;
    }

    @Bean
    public AjaxFormAuthenticationFilter authcFilter() {
        final AjaxFormAuthenticationFilter authcFilter = new AjaxFormAuthenticationFilter();
        authcFilter.setUsernameParam("username");
        authcFilter.setPasswordParam("password");
        authcFilter.setRememberMeParam("rememberMe");
        authcFilter.setFailureKeyAttribute("shiroLoginFailure");
        return authcFilter;
    }

    @Bean
    public MembershipFilter membershipFilter() {
        return new MembershipFilter();
    }

    @Bean
    public SecurityManager securityManager() {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(cacheManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public RetryLimitHashedCredentialsMatcher credentialsMatcher() {
        final RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(
            cacheManager());
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public MyShiroRealm shiroRealm() {
        final MyShiroRealm realm = new MyShiroRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    @Bean
    public SessionManager sessionManager() {
        final DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.getSessionIdCookie().setName("EasyReport_JSESSIONID");
        return sessionManager;
    }

    @Bean
    public MemoryConstrainedCacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        final DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        final AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return aasa;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        final SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        final CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("ZUdsaGJuSmxibVI2ZHc9PQ=="));
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
}

