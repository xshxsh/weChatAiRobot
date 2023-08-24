package com.rich.wechatrobot.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestAPi() {
        //构造函数传入初始化规范，这是swagger2规范
        return new Docket(DocumentationType.OAS_30)
                //apiInfo：添加api的详情信息，参数为ApiInfo类型的参数，这个参数包含了基本描述信息：比如标题、描述、版本之类的，开发中一般都是自定义这些信息
                .apiInfo(apiInfo())
                // select、apis、paths、build 这四个是一组的，组合使用才能返回一个Docket实例对象，其中apis和paths是可选的。
                .select()
                //apis:添加过滤条件。RequestHandlerSelectors中有很多过滤方式；RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)：加了ApiOperation注解的类，生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //paths：控制那些路径的api会被显示出来。
                .paths(PathSelectors.any())
                .build()
//                .securitySchemes(Collections.singletonList(tokenScheme()))
//                .securityContexts(Collections.singletonList(tokenContext()))
//                .globalRequestParameters(getGlobalRequestParameters())
                //设置API分组
                .groupName("接口文档")
                //是否开启swagger 如果是false，浏览器将无法访问，默认是true
                .enable(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题内容
                .title("接口文档")
                //描述内容
                .description("AI机器人")
                //版本
                .version("1.0")
                .build();
    }

    // 请求头token参数
    private HttpAuthenticationScheme tokenScheme() {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
    }

    // 请求头token参数作用域
    private SecurityContext tokenContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(SecurityReference.builder()
                        .scopes(new AuthorizationScope[0])
                        .reference("Authorization")
                        .build()))
                .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                .build();
    }

}