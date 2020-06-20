package com.example.demo.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import com.example.demo.domain.User;
import com.example.demo.domain.ChatRecord;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.FriendRepository;
import com.example.demo.dao.UserRepository;
// import sun.util.calendar.BaseCalendar;
// import sun.util.calendar.LocalGregorianCalendar;

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
    public Map<String, Object> getFriend(Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<User> retUser = new ArrayList<>();
        List<Integer> friendList = friendRepository.getAllFriend(id);
        for (Integer integer : friendList) {
            //一定要findByID后get，不能getOne，不然类型不对，我也不知道为啥，试出来的
            User tempUser = userRepository.findById(integer).get();
            retUser.add(tempUser);
        }
        map.put("friends", retUser);
        map.put("status", 200);
        return map;
    }

    // 获取特定聊天信息
    @GetMapping("/getmsg")
    @ResponseBody
    public Map<String, Object> getMsg(Integer id, Integer friendId) {
        Map<String, Object> map = new HashMap<>();
        List<Object[]> retChatRecord;
        List<Map<String, Object>> ret = new ArrayList<>();
        retChatRecord = friendRepository.getMsg(id, friendId);
        // TODO:记得把聊天信息的未读更新
        //下面转换一下格式
        String[] strList = {"senderid", "receiverid", "content", "isread", "sendtime"};
        for (Object[] record : retChatRecord) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (record[i] != null) {
                    temp.put(strList[i], record[i]);
                }
            }
            ret.add(temp);
        }

        map.put("chatlist", ret);
        map.put("status", 200);
        return map;
    }
    // 添加好友
    // 接受添加好友请求
    @PostMapping("/addfriend")
    @ResponseBody
    public Map<String, Object> addFriend(Integer id, Integer friendId){
        Map<String, Object> map = new HashMap<>();
        int code;
        // 先把好友请求列表中的内容置为已处理
        try {
            code = friendRepository.admitRequest(id,friendId);
        }
        catch (Exception e){
            code = -1;
        }
        if (code == 1){
            map.put("status", 200);
        }
        else{
            map.put("status", "好友请求接受失败");
            return map;
        }

        try {
            code = friendRepository.addFriend(id,friendId)+friendRepository.addFriend(friendId,id);
        }
        catch (Exception e){
            code = -1;
        }

        if (code == 2){
            map.put("status", 200);
        }
        else{
            map.put("status", "已添加此人为好友，前端疑似出错！");
            return map;
        }
        return map;
    }
    // 拒绝好友添加请求
    @PostMapping("/rejectfriend")
    @ResponseBody
    public Map<String, Object> rejectFriend(Integer id, Integer friendId){
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
            map.put("status", 200);
        }
        else{
            map.put("status", "好友请求拒绝失败");
            return map;
        }
        return map;
    }
    // 删除好友
    @PostMapping("/deletefriend")
    @ResponseBody
    public Map<String, Object> deleteFriend(Integer id, Integer friendId){
        Map<String, Object> map = new HashMap<>();
        int code;
        try {
            code = friendRepository.deleteFriend(id,friendId)+friendRepository.deleteFriend(friendId,id);
        }
        catch (Exception e){
            code = -1;
        }

        if (code == 2){
            map.put("status", 200);
        }
        else{
            map.put("status", "此人已被删除，前端疑似出错！");
        }
        return map;
    }
    // 向好友发送信息
    @PostMapping("/sendmsg")
    @ResponseBody
    public Map<String, Object> sendMsg(Integer id, Integer friendId,String content){
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
            map.put("status", 200);
        }
        else{
            map.put("status", "发送消息失败");
        }
        return map;
    }
    // 发送添加好友请求
    @PostMapping("/sendrequest")
    @ResponseBody
    public Map<String, Object> sendRequest(Integer id, Integer friendId,String content){
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
            map.put("status", 200);
        }
        else{
            map.put("status", "发送添加请求失败");
        }
        return map;
    }
    // 获取当前好友请求
    @GetMapping("/getrequest")
    @ResponseBody
    public Map<String, Object> getRequest(Integer id, Integer friendId) {
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
        map.put("status", 200);
        return map;
    }
}
