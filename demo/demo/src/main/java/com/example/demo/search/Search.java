package com.example.demo.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

public class Search {
    private String INDEX_STORE_PATH = "src\\main\\java\\com\\example\\demo\\search\\index";

    // 利用lucene 索引 搜索
    public void indexSearch(String searchType,String searchKey) {
        try {
            System.out.println("##使用索引方式搜索##");
            System.out.println("======================");
            // 根据索引位置简历 IndexSearch
            IndexSearcher searcher = new IndexSearcher(INDEX_STORE_PATH);
            // 建立搜索单元,searchType 代表要搜索的Field,searchKey代表关键字
            Term t = new Term(searchType,searchKey);
            // 由Term产生 Query
            Query q = new TermQuery(t);
            // 搜索开始时间
            Date beginTime = new Date();
            // 获取一个 <document,frequency>的枚举对象 TermDocs
            TermDocs docs = searcher.getIndexReader().termDocs(t);
            while(docs.next()){
                System.out.print("find " + docs.freq() + " matches in ");
                System.out.println(searcher.getIndexReader().document(docs.doc()).getField("filename").stringValue());

            }
            Date endTime = new Date();
            long timeofsearch = endTime.getTime() - beginTime.getTime();
            System.out.println("总耗时:" + timeofsearch);
//            File file1 = new File("/");
//            System.out.println("/ 代表的绝对路径为" + file1.getAbsolutePath());
//            File file2 = new File(".");
//            System.out.println(". 代表的绝对路径为" + file2.getAbsolutePath());
//            File file3 = new File("");
//            System.out.println(" 代表的绝对路径为" + file3.getAbsolutePath());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

//    public void stringSearch(String keyword,String searchDir){
//        System.out.println("##使用字符串方式搜索##");
//        System.out.println("======================");
//        File filesDir = new File(searchDir);
//        //返回目录文件夹所有文件数组
//        File[] files = filesDir.listFiles();
//        //HM 保存文件名和匹配次数对
//        Map rs = new LinkedHashMap();
//        //搜索开始时间
//        Date beginTime = new Date();
//        for(int i = 0 ;i < files.length ; i ++){
//            int hits = 0;
//            try{
//                BufferedReader br = new BufferedReader(new FileReader(files[i]));
//                StringBuffer sb = new StringBuffer();
//                String line = br.readLine();
//                while(line != null){
//                    sb.append(line);
//                    line = br.readLine();
//                }
//                br.close();
//                // 将 stringBuffer 转化成 String,以便于搜索
//                String stringToSearch = sb.toString();
//                // 从 0 索引 查询 -length + length = 0
//                int fromIndex = -keyword.length();
//                int len = stringToSearch.indexOf(keyword,fromIndex + keyword.length());
//                while((fromIndex = len)!= -1){
//                    hits++;
//                }
//                // 将文件名 和 匹配次数 加入 HashMap
//                rs.put(files[i].getName(), new Integer(hits));
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//        Iterator it = rs.keySet().iterator();
//        while(it.hasNext()){
//            String fileName = (String)it.next();
//            Integer hits = (Integer)rs.get(fileName);
//            System.out.println("find " + hits.intValue() + " matches in "+ fileName);
//        }
//        Date endTime = new Date();
//        long timeOfSearch = endTime.getTime() - beginTime.getTime();
//        System.out.println("使用字符串匹配方式总耗時:" + timeOfSearch + "ms");
//    }

//    public static void main(String[] args) {
//        Search s = new Search();
//        s.indexSearch("content", "卡特");
////        System.out.println();
////        s.stringSearch("卡特", "D:\\GitLib\\AndroidBackend\\demo\\demo\\src\\main\\java\\com\\example\\demo\\search\\content");
////        System.out.println();
//    }

}

