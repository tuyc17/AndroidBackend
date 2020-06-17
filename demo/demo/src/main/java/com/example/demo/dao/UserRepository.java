package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    // 除去简单的根据给定的属性进行查找以外，是否能够通过在这里定义函数，来实现其他功能譬如修改和更新呢？
}