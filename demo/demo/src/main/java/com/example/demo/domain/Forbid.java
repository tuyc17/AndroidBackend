package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 1,管理员类，用于表示每个管理员的基本信息
// 管理员基本信息，包括管理员id，管理员密码
// 2020/6/16 涂亦驰
@Entity
@Table(name = "forbid")
public class Forbid implements Serializable {
    private static final long serialVersionUID = 1L;

    // 被封禁用户的id，主键
    @Id
    private Integer id;
    // 封禁起始时间
    @Column(name = "starttime")
    private Date startTime;
    // 封禁理由
    @Column(name = "reason")
    private String reason;
    // 封禁结束时间
    @Column(name = "endtime")
    private Date endTime;

    public Forbid() {
        super();
    }

//    public User(String userName, String passWord) {
//        super();
//        this.userName = userName;
//        this.passWord = passWord;
//    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}