package com.example.demo.dao;

import com.example.demo.domain.Friend;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticlePraise;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>{
    //TODO：编写热推文章算法，建议使用深度学习
    //获取热推文章
    //TODO：编写文章搜索算法,建议使用模糊查询
    //搜索文章

    //文章点赞
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articlepraise(userid, articleid,praisetime) VALUES (:id, :articleId,:praisetime);" ,nativeQuery = true)
    int praise(@Param("id") Integer id, @Param("articleId") Integer articleId,@Param("praisetime")java.sql.Timestamp praisetime);

    //查询文章是否被点赞
    @Query(value = "select * from articlepraise where (userid = :id)and(articleid = :articleId) ",nativeQuery = true)
    List<Object[]> isPraised(@Param("id") Integer id, @Param("articleId") Integer articleId);

    //文章取消点赞
    @Modifying
    @Transactional
    @Query(value = "DELETE from articlepraise WHERE (userid = :id) and (articleid = :articleId);" ,nativeQuery = true)
    int cancelPraise(@Param("id") Integer id,@Param("articleId") Integer articleId);

    //评论点赞
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO commentpraise(userid, commentid, praisetime) VALUES (:id, :commentId,:praisetime);" ,nativeQuery = true)
    int praiseComment(@Param("id") Integer id, @Param("commentId") Integer commentId,@Param("praisetime")java.sql.Timestamp praisetime);

    //查询评论是否被点赞
    @Query(value = "select * from commentpraise where (userid = :id)and(commentid = :commentId) ",nativeQuery = true)
    List<Object[]> isPraisedComment(@Param("id") Integer id, @Param("commentId") Integer commentId);

    //评论取消点赞
    @Modifying
    @Transactional
    @Query(value = "DELETE from commentpraise WHERE (userid = :id) and (commentid = :commentId);" ,nativeQuery = true)
    int cancelPraiseComment(@Param("id") Integer id,@Param("commentId") Integer commentId);

    //文章收藏
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO favorite(userid, articleid,facoritename) VALUES (:id, :articleId,:facoritename);" ,nativeQuery = true)
    int favorite(@Param("id") Integer id, @Param("articleId") Integer articleId,@Param("facoritename")String facoritename);

    //查看文章是否被收藏
    @Query(value = "select * from favorite where (userid = :id)and(articleid = :articleid) ",nativeQuery = true)
    List<Object[]> isFavorite(@Param("id") Integer id, @Param("articleid") Integer articleid);

    //取消文章收藏
    @Modifying
    @Transactional
    @Query(value = "DELETE from favorite WHERE (userid = :id) and (articleid = :articleId);" ,nativeQuery = true)
    int cancelFavorite(@Param("id") Integer id,@Param("articleId") Integer articleId);
    //查询特定版块文章
    @Query(value = "select * from article where (articletheme = :articletheme)",nativeQuery = true)
    List<Object[]> getArticleByTheme(@Param("articletheme") String articletheme);
    //查询用户收藏夹内容
    @Query(value = "select articleid from favorite where (userid = :userid)",nativeQuery = true)
    List<Integer> getArticleByFavorite(@Param("userid") Integer userid);
    //查询特定文章信息
    //查询文章的评论
    @Query(value = "select * from comment where (farticleid = :farticleid)",nativeQuery = true)
    List<Object[]> getCommentByArticle(@Param("farticleid") Integer farticleid);

    //发表文章
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO article(articlename, content, articletheme, authorid, iswithdrew, praisecount, publishtime,hot) " +
            "VALUES (:articlename,:content,:articletheme,:authorid,0,0,:publishtime,0);" ,nativeQuery = true)
    int publish(@Param("articlename") String articlename, @Param("content")String content,@Param("authorid") Integer authorid
            ,@Param("articletheme")String articletheme,@Param("publishtime")java.sql.Timestamp publishtime);
    //发表评论
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO comment(authorid, content, farticleid, praisecount, publish_time) " +
            "VALUES (:id, :content,:articleId,0,:publishtime);" ,nativeQuery = true)
    int publishComment(@Param("id") Integer id, @Param("content")String content, @Param("articleId") Integer articleId
            ,@Param("publishtime")java.sql.Timestamp publishtime);
    //更新浏览记录
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO scanrecord(userid, articleid, scantime, articlename, articletheme) " +
            "VALUES (:id, :articleId,:scantime,:articlename, :articletheme);" ,nativeQuery = true)
    int addHistory(@Param("id") Integer id, @Param("articleId") Integer articleId,
                   @Param("scantime")java.sql.Timestamp scantime,
                   @Param("articlename") String articlename,@Param("articletheme") String articletheme

    );
    //add失败，更新时间
    @Modifying
    @Transactional
    @Query(value = "UPDATE scanrecord SET  scantime = :scantime WHERE (userid = :id) and (articleid = :articleId);" ,nativeQuery = true)
    int changeHistory(@Param("id") Integer id,@Param("articleId") Integer articleId,@Param("scantime")java.sql.Timestamp scantime);
    //查询所有浏览记录
    @Query(value = "select * from scanrecord where userid = :userid ",nativeQuery = true)
    List<Object[]> gethistory(@Param("userid") Integer userid);




}
