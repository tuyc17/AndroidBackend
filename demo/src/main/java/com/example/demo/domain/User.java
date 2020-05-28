package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 作为数据库的实体类（尝试）
@Entity     // 代表这是一个实体类
@Table(name = "user")   // 对应数据库中的user表
public class User implements Serializable {
    // 这个东西是用来serialization 的key，A和B相互之间传输信息，用seralize，但是相互之间把解包之后的文件进行了更改，如果你程序中不加这个，相互之间再传输，会因为这个key不一样，而失败。
    private static final long serialVersionUID = 1L;

    @Id     // 代表这是主键
    @GeneratedValue     // 代表该主键自增
    // 后端识别信息
    private Integer userId;      // 用户序号（数据库存储与后端保存用）（有改成int的可能）
    // 用户注册与登录信息
    @Column(name = "username")  // 代表一个"username"属性
    private String userName;    // 用户名
    @Column(name = "password")
    private String passWord;    // 用户密码
    @Column(name = "studentid")
    private String studentId;   // 学号
    // 用户个人信息
    @Column(name = "nickname")
    private String nickName;    // 昵称
    @Column(name = "headicon")
    private String headIcon;    // 头像（图片路径）

    // 构造函数
    public User() {
        super();
    }
    public User(String userName, String passWord, String studentId) {
        super();
        this.userName = userName;
        this.passWord = passWord;
        this.studentId = studentId;
    }

    // getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

}
