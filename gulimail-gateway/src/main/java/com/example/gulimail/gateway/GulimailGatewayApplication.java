package com.example.gulimail.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1. 开启服务注册发现
 *      配置 nacos 地址
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GulimailGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailGatewayApplication.class, args);
    }

}
