package com.grupo3.forgotservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class ForgotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForgotServiceApplication.class, args);
    }

}
