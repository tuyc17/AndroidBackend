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

    // 利用lucene索引搜索（被搜查的对象：searchLit）（返回对象：由文章的id按匹配数从大到小排序的长度不超过20的listRet）
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
                    //System.out.print("find " + times + " matches in " + file);
                    // System.out.println("\n");
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
                //System.out.println(result);
                // System.out.println("\n");
//            Date endTime = new Date();
//            long timeofsearch = endTime.getTime() - beginTime.getTime();
//            System.out.println("总耗时:" + timeofsearch);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        // result转化为list形式并排序
        List<Map.Entry<String, Integer>> listTmp = new ArrayList<Map.Entry<String, Integer>>(result.entrySet()); //转换为list
        listTmp.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        // 将listTmp的前若干个（这个若干待定，暂定为20），写入返回list<Integer id>中
        List<Integer> listRet = new ArrayList<Integer>();
        for (int i = 0; i < listTmp.size() && i < 20; i++) {
            // 将1.txt(String)转化为1(Integer)
            String content = listTmp.get(i).getKey();
            int dot = content.lastIndexOf('.');
            String cut = content.substring(0, dot);
            listRet.add(Integer.valueOf(cut));
            // System.out.println(content + ": " + cut);
        }
        System.out.println(listRet);
        return listRet;
    }


//    public static void main(String[] args) {
////        IndexProcessor pr = new  IndexProcessor();
////        pr.createIndex("src\\main\\java\\com\\example\\demo\\search\\content");
////        Search s = new Search();
////        List<String> searchWords = new ArrayList<>();
////        searchWords.add("卡特");
////        searchWords.add("英雄");
////        s.indexSearch("content", searchWords);//"卡特");
//        // 尝试将String分解为List<String>
//        String target = "米兰大公、枢机卿、教皇厅国务圣省长官";
//        List<String> targetList = new ArrayList<String>();
//        for(int i = 0; i + 2 < target.length(); i++) {
//            targetList.add(target.substring(i, i+2));
//        }
//        targetList.add(target.substring(target.length()-2));
//
//        IndexProcessor pr = new  IndexProcessor();
//        pr.createIndex("src\\main\\java\\com\\example\\demo\\search\\content");
//        Search s = new Search();
//        System.out.println(s.indexSearch("content", targetList));
//    }
}

