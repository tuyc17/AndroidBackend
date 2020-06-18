package com.example.demo.controller;
import  org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Controller
// @SpringBootApplication
@RequestMapping("/springboot")
public class HelloController {
    @RequestMapping("/hello")
    public String helloHtml() {
        // System.out.println("进入后台");
        return "hello";
    }
}