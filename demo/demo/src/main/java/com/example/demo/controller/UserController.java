package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> logon(String studentId, String password, String username) {
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        User tempuser, user;

        tempuser = userRepository.findBystudentId(studentId);
        if (tempuser == null) {
            user = new User();
            user.setLastLogin(ctime);
            user.setNickName(username);
            user.setOnline(false);
            user.setVerified(false);
            user.setUserName(username);
            user.setStudentId(studentId);
            user.setPassWord(password);
            user.setAvatar(2131165277);
            user.setPraiseCount(0);
            user.setFavoriteCount(0);
            userRepository.save(user);
            map.put("code", 200);


            return map;
        } else {
            //如果学号已存在，返回已存在该用户
            map.put("code", 400);
            map.put("msg", "已存在该用户");
            return map;
        }
    }

    //修改昵称
    @PostMapping("/name")
    @ResponseBody
    public Map<String, Object> changeName(String username) {
        Map<String, Object> map = new HashMap<>();
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = myUserDetails.getId();
        User tempUser = userRepository.findById(id).get();
        tempUser.setNickName(username);
        tempUser.setUserName(username);
        userRepository.save(tempUser);
        map.put("code", 200);
        return map;
    }

    //修改头像
    @PostMapping("/avatar")
    @ResponseBody
    public Map<String, Object> changeAvatar(Integer avatar) {
        Map<String, Object> map = new HashMap<>();
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = myUserDetails.getId();
        User tempUser = userRepository.findById(id).get();
        tempUser.setAvatar(avatar);
        userRepository.save(tempUser);
        map.put("code", 200);
        return map;
    }

    //修改密码
    @PostMapping("/password")
    @ResponseBody
    public Map<String, Object> changePassword(String password) {
        Map<String, Object> map = new HashMap<>();
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = myUserDetails.getId();
        User tempUser = userRepository.findById(id).get();
        tempUser.setPassWord(password);
        userRepository.save(tempUser);
        map.put("code", 200);
        return map;
    }

    //获取特定id用户信息
    @GetMapping("/info")
    @ResponseBody
    public Map<String, Object> getUserById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Object[]> retUser;
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (id == -1) {
            id = myUserDetails.getId();
        }
        try {
            retUser = userRepository.getInfo(id);
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "查无此人");
            return map;
        }
        if (retUser == null) {
            map.put("code", 400);
            map.put("msg", "查无此人");
            return map;
        }
        //下面转换一下格式
        String[] strList = {"id", "isonline", "isverified", "lastlogin", "nickname",
                "password", "studentid", "username", "avatar", "favoritecount", "praisecount"};
        Map<String, Object> temp = new HashMap<>();
        for (Object[] user : retUser) {
            for (int i = 0; i < strList.length; i++) {
                if (user[i] != null) {
                    temp.put(strList[i], user[i]);
                }
            }
        }
        temp.remove("password");
        map.put("info", temp);
        map.put("code", 200);
        return map;
    }

//    // 查找用户
//    @GetMapping("/search")
//    @ResponseBody
//    public Map<String, Object> searchUser(String target) {
//
//    }
}