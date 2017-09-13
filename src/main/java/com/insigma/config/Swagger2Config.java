package com.insigma.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration      //��Spring�����ظ�������
@EnableWebMvc       //����Mvc����springboot�����Ҫ����ע��@EnableWebMvc
@EnableSwagger2     //����Swagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                //ɨ��ָ�����е�swaggerע��
                //.apis(RequestHandlerSelectors.basePackage("com.xia.controller"))
                //ɨ��������ע���api�������ַ�ʽ�����
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("����ƽ̨ RESTful APIs")
                .description("����ƽ̨ RESTful ���Ľӿ��ĵ���������ϸ������ļ�����ǰ��˵Ĺ�ͨ�ɱ���ͬʱȷ���������ĵ����ָ߶�һ�£�����ļ���ά���ĵ���ʱ�䡣")
                .termsOfServiceUrl("http://xiachengwei5.coding.me")
                .version("1.0.0")
                .build();
    }
}
