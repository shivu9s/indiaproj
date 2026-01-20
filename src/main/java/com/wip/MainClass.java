package com.wip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainClass {

    @GetMapping("/")
    public String home() {
        return "Java + Jenkins + Docker + Kubernetes is RUNNING";
    }

    public int add(int... number) {
        int result = 0;
        for (int i : number) {
            result = result + i;
        }
        return result;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainClass.class, args);
        System.out.println("Spring Boot app started successfully inside Kubernetes");
    }
}
