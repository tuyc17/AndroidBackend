package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 5,聊天记录类，用于表示每条聊天记录的基本信息
// 聊天记录基本信息，包括发送者id，接收者id，发送时间，消息内容，是否已读
// 2020/6/17 涂亦驰
@Entity
@Table(name = "chatrecord")
public class ChatRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    // 发送者id与接收者id双主键
    // 发送者id
    @Id
    @Column(name = "senderid")
    private Integer senderId;
    // 接收者id
    @Id
    @Column(name = "receiverid")
    private Integer receiverId;
    // 发送时间
    @Column(name = "sendtime")
    private Date sendTime;
    // 消息内容
    @Column(name = "content")
    private String content;
    // 是否已读
    @Column(name = "isread")
    private Boolean isRead;

    public ChatRecord() {
        super();
    }

//    public User(String userName, String passWord) {
//        super();
//        this.userName = userName;
//        this.passWord = passWord;
//    }


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

}