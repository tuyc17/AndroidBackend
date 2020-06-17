package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 13,通知消息类，用于表示每个特定用户的新通知
// 通知，包括用户id，是否有新通知，是否有新好友添加请求，文章是否被点赞收藏评论
// 2020/6/17 涂亦驰
@Entity
@Table(name = "message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键 用户id
    // 用户id
    @Id
    @Column(name = "userid")
    private Integer userId;
    // 是否有新通知
    @Column(name = "newmessage")
    private Boolean newMessage;
    // 是否有新的好友添加请求
    @Column(name = "newrequest")
    private Boolean newRequest;
    // 是否有新的文章点赞收藏评论
    @Column(name = "newinteraction")
    private Boolean newInteraction;

    public Message() {
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

    public Boolean getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Boolean newMessage) {
        this.newMessage = newMessage;
    }

    public Boolean getNewRequest() {
        return newRequest;
    }

    public void setNewRequest(Boolean newRequest) {
        this.newRequest = newRequest;
    }

    public Boolean getNewInteraction() {
        return newInteraction;
    }

    public void setNewInteraction(Boolean newInteraction) {
        this.newInteraction = newInteraction;
    }

}