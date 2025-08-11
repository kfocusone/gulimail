package com.example.gulimail.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * mybatisPlus 配置
 */
@MapperScan("com.example.gulimail.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.gulimail.product.feign")
public class GulimailProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailProductApplication.class, args);
    }

}
