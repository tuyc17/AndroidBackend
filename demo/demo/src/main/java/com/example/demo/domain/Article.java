package com.example.demo.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.*;

// 3,文章类，用于表示每篇特定文章的信息
// 文章信息，包括文章id，标题，文章模块分类，作者id，文章内容路径，点赞数，发布时间，是否被强制下
// 2020/6/17 涂亦驰
@Entity
@Table(name = "article")
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    // 文章id，主键，自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    // 文章内容
    @Column(name = "content")
    private String content;
    // 点赞数
    @Column(name = "praisecount")
    private Integer praiseCount;
    // 发布日期与时间
    @Column(name = "publishtime")
    private Date publishTime;
    // 是否被强制下架
    @Column(name = "iswithdrew")
    private Boolean isWithdrew;

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    //热度，隐藏属性,每次点赞，评论，收藏，浏览，点赞评论都会增加热度，
    /*
        热度具体设置：
        搜索时根据排名+10~1
        浏览+1
        评论点赞+2
        点赞+5
        收藏+10
        评论+20
        每天0点变为原先的2/3


     */
    @Column(name = "hot")
    private Integer hot;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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