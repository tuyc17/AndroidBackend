package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 6,院系通知类
// 通知，包括通知id，通知时间，通知院系，通知内容
// 2020/6/17 涂亦驰
@Entity
@Table(name = "departmentnotice")
public class DepartmentNotice implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键：通知id
    @Id
    @Column(name = "id")
    private Integer id;
    // 通知时间
    @Column(name = "noticetime")
    private Date noticeTime;
    // 通知院系
    @Column(name = "department")
    private String department;
    // 通知内容
    @Column(name = "content")
    private String content;

    public DepartmentNotice() {
        super();
    }

//    public User(String userName, String passWord) {
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

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}