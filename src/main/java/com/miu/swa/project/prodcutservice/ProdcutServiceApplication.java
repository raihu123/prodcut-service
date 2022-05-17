package com.miu.swa.project.prodcutservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProdcutServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdcutServiceApplication.class, args);
    }

}
