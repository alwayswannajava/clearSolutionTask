package com.spring.clearsolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ClearsolutionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClearsolutionsApplication.class, args);
    }

}
