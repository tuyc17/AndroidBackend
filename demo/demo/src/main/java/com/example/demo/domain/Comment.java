package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

// 4,评论类，用于表示对某篇文章或评论的具体评论的信息
// 评论信息，包括评论id，父文章id，父评论id，评论作者id，评论内容路径，点赞数，发布时间
// 2020/6/17 涂亦驰
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    // 评论id，主键，自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 父文章id
    @Column(name = "farticleid")
    private Integer fArticleId;
    // 父评论id
    @Column(name = "fcommentid")
    private Integer fCommentId;
    // 评论作者id
    @Column(name = "authorid")
    private Integer authorId;
    // 评论内容路径
    @Column(name = "content")
    private String content;
    // 点赞数
    @Column(name = "praisecount")
    private Integer praiseCount;
    // 发布时间
    @Column(name = "publishTime")
    private Date publishTime;

    public Comment() {
        super();
    }

//    设置属性的default值
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

    public Integer getfArticleId() {
        return fArticleId;
    }

    public void setfArticleId(Integer fArticleId) {
        this.fArticleId = fArticleId;
    }

    public Integer getfCommentId() {
        return fCommentId;
    }

    public void setfCommentId(Integer fCommentId) {
        this.fCommentId = fCommentId;
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

    public void setContent(String commentPath) {
        this.content = commentPath;
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

}