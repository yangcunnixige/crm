package com.yangnan.crm.common.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig{

    // http://localhost:8082/swagger-ui/index.html
    // http://localhost:8082/doc.html

    // 配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) //指定api类型为swagger2
                .groupName("WebApi") //指定分组
                .apiInfo(getApiInfo()) //用于定义api文档汇总信息
                .select()
                .paths(PathSelectors.regex("/rbac/.*"))
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("CRM后台接口api")// 文档页标题
                .contact(new Contact("yangnan", "wu", "1721380260@qq.com"))// 联系人信息
                .description("CRM后台接口提供的api文档") //详细信息
                .version("1.0.1")  //文档版本号
                .termsOfServiceUrl("无") //网站地址
                .build();
    }
}