package com.example.demo.dao;

//import org.apache.ibatis.annotations.*;
//import org.mybatis.spring.annotation.MapperScan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);
    //根据id 修改图书
//    @Update("update book set bookname=#{bookname}, price=#{price},autor=#{autor}, booktype=#{booktype} where id=#{id}")
//    int updateBook(Book book);

    // 除去简单的根据给定的属性进行查找以外，是否能够通过在这里定义函数，来实现其他功能譬如修改和更新呢？
}