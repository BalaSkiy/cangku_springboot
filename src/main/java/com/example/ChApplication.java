package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan(value= {"com.example.mapper"})
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class ChApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChApplication.class, args);
    }

}

