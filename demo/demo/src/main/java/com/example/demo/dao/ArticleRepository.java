package com.example.demo.dao;

import com.example.demo.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
    //
}
