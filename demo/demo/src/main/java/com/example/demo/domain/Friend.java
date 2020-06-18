package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.IdClass;

// 10,好友类，用于表示每个特定用户的好友情况
// 好友情况，包括用户id，好友id
// 2020/6/17 涂亦驰

//此处增加联合主键，以免报错

/**
 *
 * @author 林声浩
 *	组合主键
 */
class idpair implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * 主键-RID
     */
    private Integer userId;

    /*
     * 主键-历史版本号，保存格式年份_版本号，例如2018_1
     */
    private Integer friendId;
}

@Entity
@Table(name = "friend")
@IdClass(idpair.class)
public class Friend implements Serializable {
    private static final long serialVersionUID = 1L;

    // 用户id、好友id双主键
    // 用户id
    @Id
    @Column(name = "userid")
    private Integer userId;
    // 好友id
    @Id
    @Column(name = "friendid")
    private Integer friendId;

    public Friend() {
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

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

}