package com.example.myspringapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@SpringBootApplication
@Service
public class MySpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringApplication.class, args);
    }

}
