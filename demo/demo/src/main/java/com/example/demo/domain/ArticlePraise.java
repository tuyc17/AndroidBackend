package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 8,文章点赞类，用于表示每个特定用户对文章的点赞情况
// 点赞情况，包括用户id，文章id，点赞时间
// 2020/6/17 涂亦驰
@Entity
@Table(name = "articlepraise")
public class ArticlePraise implements Serializable {
    private static final long serialVersionUID = 1L;

    // 用户id和文章id双主键
    // 用户id
    @Id
    @Column(name = "userid")
    private Integer userId;
    // 文章id
    @Id
    @Column(name = "articleid")
    private Integer articleId;
    // 点赞时间
    @Column(name = "praisetime")
    private Date praiseTime;

    public ArticlePraise() {
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

    public Date getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

}