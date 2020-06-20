package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 6,浏览历史记录类，用于表示特定用户浏览特定文章的基本信息
// 浏览历史记录基本信息，包括用户id，文章id，浏览时间
// 2020/6/17 涂亦驰
@Entity
@Table(name = "scanrecord")
public class ScanRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    // 用户id、文章id双主键
    // 用户id
    @Id
    @Column(name = "userid")
    private Integer userId;
    // 文章id
    @Id
    @Column(name = "articleid")
    private Integer articleId;
    // 浏览时间
    @Column(name = "scantime")
    private Date scanTime;
    // 文章标题（如：《中单卡特技巧与攻略》）
    @Column(name = "articlename")
    private String articleName;
    // 文章所属主题（theme）
    @Column(name = "articletheme")
    private String articleTheme;

    public ScanRecord() {
        super();
    }

//    public User(String userName, String passWord) {
//        super();
//        this.userName = userName;
//        this.passWord = passWord;
//    }


    public String getArticleName() {
        return articleName;
    }

    public String getArticleTheme() {
        return articleTheme;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setArticleTheme(String articleTheme) {
        this.articleTheme = articleTheme;
    }

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

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

}