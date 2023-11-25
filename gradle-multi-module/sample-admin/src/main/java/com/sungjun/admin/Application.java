package com.sungjun.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.sungjun", "com.sungjun.dto.*"})
@EnableJpaRepositories(basePackages = "com.sungjun")
@EntityScan(basePackages = "com.sungjun")
public class Application {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(Application.class, args);
        ac.getBeanDefinitionNames();
    }
}
