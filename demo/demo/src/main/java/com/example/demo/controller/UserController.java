package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.config.MyUserDetails;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    // 注册，只返回状态码，不登录
    @PostMapping("/logon")
    @ResponseBody
    public Map<String, Object> logon(String studentid , String password,String username){
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        User tempuser,user;

        tempuser = userRepository.findBystudentId(studentid);
        if (tempuser == null){
            user = new User();
            user.setLastLogin(ctime);
            user.setNickName(username);
            user.setOnline(false);
            user.setVerified(false);
            user.setUserName(username);
            user.setStudentId(studentid);
            user.setPassWord(password);
            user.setAvatar(0);
            user.setPraiseCount(0);
            user.setFavoriteCount(0);
            userRepository.save(user);
            map.put("code", 200);
            return map;
        }
        else{
            //如果学号已存在，返回已存在该用户
            map.put("code", 400);
            map.put("msg", "已存在该用户");
            return map;
        }
    }
    //修改昵称
    @PostMapping("/name")
    @ResponseBody
    public Map<String, Object> changename(String username) {
        Map<String, Object> map = new HashMap<>();
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        int id = myUserDetails.getId();
        User tempUser = userRepository.findById(id).get();
        tempUser.setNickName(username);
        tempUser.setUserName(username);
        userRepository.save(tempUser);
        map.put("code",200);
        return map;
    }
    //修改头像

    //修改密码

    //获取特定id用户信息


}