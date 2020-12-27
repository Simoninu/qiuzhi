package com.hjw.qiuzhi.service.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger基本配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 前台页面SwaggerApi页面文档
     * @return
     */
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                // 配置文档详细
                .apiInfo(webApiInfo())
                .select()
                // 只显示api路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    /**
     * 后台管理SwaggerApi页面文档
     * @return
     */
    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                // 配置文档详细
                .apiInfo(adminApiInfo())
                .select()
                // 只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    /**
     * 前台页面API文档详细配置
     * @return
     */
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("网站API文档")
                .description("本文档描述了前台网站接口定义")
                .version("1.0")
                .contact(new Contact("hjw", "http://123.com", "hjw123456@qq.com"))
                .build();
    }

    /**
     * 后台管理API文档详细配置
     * @return
     */
    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理API文档")
                .description("本文档描述了后台管理1系统接口定义")
                .version("1.0")
                .contact(new Contact("hjw", "http://123.com", "hjw123456@qq.com"))
                .build();
    }
}
