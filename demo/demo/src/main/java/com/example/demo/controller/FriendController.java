package com.example.demo.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import com.example.demo.config.MyUserDetails;
import com.example.demo.domain.User;
import com.example.demo.domain.ChatRecord;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.FriendRepository;
import com.example.demo.dao.UserRepository;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.LocalGregorianCalendar;

@RestController
@RequestMapping("friend")
public class FriendController {
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;

    //获取所有好友信息
    @GetMapping("/all")
    @ResponseBody
    public Map<String, Object> getFriend() {
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        List<User> retUser = new ArrayList<>();
        List<Integer> friendList = friendRepository.getAllFriend(id);
        for (Integer integer : friendList) {
            //一定要findByID后get，不能getOne，不然类型不对，我也不知道为啥，试出来的
            User tempUser = userRepository.findById(integer).get();
            retUser.add(tempUser);
        }
        map.put("friends", retUser);
        map.put("code", 200);
        return map;
    }

    // 获取特定聊天信息
    @GetMapping("/msg")
    @ResponseBody
    public Map<String, Object> getMsg(Integer friendId) {
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        List<Object[]> retChatRecord;
        List<Map<String, Object>> ret = new ArrayList<>();
        retChatRecord = friendRepository.getMsg(id, friendId);
        // TODO:记得把聊天信息的未读更新
        //下面转换一下格式
        String[] strList = {"id", "content", "isread", "receiverid", "sendtime", "senderid"};
        for (Object[] record : retChatRecord) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (record[i] != null) {
                    temp.put(strList[i], record[i]);
                }
            }
            temp.remove("id");
            ret.add(temp);
        }

        map.put("chatlist", ret);
        map.put("code", 200);
        return map;
    }
    // 添加好友
    // 接受添加好友请求
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addFriend(Integer friendId){
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        int code;
        // 先把好友请求列表中的内容置为已处理
        try {
            code = friendRepository.admitRequest(id,friendId);
        }
        catch (Exception e){
            code = -1;
        }
        if (code != 1){
            map.put("code", 400);
            map.put("msg", "好友请求接受失败");
            return map;
        }

        try {
            code = friendRepository.addFriend(id,friendId)+friendRepository.addFriend(friendId,id);
        }
        catch (Exception e){
            code = -1;
        }

        if (code != 2){
            map.put("code", 401);
            map.put("msg", "已添加此人为好友，前端疑似出错！");
            return map;
        }
        map.put("code", 200);
        return map;
    }
    // 拒绝好友添加请求
    @PostMapping("/reject")
    @ResponseBody
    public Map<String, Object> rejectFriend(Integer friendId){
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        int code;
        // 先把好友请求列表中的内容置为已处理
        try {
            code = friendRepository.rejectRequest(id,friendId);
        }
        catch (Exception e){
            code = -1;
        }
        if (code == 1){
            map.put("code", 200);
        }
        else{
            map.put("code", 400);
            map.put("msg", "好友请求拒绝失败");
            return map;
        }
        return map;
    }
    // 删除好友
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteFriend(Integer friendId){
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        int code;
        try {
            code = friendRepository.deleteFriend(id,friendId)+friendRepository.deleteFriend(friendId,id);
        }
        catch (Exception e){
            code = -1;
        }

        if (code == 2){
            map.put("code", 200);
        }
        else{
            map.put("code", 400);
            map.put("msg", "此人已被删除，前端疑似出错！");
        }
        return map;
    }
    // 向好友发送信息
    @PostMapping("/msg")
    @ResponseBody
    public Map<String, Object> sendMsg(Integer friendId,String content){
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        int code;
        try {
            code =  friendRepository.sendMsg(id,friendId,content,ctime);
        }
        catch (Exception e){
            code = -1;
        }

        if (code == 1){
            map.put("code", 200);
        }
        else{
            map.put("code", 400);
            map.put("msg", "发送消息失败");
        }
        return map;
    }
    // 发送添加好友请求
    @PostMapping("/request")
    @ResponseBody
    public Map<String, Object> sendRequest(Integer friendId,String content){
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        int code;
        try {
            code =  friendRepository.sendRequest(id,friendId,ctime,content);
        }
        catch (Exception e){
            code = -1;
        }

        if (code == 1){
            map.put("code", 200);
        }
        else{
            map.put("code", 400);
            map.put("msg", "发送添加请求失败");
        }
        return map;
    }
    // 获取当前好友请求
    @GetMapping("/request")
    @ResponseBody
    public Map<String, Object> getRequest() {
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        List<Object[]> retRequest;
        List<Map<String, Object>> ret = new ArrayList<>();
        retRequest = friendRepository.getRequest(id);
        // TODO:记得把聊天信息的未读更新
        //下面转换一下格式
        String[] strList = {"userid", "targetid", "ishandled", "isrejected", "rejecttime", "requesttime", "verifyinfo"};
        for (Object[] request : retRequest) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (request[i] != null) {
                    temp.put(strList[i], request[i]);
                }
            }
            ret.add(temp);
        }
        map.put("requestlist", ret);
        map.put("code", 200);
        return map;
    }
}
