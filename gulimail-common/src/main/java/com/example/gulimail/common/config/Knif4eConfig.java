package com.example.gulimail.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */

@Configuration
public class Knif4eConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI().info(
                new Info()
                        .title("后台管理系统API")
                        .version("1.0")
                        .description("后台管理系统API"));
    }

    @Bean
    public GroupedOpenApi productAPI() {

        return GroupedOpenApi.builder().group("商品管理").
                pathsToMatch(
                        "/product/brand/**",
                        "/product/category/**",
                        "/product/attrgroup/**",
                        "/product/categorybrandrelation/**",
                        "/product/attr/**",
                        "/product/**"
                ).
                build();
    }

    @Bean
    public GroupedOpenApi memberAPI() {

        return GroupedOpenApi.builder().group("会员管理").
                pathsToMatch(
                        "/member/member/**"

                ).
                build();
    }

}
