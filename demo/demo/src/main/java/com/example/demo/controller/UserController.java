package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;

@RestController
@RequestMapping("user")
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