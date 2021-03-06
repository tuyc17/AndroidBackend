package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 5,评论申诉类，用于表示特定用户对被举报的评论的申诉情况
// 申诉情况，包括申诉者id，申诉评论id，申诉理由
// 2020/6/17 涂亦驰
@Entity
@Table(name = "commentappeal")
public class CommentAppeal implements Serializable {
    private static final long serialVersionUID = 1L;

    // 申诉用户id、被举报评论id双主键
    // 用户id
    @Id
    @Column(name = "userid")
    private Integer userId;
    // 评论id
    @Id
    @Column(name = "commentid")
    private Integer articleId;
    // 申诉理由
    @Column(name = "appealreason")
    private String appealReason;

    public CommentAppeal() {
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

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getAppealReason() {
        return appealReason;
    }

    public void setAppealReason(String appealReason) {
        this.appealReason = appealReason;
    }

}