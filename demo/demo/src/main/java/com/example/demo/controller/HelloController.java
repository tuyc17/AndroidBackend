package com.example.demo.controller;
import  org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequestMapping("/springboot")
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Boot";
    }
}