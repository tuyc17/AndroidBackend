package com.example.demo.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import jeasy.analysis.MMAnalyzer;

public class IndexProcessor {
    // 索引存储目录
    private String INDEX_STORE_PATH = "src\\main\\java\\com\\example\\demo\\search\\index";
    // 创建索引
    public void createIndex(String inputDir){
        try {
            // 利用分词工具创建 IndexWriter
            IndexWriter writer = new IndexWriter(INDEX_STORE_PATH,new MMAnalyzer(),true);
            File filesDir = new File(inputDir);
            // 取得 要建立 索引的文件数组
            File[] files = filesDir.listFiles();
            for(int i = 0 ; i < files.length; i++){
                String fileName = files[i].getName();
                if(fileName.substring(fileName.lastIndexOf(".")).equals(".txt")){
                    // 创建新的Document
                    Document doc = new Document();
                    // 为文件名创建一个 Field
                    Field field = new Field("filename",files[i].getName(),Field.Store.YES,Field.Index.TOKENIZED);
                    doc.add(field);
                    field = new Field("content",loadFileToString(files[i]),Field.Store.NO,Field.Index.TOKENIZED);
                    doc.add(field);
                    // 把Document加入 IndexWriter
                    writer.addDocument(doc);
                }
            }
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    //加载文档 生成字符串
    public String loadFileToString(File f) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while(line != null){
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args){
//        IndexProcessor pr = new  IndexProcessor();
//        pr.createIndex("src\\main\\java\\com\\example\\demo\\search\\content");
//    }
}