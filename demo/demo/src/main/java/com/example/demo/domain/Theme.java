package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 2,主题模块类，用于表示每篇特定文章的分类结果
// 文章主题分类模块，包括主题模块id，主题名称，主题所属类别（文理工）
// 2020/6/16 涂亦驰
@Entity
@Table(name = "theme")
public class Theme implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主题id，主键，自增
    @Id
    @GeneratedValue
    private Integer id;
    // 主题名称（如：unity的学习和使用）
    @Column(name = "themename")
    private String themeName;
    // 主题所属类别（文理工）
    @Column(name = "themeclass")
    private String themeClass;

    public Theme() {
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

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeClass() {
        return themeClass;
    }

    public void setThemeClass(String themeClass) {
        this.themeClass = themeClass;
    }


}