package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 10,好友类，用于表示每个特定用户的好友情况
// 好友情况，包括用户id，好友id
// 2020/6/17 涂亦驰
@Entity
@Table(name = "friend")
public class Friend implements Serializable {
    private static final long serialVersionUID = 1L;

    // 用户id
    @Column(name = "userid")
    private Integer userId;
    // 好友id
    @Column(name = "friendid")
    private Integer friendId;

    public Friend() {
        super();
    }

//    public User(String userName, String passWord) {
//        super();
//        this.userName = userName;
//        this.passWord = passWord;
//    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

}