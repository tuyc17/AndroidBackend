package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.collections.MappingChange;
import org.apache.ibatis.annotations.Update;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dao.ArticleRepository;
import com.example.demo.domain.User;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;
    @PostMapping("/publish")
    public Map<String,Object> publish(int id,String title,String str){
        Map<String,Object> map = new HashMap<>();

        map.put("status",200);
        return map;
    }
}
