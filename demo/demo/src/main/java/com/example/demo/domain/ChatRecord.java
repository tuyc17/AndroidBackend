package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

// 5,聊天记录类，用于表示每条聊天记录的基本信息
// 聊天记录基本信息，包括发送者id，接收者id，发送时间，消息内容，是否已读
// 2020/6/17 涂亦驰
@Entity
@Table(name = "chatrecord")
//类注解，空列不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键为聊天记录id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 发送者id
    @Column(name = "senderid")
    private Integer senderId;
    // 接收者id
    @Column(name = "receiverid")
    private Integer receiverId;
    // 发送时间
    @Column(name = "sendtime")
    private java.sql.Timestamp sendTime;
    // 消息内容
    @Column(name = "content")
    private String content;
    // 是否已读
    @Column(name = "isread")
    private Boolean isRead;

    public ChatRecord() {
        super();
    }


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

    public void setSendTime(java.sql.Timestamp sendTime) {
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