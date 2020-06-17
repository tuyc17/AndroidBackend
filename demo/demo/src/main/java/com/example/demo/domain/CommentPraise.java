package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 9,评论点赞类，用于表示每个特定用户对评论的点赞情况
// 点赞情况，包括用户id，评论id，点赞时间
// 2020/6/17 涂亦驰
@Entity
@Table(name = "commentpraise")
public class CommentPraise implements Serializable {
    private static final long serialVersionUID = 1L;

    // 用户id
    @Column(name = "userid")
    private Integer userId;
    // 评论id
    @Column(name = "commentid")
    private Integer commentId;
    // 点赞时间
    @Column(name = "praisetime")
    private Date praiseTime;

    public CommentPraise() {
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

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Date getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

}