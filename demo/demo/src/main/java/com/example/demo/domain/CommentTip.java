package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 4,评论举报类，用于表示特定用户对特定评论的举报情况
// 举报情况，包括举报者id，举报评论id，举报类型，举报理由
// 2020/6/17 涂亦驰
@Entity
@Table(name = "commenttip")
public class CommentTip implements Serializable {
    private static final long serialVersionUID = 1L;

    // 举报用户id、被举报评论id双主键
    // 用户id
    @Id
    @Column(name = "userid")
    private Integer userId;
    // 评论id
    @Id
    @Column(name = "commentid")
    private Integer commentId;
    // 举报类型
    @Column(name = "tiptype")
    private String tipType;
    // 举报理由
    @Column(name = "tipreason")
    private String tipReason;

    public CommentTip() {
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

    public String getTipType() {
        return tipType;
    }

    public void setTipType(String tipType) {
        this.tipType = tipType;
    }

    public String getTipReason() {
        return tipReason;
    }

    public void setTipReason(String tipReason) {
        this.tipReason = tipReason;
    }

}