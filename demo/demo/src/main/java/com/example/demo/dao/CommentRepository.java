package com.example.demo.dao;

//import org.apache.ibatis.annotations.*;
//import org.mybatis.spring.annotation.MapperScan;
import java.util.List;

import com.example.demo.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.User;

public interface CommentRepository extends JpaRepository<Comment, Integer> {


}