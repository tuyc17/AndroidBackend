package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 12,关注类，用于表示每个特定用户关注对象的情况
// 关注情况，包括用户id，被用户关注者的id
// 2020/6/17 涂亦驰
@Entity
@Table(name = "following")
public class Following implements Serializable {
    private static final long serialVersionUID = 1L;

    // 用户id、被关注者id双主键
    // 用户id
    @Id
    @Column(name = "userid")
    private Integer userId;
    // 该用户关注之人的id
    @Id
    @Column(name = "followingid")
    private Integer followingId;

    public Following() {
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

    public Integer getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Integer followingId) {
        this.followingId = followingId;
    }

}