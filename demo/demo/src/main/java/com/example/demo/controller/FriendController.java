package com.example.demo.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import com.example.demo.config.MyUserDetails;
import com.example.demo.domain.Friend;
import com.example.demo.domain.User;
import com.example.demo.domain.ChatRecord;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
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

    //获取所有关注你的人的信息
    @GetMapping("/follower")
    @ResponseBody
    public Map<String, Object> getFriend() {
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        List<User> retUser = new ArrayList<>();
        List<Integer> friendList = friendRepository.getAllFriended(id);
        for (Integer integer : friendList) {
            //一定要findByID后get，不能getOne，不然类型不对，我也不知道为啥，试出来的
            User tempUser = userRepository.findById(integer).get();
            retUser.add(tempUser);
        }
        map.put("follower", retUser);
        map.put("code", 200);
        return map;
    }
    // 获取所有你关注的人
    @GetMapping("/following")
    @ResponseBody
    public Map<String, Object> getFriend2() {
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
        map.put("following", retUser);
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
    //关注/取关
    @PostMapping("/follow")
    @ResponseBody
    public Map<String, Object> follow(Integer friendId){
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        Friend temp = new Friend();
        temp.setUserId(id);
        temp.setFriendId(friendId);
        if (!friendRepository.exists(Example.of(temp))){
            friendRepository.save(temp);
            map.put("code", 200);
        }
        else {
            friendRepository.deleteFriend(id,friendId);
            map.put("code", 201);
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
    // 返回用户是否关注某人
    @GetMapping("/follow")
    @ResponseBody
    public Map<String, Object> isFollow(Integer friendId){
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        Friend temp = new Friend();
        temp.setUserId(id);
        temp.setFriendId(friendId);
        if (!friendRepository.exists(Example.of(temp))){
            map.put("code", 201);
        }
        else {
            map.put("code", 200);
        }
        return map;
    }
    // 返回用户是否被某人关注
    @GetMapping("/followed")
    @ResponseBody
    public Map<String, Object> isFollowed(Integer friendId){
        MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        Friend temp = new Friend();
        temp.setUserId(friendId);
        temp.setFriendId(id);
        if (!friendRepository.exists(Example.of(temp))){
            map.put("code", 201);
        }
        else {
            map.put("code", 200);
        }
        return map;
    }
    @GetMapping("/followingcount")
    @ResponseBody
    public Map<String, Object> getAllFriendCount() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id= myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        Integer ret = friendRepository.getAllFriendCount(id);
        //下面转换一下格式
        map.put("count", ret);
        map.put("code", 200);
        return map;
    }

}
