package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;

@RestController
@RequestMapping("user") // TODO:注意这里有RequestMapping，我担心这会与url匹配产生某些化学反应
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<User> findAll() {
        List<User> list = new ArrayList<User>();
        list = userRepository.findAll();
        return list;
    }

    @RequestMapping("/getByUserName")
    @ResponseBody
    public User getByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return user;
    }

}