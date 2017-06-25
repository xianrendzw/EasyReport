package com.easytoolsoft.easyreport.web.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhiwei.deng
 * @date 2017-04-20
 * @see <a href="http://springfox.github.io/springfox/docs/current/#getting-started">swagger</a>
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String BASE_PACKAGE = "com.easytoolsoft.easyreport.web";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(this.apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("OKCoin-OKEX")
            .description("OKCoin交易系统")
            .termsOfServiceUrl("http://www.easytoolsoft.com")
            .contact(new Contact("easytoolsoft", "http://www.easytoolsoft.com", "14068728@qq.com"))
            .version("2.1")
            .build();
    }
}
