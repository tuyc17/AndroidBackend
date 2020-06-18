package com.example.demo.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.FriendRepository;
import com.example.demo.dao.UserRepository;

@RestController
@RequestMapping("friend")
public class FriendController {
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;
    //获取所有好友信息
    @GetMapping("/getfriend")
    @ResponseBody
    public Map<String,Object> getFriend(Integer id) {
        Map<String,Object> map = new HashMap<>();
        List<User> retUser = new ArrayList<>();
        List<Integer> friendList = friendRepository.getAllFriend(id);
        for (Integer integer : friendList) {
            //一定要findByID后get，不能getOne，不然类型不对，我也不知道为啥，试出来的
            User tempUser = userRepository.findById(integer).get();
            retUser.add(tempUser);
        }
        map.put("friends",retUser);

        map.put("status",200);
        return map;

    }
//    @RequestMapping("/getAllUser")
//    @ResponseBody
//    public List<User> findAll() {
//        List<User> list ;
//        list = userRepository.findAll();
//        return list;
//    }
}
