package com.example.gulimail.ware;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
public class GulimailWareApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimailWareApplication.class, args);
    }

}
