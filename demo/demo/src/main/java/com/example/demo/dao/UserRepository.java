package com.example.demo.dao;

//import org.apache.ibatis.annotations.*;
//import org.mybatis.spring.annotation.MapperScan;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);
    // 用学号查找
    User findBystudentId(String studentid);

    //通过id获取某人的信息
    @Query(value = "select id, isonline, isverified, lastlogin, nickname, " +
            "password, studentid, username, avatar, favoritecount, praisecount from user where id = :id",nativeQuery = true)
    List<Object[]> getInfo(@Param("id") Integer id);

    //用户注册

}