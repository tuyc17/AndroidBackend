package com.example.demo.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 3,文章类，用于表示每篇特定文章的信息
// 文章信息，包括文章id，标题，文章模块分类，作者id，文章内容路径，点赞数，发布时间，是否被强制下
// 2020/6/17 涂亦驰
@Entity
@Table(name = "article")
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    // 文章id，主键，自增
    @Id
    @GeneratedValue
    private Integer id;
    // 文章标题（如：《中单卡特技巧与攻略》）
    @Column(name = "articlename")
    private String articleName;
    // 文章所属主题（theme）
    @Column(name = "articletheme")
    private String articleTheme;
    // 作者id（User.id）
    @Column(name = "authorid")
    private Integer authorId;
    // 文章内容路径（url）
    @Column(name = "articlepath")
    private String articlePath;
    // 点赞数
    @Column(name = "praisecount")
    private Integer praiseCount;
    // 发布日期与时间 TODO:疑似Date中有Calendar类可以获取日期和时间 https://blog.csdn.net/mengshi_/article/details/84666293
    @Column(name = "publishtime")
    private Date publishTime;
    // 是否被强制下架
    @Column(name = "iswithdrew")
    private Boolean isWithdrew;

    public Article() {
        super();
    }

//    public Theme(String userName, String passWord) {
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

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleTheme() {
        return articleTheme;
    }

    public void setArticleTheme(String articleTheme) {
        this.articleTheme = articleTheme;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getArticlePath() {
        return articlePath;
    }

    public void setArticlePath(String articlePath) {
        this.articlePath = articlePath;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Boolean getWithdrew() {
        return isWithdrew;
    }

    public void setWithdrew(Boolean withdrew) {
        isWithdrew = withdrew;
    }

}