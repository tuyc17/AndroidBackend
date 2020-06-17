package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 11,添加好友请求类，用于表示每个用户向其他用户提出的交友请求
// 交友请求，包括用户id，请求对象id，处理与否，被拒绝与否，请求时间，拒绝时间，验证信息
// 2020/6/17 涂亦驰
@Entity
@Table(name = "friendrequest")
public class FriendRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // 用户id、请求对象id双主键
    // 用户id
    @Id
    @Column(name = "userid")
    private Integer userId;
    // 请求对象id
    @Id
    @Column(name = "targetid")
    private Integer targetId;
    // 是否处理
    @Column(name = "ishandled")
    private Boolean isHandled;
    // 是否被拒绝
    @Column(name = "isrejected")
    private Boolean isRejected;
    // 请求时间
    @Column(name = "requesttime")
    private Date requestTime;
    // 拒绝时间
    @Column(name = "rejecttime")
    private Date rejectTime;
    // 验证信息
    @Column(name = "verifyinfo")
    private String verifyInfo;

    public FriendRequest() {
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

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Boolean getHandled() {
        return isHandled;
    }

    public void setHandled(Boolean handled) {
        isHandled = handled;
    }

    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    public String getVerifyInfo() {
        return verifyInfo;
    }

    public void setVerifyInfo(String verifyInfo) {
        this.verifyInfo = verifyInfo;
    }

}