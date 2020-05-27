package com.example.demo;

public class User {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    // 后端识别信息
    private String userId;      // 用户序号（数据库存储与后端保存用）（有改成int的可能）
    // 用户注册与登录信息
    private String userName;    // 用户名
    private String passWord;    // 用户密码
    private String studentId;   // 学号
    // 用户个人信息
    private String nickName;    // 昵称
    private String headIcon;    // 头像（图片路径）
}
