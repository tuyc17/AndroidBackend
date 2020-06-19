package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.dao.CommentRepository;
import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
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
    @Autowired
    CommentRepository commentRepository;
    //发布文章
    @PostMapping("/publish")
    public Map<String, Object> publish(Integer id, String title, String content, String theme) {
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        articleRepository.publish(title, content, id, theme, ctime);
        map.put("status", 200);
        return map;
    }

    //发布评论
    @PostMapping("/publishcomment")
    public Map<String, Object> publishComment(Integer id, Integer articleId, String content) {
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        articleRepository.publishComment(id, content, articleId, ctime);
        map.put("status", 200);
        return map;
    }
    //TODO：编写热推文章算法，建议使用深度学习
    //获取热推文章

    //TODO：编写文章搜索算法,建议使用模糊查询
    //搜索文章


    //阅读特定文章
    @GetMapping("/readarticle")
    public Map<String, Object> getArticle(Integer id, Integer articleId) {
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        try {
            map.put("article", articleRepository.findById(articleId).get());
        } catch (Exception e) {
            map.put("status", "错误：文章不存在");
            return map;
        }
        //拿所有的评论
        List<Object[]> comments;
        try {
            comments = articleRepository.getCommentByArticle(articleId);
        }catch (Exception e) {
            map.put("status", "拿评论时出错");
            return map;
        }
        List<Map<String, Object>> ret = new ArrayList<>();
        //下面转换一下格式
        String[] strList = {"id", "authorid", "content", "farticleid", "fcommentid", "praisecount", "publish_time"};
        for (Object[] record : comments) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (record[i] != null) {
                    temp.put(strList[i], record[i]);
                }
            }
            ret.add(temp);
        }
        map.put("comments", ret);
        //下面更新浏览记录
        try {
            articleRepository.addHistory(id, articleId, ctime);
        } catch (Exception e) {
            articleRepository.changeHistory(id, articleId, ctime);
        }
        map.put("status", 200);
        return map;
    }

    //收藏/取消收藏文章
    @PostMapping("/favorite")
    @ResponseBody
    public Map<String, Object> favorite(Integer id, Integer articleId){
        Map<String, Object> map = new HashMap<>();
        Article tempArticle;
        //先拿到对应文章
        try {
            tempArticle  =  articleRepository.findById(articleId).get();
        } catch (Exception e) {
            map.put("status", "错误：文章不存在");
            return map;
        }
        //判断文章是否被此人收藏过
        List<Object[]> temp;
        try {
            temp = articleRepository.isFavorite(id,articleId);
        }
        catch (Exception e){
            map.put("status", "查询收藏出错");
            return map;
        }
        if (temp.size() == 0){
            //没有收藏，调用收藏接口
            try {
                articleRepository.favorite(id, articleId,"default");
                //200表示收藏
                map.put("status", 200);
            }
            catch (Exception e){
                map.put("status", "收藏出错");
                return map;
            }
        }
        else{
            //否则调用取消收藏接口
            try {
                articleRepository.cancelFavorite(id,articleId);
                //201表示取消收藏
                map.put("status", 201);
            }
            catch (Exception e){
                map.put("status", "取消收藏出错");
                return map;
            }
        }
        return map;
    }

    //点赞/取消点赞文章
    @PostMapping("/praise")
    @ResponseBody
    public Map<String, Object> praise(Integer id, Integer articleId){
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        Article tempArticle;
        //先拿到对应文章
        try {
            tempArticle  =  articleRepository.findById(articleId).get();
        } catch (Exception e) {
            map.put("status", "错误：文章不存在");
            return map;
        }
        //判断文章是否被此人点赞过
        List<Object[]> temp;
        try {
            temp = articleRepository.isPraised(id,articleId);
        }
        catch (Exception e){
            map.put("status", "查询点赞出错");
            return map;
        }
        if (temp.size() == 0){
            //没有点赞，调用点赞接口
            try {
                articleRepository.praise(id, articleId, ctime);
                //200表示点赞
                map.put("status", 200);
                tempArticle.setPraiseCount(tempArticle.getPraiseCount()+1);
                articleRepository.save(tempArticle);
            }
            catch (Exception e){
                map.put("status", "点赞出错");
                return map;
            }
        }
        else{
            //否则调用取消点赞接口
            try {
                articleRepository.cancelPraise(id,articleId);
                //201表示取消点赞
                map.put("status", 201);
                tempArticle.setPraiseCount(tempArticle.getPraiseCount()-1);
                articleRepository.save(tempArticle);
            }
            catch (Exception e){
                map.put("status", "取消点赞出错");
                return map;
            }
        }
        return map;
    }

    //点赞/取消点赞评论
    @PostMapping("/praisecomment")
    @ResponseBody
    public Map<String, Object> praiseComment(Integer id, Integer commentId){
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        Comment tempComment;
        //先拿到对应评论
        try {
            tempComment  =  commentRepository.findById(commentId).get();
        } catch (Exception e) {
            map.put("status", "错误：评论不存在");
            return map;
        }
        //判断评论是否被此人点赞过
        List<Object[]> temp;
        try {
            temp = articleRepository.isPraisedComment(id,commentId);
        }
        catch (Exception e){
            map.put("status", "查询点赞出错");
            return map;
        }
        if (temp.size() == 0){
            //没有点赞，调用点赞接口
            try {
                articleRepository.praiseComment(id, commentId, ctime);
                //200表示点赞
                map.put("status", 200);
                tempComment.setPraiseCount(tempComment.getPraiseCount()+1);
                commentRepository.save(tempComment);
            }
            catch (Exception e){
                map.put("status", "点赞出错");
                return map;
            }
        }
        else{
            //否则调用取消点赞接口
            try {
                articleRepository.cancelPraiseComment(id,commentId);
                //201表示取消点赞
                map.put("status", 201);
                tempComment.setPraiseCount(tempComment.getPraiseCount()-1);
                commentRepository.save(tempComment);
            }
            catch (Exception e){
                map.put("status", "取消点赞出错");
                return map;
            }
        }
        return map;
    }

    //获取浏览记录
    @GetMapping("/gethistory")
    @ResponseBody
    public Map<String, Object> getHistory(Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Object[]> retHistory;
        List<Map<String, Object>> ret = new ArrayList<>();
        retHistory = articleRepository.gethistory(id);
        //下面转换一下格式
        String[] strList = {"userid", "articleid", "scantime"};
        for (Object[] record : retHistory) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (record[i] != null) {
                    temp.put(strList[i], record[i]);
                }
            }
            ret.add(temp);
        }
        map.put("history", ret);
        map.put("status", 200);
        return map;
    }
    //获取收藏文章
    @GetMapping("/getfavorite")
    @ResponseBody
    public Map<String, Object> getFavorite(Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> retFavorite;
        List<Article> articles = new ArrayList<>();
        List<Map<String, Object>> ret = new ArrayList<>();
        retFavorite = articleRepository.getArticleByFavorite(id);
        for (Integer index : retFavorite) {
            articles.add(articleRepository.findById(index).get());
        }
        map.put("articles", articles);
        map.put("status", 200);
        return map;
    }

}
