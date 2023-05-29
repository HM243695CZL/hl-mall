package cn.hl.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台管理模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("商城管理系统")
                .description("商城管理系统接口文档")
                .version("1.0")
                .build();
    }
}
