package com.example.demo.controller;
import  org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Controller
// @SpringBootApplication
public class LoginController {
    @RequestMapping("/login")
    public String loginHtml() {
        // System.out.println("进入后台");
        return "login";
    }
}