package com.easytoolsoft.easyreport.web.config.mvc;

import com.easytoolsoft.easyreport.support.consts.AppEnvConsts;
import com.easytoolsoft.easyreport.support.filter.ContextInitDataFilter;
import com.easytoolsoft.easyreport.web.config.properties.EnvProperties;
import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author Tom Deng
 * @date 2017-04-11
 **/
@Configuration
@EnableConfigurationProperties(EnvProperties.class)
public class ServletConfig {
    @Autowired
    private EnvProperties envProperties;

    /**
     * 让static下的静态资源走DefaultServlet, 不走SpringMVC DispatchServlet
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new DefaultServlet(), "/static/*");
    }

    /**
     * 在系统启动时加一些初始化数据到request上下文中
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean contextInitDataFilterRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ContextInitDataFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter(AppEnvConsts.APP_NAME_ITEM, this.envProperties.getAppName());
        registrationBean.addInitParameter(AppEnvConsts.ENV_NAME_ITEM, this.envProperties.getName());
        registrationBean.addInitParameter(AppEnvConsts.VERSION_ITEM, this.envProperties.getVersion());
        registrationBean.setName("contextInitDataFilter");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/customError/401"));
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/customError/403"));
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/customError/404"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/customError"));
        };
    }

    @Bean
    public ErrorProperties errorProperties() {
        final ErrorProperties properties = new ErrorProperties();
        properties.setIncludeStacktrace(IncludeStacktrace.ALWAYS);
        return properties;
    }
}
