package com.example.demo.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

public class Search {
    private String INDEX_STORE_PATH = "src\\main\\java\\com\\example\\demo\\search\\index";

    // 利用lucene索引搜索（被搜查的对象：searchKey）（进阶修改：将searchKey改为一个List）
    public List indexSearch(String searchType, List<String> searchList) {
        System.out.println("##使用索引方式搜索##");
        System.out.println("======================");
        Map<String, Integer> result = new HashMap<>();
        for(int i = 0; i < searchList.size(); i++) {
            String searchKey = searchList.get(i);
            try {
                // 根据索引位置简历 IndexSearch
                IndexSearcher searcher = new IndexSearcher(INDEX_STORE_PATH);
                // 建立搜索单元,searchType 代表要搜索的Field,searchKey代表关键字
                Term t = new Term(searchType, searchKey);
                // 由Term产生 Query
                Query q = new TermQuery(t);
                // 搜索开始时间
//            Date beginTime = new Date();
                // 获取一个 <document,frequency>的枚举对象 TermDocs
                TermDocs docs = searcher.getIndexReader().termDocs(t);
                while (docs.next()) {
                    String file = searcher.getIndexReader().document(docs.doc()).getField("filename").stringValue();
                    Integer times = docs.freq();
//                    // 在searcher.getIndexReader().document(docs.doc()).getField("filename").stringValue()文件中找到了docs.freq()次匹配
//                    System.out.print("find " + times + " matches in " + file);
//                    System.out.println("\n");
                    // 查找key是否存在
                    if (!result.containsKey(file)) {
                        // 不存在，新建
                        result.put(file, times);
                    } else {
                        // 存在，增加
                        result.compute(file, (key, value) -> (Integer) value + times);
                    }
                }
//                // 遍历结束后result中存储了每个文件中获得的searchKey的匹配次数
//                System.out.println(result);
//                System.out.println("\n");
//            Date endTime = new Date();
//            long timeofsearch = endTime.getTime() - beginTime.getTime();
//            System.out.println("总耗时:" + timeofsearch);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        // result转化为list形式并排序
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(result.entrySet()); //转换为list
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
        }
        return list;
    }


    public static void main(String[] args) {
        IndexProcessor pr = new  IndexProcessor();
        pr.createIndex("src\\main\\java\\com\\example\\demo\\search\\content");
        Search s = new Search();
        List<String> searchWords = new ArrayList<>();
        searchWords.add("卡特");
        searchWords.add("英雄");
        s.indexSearch("content", searchWords);//"卡特");
    }
}

