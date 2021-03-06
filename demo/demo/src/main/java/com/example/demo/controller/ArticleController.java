package com.example.demo.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.example.demo.config.MyUserDetails;
import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.ScanRecordRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.*;
// import com.sun.javafx.collections.MappingChange;
import com.example.demo.search.IndexProcessor;
import com.example.demo.search.Search;
import org.apache.ibatis.annotations.Update;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.WebSocketServer;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import com.example.demo.dao.ArticleRepository;
import org.springframework.scheduling.annotation.Scheduled;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ScanRecordRepository scanRecordRepository;

    // 发布文章
    @PostMapping("/article")
    public Map<String, Object> publish(String title, String content, String theme) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());


        // 将文章内容content写进目录下
        Article article = new Article();
        article.setPraiseCount(0);
        article.setHot(0);
        article.setArticleName(title);
        article.setAuthorId(id);
        article.setPublishTime(ctime);
        article.setArticleTheme(theme);
        //找第一个分隔符
        Integer[] t ={content.indexOf("。"),content.indexOf("!"),content.indexOf("."),20,content.length()};
        Integer min = 99;
        for (Integer index:t) {
            if (index==-1){
                continue;
            }
            if (min>=index){
                min = index;
            }
        }

        article.setContent(content.substring(0,min));
        articleRepository.save(article);
        Integer articleId = article.getId();
        File contentDir = new File("search" + File.separator + "content"
                + File.separator + articleId.toString() + ".txt");
        // File contentDir = new File("D:\\GitLib\\AndroidBackend\\demo\\demo\\search\\content\\"+articleId.toString()+".txt");
        try {
            contentDir.createNewFile();
            // 指定UTF_8编码防止乱码
            BufferedWriter bwriter = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(contentDir, false), StandardCharsets.UTF_8));
            bwriter.write(content);
            bwriter.flush();
            bwriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("code", 200);
        map.put("id",articleId);
        return map;
    }

    // 发布评论
    @PostMapping("/comment")
    public Map<String, Object> publishComment(Integer articleId, String content) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        Article article;
        try {
            article = articleRepository.findById(articleId).get();
            map.put("article", article);
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "错误：文章不存在");
            return map;
        }
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        articleRepository.publishComment(id, content, articleId, ctime);
        // 更新文章热度
        article.setHot(article.getHot() + 20);
        articleRepository.save(article);
        map.put("code", 200);
        return map;
    }
    // TODO：编写文章搜索算法,建议使用模糊查询
    // 搜索文章
    @RequestMapping("/search")
    public Map<String, Object> search(String target) {
        // 将String分解为List<String>
        Map<String, Object> map = new HashMap<>();
        List<String> targetList = new ArrayList<String>();
        for (int i = 0; i + 2 < target.length(); i++) {
            targetList.add(target.substring(i, i + 2));
        }
        targetList.add(target.substring(target.length() - 2));

        IndexProcessor pr = new IndexProcessor();
        pr.createIndex("search" + File.separator + "content");
        // pr.createIndex("D:\\GitLib\\AndroidBackend\\demo\\demo\\search\\content");
        Search s = new Search();
        List<Integer> tempret = s.indexSearch("content", targetList);
        List<Article> ret = new ArrayList();
        int hotIncrease = 20;
        for (Integer i : tempret) {
            //热度增加
            Article article = articleRepository.findById(i).get();
            article.setHot(article.getHot() + hotIncrease);
            articleRepository.save(article);
            ret.add(article);
        }
        map.put("article", ret);
        map.put("code", 200);
        return map;
    }
    // 搜索文章
    // 查找文章
    @GetMapping("/fakesearch")
    @ResponseBody
    public Map<String, Object> fakesearch(String target) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> rets = articleRepository.select(target);
        List<Article> articles = new ArrayList<>();
        for (Integer ret : rets) {
            articles.add(articleRepository.findById(ret).get());
        }
        map.put("articles",articles);
        map.put("code", 200);
        return map;
    }

    // 阅读特定文章
    @GetMapping("/read")
    public Map<String, Object> getArticle(Integer articleId) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        Object[] article2;
        Article article;
        try {
            article = articleRepository.findById(articleId).get();
            article2 = articleRepository.findbyid(articleId).get(0);
            String[] strList = {"id", "articlename", "articletheme", "authorid",
                    "content", "iswithdrew", "praisecount", "publishtime"};
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (article2[i] != null) {
                    temp.put(strList[i], article2[i]);
                }
            }
            String path = article.getId()+".txt";
            BufferedReader br = new BufferedReader(
                    new FileReader("search" + File.separator + "content" + File.separator + path));
            String paragraph = null;
            String line = null;
            while ((line = br.readLine()) != null) {
                if (paragraph == null) {
                    paragraph = line;
                } else {
                    paragraph = paragraph + line;
                }
            }
            temp.put("content",paragraph);
            map.put("article", temp);    // TODO:这里直接传递了article，但是这里的content是文章路径，需要修改

        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "错误：文章不存在");
            return map;
        }
        // 拿所有的评论
        List<Object[]> comments;
        try {
            comments = articleRepository.getCommentByArticle(articleId);
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "拿评论时出错");
            return map;
        }
        List<Map<String, Object>> ret = new ArrayList<>();
        // 下面转换一下格式
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
        // 更新浏览记录
        ScanRecord scanRecord = new ScanRecord();
        scanRecord.setArticleId(articleId);
        scanRecord.setArticleName(article.getArticleName());
        scanRecord.setArticleTheme(article.getArticleTheme());

        scanRecord.setUserId(id);
        List<ScanRecord> temp = scanRecordRepository.findAll(Example.of(scanRecord));
        if (temp.size()!=0){
            scanRecord = temp.get(0);
            scanRecord.setScanTime(ctime);
            scanRecordRepository.save(scanRecord);
        }
        else{
            scanRecord.setScanTime(ctime);
            scanRecordRepository.save(scanRecord);
        }


        // 更新文章热度
        article.setHot(article.getHot() + 1);
        articleRepository.save(article);
        map.put("code", 200);
        return map;
    }

    // 收藏/取消收藏文章
    @PostMapping("/favorite")
    @ResponseBody
    public Map<String, Object> favorite(Integer articleId) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        Article article;
        // 先拿到对应文章
        try {
            article = articleRepository.findById(articleId).get();  // TODO:这里好像不涉及文章内容？
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "错误：文章不存在");
            return map;
        }
        //拿到文章作者
        User author;
        try {
            author = userRepository.findById(article.getAuthorId()).get();
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "错误：文章作者不存在");
            return map;
        }
        //判断文章是否被此人收藏过
        List<Object[]> temp;
        try {
            temp = articleRepository.isFavorite(id, articleId);
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "查询收藏出错");
            return map;
        }
        if (temp.size() == 0) {
            //没有收藏，调用收藏接口
            try {
                articleRepository.favorite(id, articleId, "default");
                //200表示收藏
                map.put("code", 200);
                //增加作者总收藏数
                author.setFavoriteCount(author.getFavoriteCount() + 1);
                userRepository.save(author);
                // 更新文章热度
                article.setHot(article.getHot() + 10);
                articleRepository.save(article);

            } catch (Exception e) {
                map.put("code", 402);
                map.put("msg", "收藏出错");
                return map;
            }
        } else {
            //否则调用取消收藏接口
            try {
                articleRepository.cancelFavorite(id, articleId);
                //201表示取消收藏
                map.put("code", 201);
                //减少作者总收藏数
                author.setFavoriteCount(author.getFavoriteCount() - 1);
                userRepository.save(author);
                // 更新文章热度
                article.setHot(article.getHot() - 10);
                articleRepository.save(article);
            } catch (Exception e) {
                map.put("code", 403);
                map.put("msg", "取消收藏出错");
                return map;
            }
        }
        return map;
    }

    // 点赞/取消点赞文章
    @PostMapping("/praiseArticle")
    @ResponseBody
    public Map<String, Object> praise(Integer articleId) {
//        WebSocketServer.sendInfo();
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        Article article;
        // 先拿到对应文章
        try {
            article = articleRepository.findById(articleId).get();  //
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "错误：文章不存在");
            return map;
        }
        // 拿到文章作者
        User author;
        try {
            author = userRepository.findById(article.getAuthorId()).get();
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "错误：文章作者不存在");
            return map;
        }

        // 判断文章是否被此人点赞过
        List<Object[]> temp;
        try {
            temp = articleRepository.isPraised(id, articleId);
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "查询点赞出错");
            return map;
        }
        if (temp.size() == 0) {
            //没有点赞，调用点赞接口
            try {
                articleRepository.praise(id, articleId, ctime);
                //200表示点赞
                map.put("code", 200);
                article.setPraiseCount(article.getPraiseCount() + 1);
                articleRepository.save(article);
                //增加作者总赞数
                author.setPraiseCount(author.getPraiseCount() + 1);
                userRepository.save(author);
                // 更新文章热度
                article.setHot(article.getHot() + 5);
                articleRepository.save(article);


            } catch (Exception e) {
                map.put("code", 402);
                map.put("msg", "点赞出错");
                return map;
            }
        } else {
            //否则调用取消点赞接口
            try {
                articleRepository.cancelPraise(id, articleId);
                //201表示取消点赞
                map.put("code", 201);
                article.setPraiseCount(article.getPraiseCount() - 1);
                articleRepository.save(article);
                //减少作者总赞数
                author.setPraiseCount(author.getPraiseCount() - 1);
                userRepository.save(author);
                // 更新文章热度
                article.setHot(article.getHot() - 5);
                articleRepository.save(article);
            } catch (Exception e) {
                map.put("code", 403);
                map.put("msg", "取消点赞出错");
                return map;
            }
        }
        return map;
    }

    //点赞/取消点赞评论
    @PostMapping("/praiseComment")
    @ResponseBody
    public Map<String, Object> praiseComment(Integer commentId) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        Comment tempComment;
        Article article;
        //先拿到对应评论
        try {
            tempComment = commentRepository.findById(commentId).get();
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "错误：评论不存在");
            return map;
        }
        //拿到对应文章
        try {
            article = articleRepository.findById(tempComment.getfArticleId()).get();
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "错误：文章不存在");
            return map;
        }
        //判断评论是否被此人点赞过
        List<Object[]> temp;
        try {
            temp = articleRepository.isPraisedComment(id, commentId);
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "查询点赞出错");
            return map;
        }

        if (temp.size() == 0) {
            //没有点赞，调用点赞接口
            try {
                articleRepository.praiseComment(id, commentId, ctime);
                //200表示点赞
                map.put("code", 200);
                tempComment.setPraiseCount(tempComment.getPraiseCount() + 1);
                commentRepository.save(tempComment);
                // 更新文章热度
                article.setHot(article.getHot() + 2);
                articleRepository.save(article);
            } catch (Exception e) {
                map.put("code", 402);
                map.put("msg", "点赞出错");
                return map;
            }
        } else {
            //否则调用取消点赞接口
            try {
                articleRepository.cancelPraiseComment(id, commentId);
                //201表示取消点赞
                map.put("code", 201);
                tempComment.setPraiseCount(tempComment.getPraiseCount() - 1);
                commentRepository.save(tempComment);
                // 更新文章热度
                article.setHot(article.getHot() - 2);
                articleRepository.save(article);
            } catch (Exception e) {
                map.put("code", 403);
                map.put("msg", "取消点赞出错");
                return map;
            }
        }
        return map;
    }

    //获取浏览记录
    @GetMapping("/history")
    @ResponseBody
    public Map<String, Object> getHistory() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        List<Object[]> retHistory;
        List<Article> articles = new ArrayList<>();
        List<Map<String, Object>> ret = new ArrayList<>();
        retHistory = articleRepository.gethistory(id);
        //下面转换一下格式
        for (Object[] record : retHistory) {
            articles.add(articleRepository.findById((Integer) record[1]).get());
        }
        map.put("articles", articles);
        map.put("code", 200);
        return map;
    }

    //获取收藏文章
    @GetMapping("/favorite")
    @ResponseBody
    public Map<String, Object> getFavorite() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        List<Integer> retFavorite;
        List<Article> articles = new ArrayList<>();
        List<Map<String, Object>> ret = new ArrayList<>();
        retFavorite = articleRepository.getArticleByFavorite(id);
        for (Integer index : retFavorite) {
            articles.add(articleRepository.findById(index).get());
        }
        map.put("articles", articles);
        map.put("code", 200);
        return map;
    }

    // 拿特定板块的文章
    @GetMapping("/theme")
    @ResponseBody
    public Map<String, Object> getByTheme(String theme) {
        Map<String, Object> map = new HashMap<>();
        List<Object[]> objectLists;
        List<Map<String, Object>> ret = new ArrayList<>();

        objectLists = articleRepository.getArticleByTheme(theme);
        //下面转换一下格式
        String[] strList = {"id", "articlename", "articletheme", "authorid",
                "content", "iswithdrew", "praisecount", "publishtime"};
        for (Object[] record : objectLists) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (record[i] != null) {
                    temp.put(strList[i], record[i]);
                }
            }
            ret.add(temp);
        }

        map.put("articles", ret);
        map.put("code", 200);
        return map;
    }

    // 热搜功能，推荐5个最热文章
    @GetMapping("/hot")
    @ResponseBody
    public Map<String, Object> getByHot() {
        Map<String, Object> map = new HashMap<>();
        List<Object[]> objectLists;
        List<Map<String, Object>> ret = new ArrayList<>();

        objectLists = articleRepository.getArticleByHot();
        //下面转换一下格式
        String[] strList = {"id", "articlename", "articletheme", "authorid",
                "content", "iswithdrew", "praisecount", "publishtime"};
        int index = 0;
        for (Object[] record : objectLists) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (record[i] != null) {
                    temp.put(strList[i], record[i]);
                }
            }
            ret.add(temp);
            index += 1;
            if (index == 5) {
                break;
            }
        }
        map.put("articles", ret);
        map.put("code", 200);
        return map;
    }

    //获取某人的所有文章
    @GetMapping("/person")
    @ResponseBody
    public Map<String, Object> getByPerson(Integer id) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id == -1) {
            //表示拿自己的文章
            id = myUserDetails.getId();
        }
        Map<String, Object> map = new HashMap<>();
        List<Object[]> objectLists;
        List<Map<String, Object>> ret = new ArrayList<>();

        objectLists = articleRepository.getArticleByauthorid(id);
        //下面转换一下格式
        String[] strList = {"id", "articlename", "articletheme", "authorid",
                "content", "iswithdrew", "praisecount", "publishtime"};
        int index = 0;
        for (Object[] record : objectLists) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < strList.length; i++) {
                if (record[i] != null) {
                    temp.put(strList[i], record[i]);
                }
            }
            ret.add(temp);
            index += 1;
            if (index == 5) {
                break;
            }
        }
        map.put("articles", ret);
        map.put("code", 200);
        return map;
    }

    // 返回文章是否被点赞
    @GetMapping("/ispraised")
    @ResponseBody
    public Map<String, Object> isPraised(Integer articleId) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        //判断文章是否被此人点赞过
        List<Object[]> temp;
        try {
            temp = articleRepository.isPraised(id, articleId);
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "查询点赞出错");
            return map;
        }
        if (temp.size() == 0) {
            map.put("code", 200);
            return map;
        } else {
            map.put("code", 201);
            return map;
        }
    }

    // 返回文章是否被收藏
    @GetMapping("/isfavorited")
    @ResponseBody
    public Map<String, Object> isfavorited(Integer articleId) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        //判断文章是否被收藏
        List<Object[]> temp;
        try {
            temp = articleRepository.isFavorite(id, articleId);
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "查询收藏出错");
            return map;
        }
        if (temp.size() == 0) {
            map.put("code", 200);
            return map;
        } else {
            map.put("code", 201);
            return map;
        }
    }

    // 返回评论是否被点赞
    @GetMapping("/commentispraised")
    @ResponseBody
    public Map<String, Object> commentIsPraised(Integer commentId) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        //判断文章是否被收藏
        List<Object[]> temp;
        try {
            temp = articleRepository.isPraisedComment(id, commentId);
        } catch (Exception e) {
            map.put("code", 401);
            map.put("msg", "查询收藏出错");
            return map;
        }
        if (temp.size() == 0) {
            map.put("code", 200);
            return map;
        } else {
            map.put("code", 201);
            return map;
        }
    }

    //获取收藏文章数
    @GetMapping("/favoritecount")
    @ResponseBody
    public Map<String, Object> getArticleCountByFavorite() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        Integer ret = articleRepository.getArticleCountByFavorite(id);
        //下面转换一下格式
        map.put("count", ret);
        map.put("code", 200);
        return map;
    }

    //获取发表文章数
    @GetMapping("/articlecount")
    @ResponseBody
    public Map<String, Object> getArticleByauthorid() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = myUserDetails.getId();
        Map<String, Object> map = new HashMap<>();
        Integer ret = articleRepository.getArticleCountByauthorid(id);
        //下面转换一下格式
        map.put("count", ret);
        map.put("code", 200);
        return map;
    }

    //上传文件
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> handleFormUpload(MultipartFile file
                            ,Integer articleId) {
        Map<String, Object> map = new HashMap<>();
        try{
            if (!file.isEmpty()) {
                new File("downloadfile"+File.separator+articleId.toString()).mkdirs();
                byte[] bytes = file.getBytes();
                File picture = new File("downloadfile"+File.separator
                        +articleId.toString()+File.separator+file.getOriginalFilename());//这里指明上传文件保存的地址
                FileOutputStream fos = new FileOutputStream(picture);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(bytes);
                bos.close();
                fos.close();
                map.put("code", 200);
                return map;
            }
        }catch (IOException e){
            System.out.println(e);
        }
        map.put("code", 400);
        return map;
    }

    //下载文件
    @GetMapping("/download")
    @ResponseBody
    static void download(HttpServletResponse response, Integer articleId){
        String path="downloadfile"+File.separator +articleId.toString();
        File theDir =new File(path);
        File file;
        if (theDir.length()==0){
            response.setHeader("code","201");
        }
        else{
            file = theDir.listFiles()[0];
            ServletOutputStream out = null;
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                //设置文件ContentType类型
                response.setContentType("multipart/form-data");
                //设置文件头：最后一个参数是设置下载文件名
                response.setHeader("Content-Disposition", file.getName());
                out = response.getOutputStream();
                // 读取文件流
                int len = 0;
                byte[] buffer = new byte[1024 * 10];
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
            } catch (FileNotFoundException e) {
                System.out.println("responseFileStream error:FileNotFoundException" + e.toString());
            } catch (Exception e) {
                System.out.println("responseFileStream error:" + e.toString());
            } finally {
                try {
                    out.close();
                    in.close();
                } catch (NullPointerException e) {
                    System.out.println("responseFileStream stream close() error:NullPointerException" + e.toString());
                } catch (Exception e) {
                    System.out.println("responseFileStream stream close() error:" + e.toString());
                }
            }
        }
    }


    // 每天零点调用的函数，让热度下降1/3
    //每天0：00执行
    @Scheduled(cron = "0 00 00 ? * *")
    public void hotDecrease() {
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            article.setHot(article.getHot() * 2 / 3);
            articleRepository.save(article);
        }
    }
}
