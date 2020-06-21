package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

// 1,用户类，用于表示每个特定用户的基本信息
// 用户基本信息，包括用户id，姓名，昵称，学号，密码，上次登录时间，在线状态，大v标识
// 2020/6/16 涂亦驰
@Entity
@Table(name = "user")
// 类注解，空列不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    // 用户id，主键，自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 姓名（如：涂亦驰）
    @Column(name = "username")
    private String userName;
    // 昵称（如：襄江的蒲苇）
    @Column(name = "nickname")
    private String nickName;
    // 学号（如：2017013579）
    @Column(name = "studentid")
    private String studentId;
    // 密码
    @Column(name = "password")
    private String passWord;
    // 上次登录日期
    @Column(name = "lastlogin")
    private Date lastLogin;
    // 在线状态
    @Column(name = "isonline")
    private Boolean isOnline;
    // 大V标识
    @Column(name = "isverified")
    private Boolean isVerified;
    // 获得的赞数
    @Column(name = "praisecount")
    private Integer praiseCount;
    // 获得的收藏数
    @Column(name = "favoritecount")
    private Integer favoriteCount;
    // 头像
    @Column(name = "avatar")
    private Integer avatar;
    public User() {
        super();
    }

    public User(String userName, String passWord) {
        super();
        this.userName = userName;
        this.passWord = passWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }
    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }
}