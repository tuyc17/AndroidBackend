package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 7,收藏关系类，用于表示每个特定用户对文章的收藏情况
// 收藏情况，包括用户id，文章id，收藏夹名
// 2020/6/17 涂亦驰
@Entity
@Table(name = "favorite")
public class Favorite implements Serializable {
    private static final long serialVersionUID = 1L;

//    // 用户id，主键，自增
//    @Id
//    @GeneratedValue
//    private Integer id;
    // 用户id
    @Column(name = "userid")
    private Integer userId;
    // 文章id
    @Column(name = "articleid")
    private Integer articleId;
    // 收藏夹名
    @Column(name = "facoritename")
    private String facoriteName;

    public Favorite() {
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

    public String getFacoriteName() {
        return facoriteName;
    }

    public void setFacoriteName(String facoriteName) {
        this.facoriteName = facoriteName;
    }

}