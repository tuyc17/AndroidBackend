package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.User;

// Dao层主要用来实现对数据库的增删改查，只需要继承JpaRepository即可，几乎不用写方法，可以根据方法名自动生产SQL
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);   // 自动生产一个以userName为参数的查询方法
}
