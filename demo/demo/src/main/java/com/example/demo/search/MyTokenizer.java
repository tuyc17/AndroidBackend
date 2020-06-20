//package com.example.demo.search;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import net.sf.classifier4J.ITokenizer;
//import org.apache.lucene.analysis.tokenattributes.TermAttribute;
//import org.wltea.analyzer.cfg.Configuration;
//import org.wltea.analyzer.cfg.DefaultConfig;
//import org.wltea.analyzer.dic.Dictionary;
//import org.wltea.analyzer.lucene.IKTokenizer;
//
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//
///**
// * 中文分词器类
// *
// * @author CSD
// *
// */
//@SuppressWarnings("deprecation")
//public class MyTokenizer implements ITokenizer {
//
//    private static final Logger logger = LogManager.getLogger(MyTokenizer.class);
//
//    private List<String> list;
//    private String[] strArray;
//    private static Collection<String> exwordc = new ArrayList<>();
//    private static String exdict = "exdict.dic";
//
//    // 加载新增词库
//    static {
//
//        try {
//            File file = new File(exdict);
//            FileInputStream fin = new FileInputStream(file);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                exwordc.add(line.trim());
//            }
//            reader.close();
//            logger.info("加载词典::" + exdict);
//            // 增加词库
//            Configuration cfg = DefaultConfig.getInstance();
//            Dictionary dict = Dictionary.initial(cfg);
//            dict.addWords(exwordc);
//        } catch (IOException e) {
//            logger.error(e + "------------------加载词典出错，请确认词典文件！------------------");
//        }
//    }
//
//    /**
//     * 分词，返回分词数组
//     *
//     * @param input
//     *            文本字符串
//     * @return String[]
//     */
//    public String[] tokenize(String input) {
//        list = new ArrayList<String>();
//
//        IKTokenizer tokenizer = new IKTokenizer(new StringReader(input), true);
//        try {
//            while (tokenizer.incrementToken()) {
//                TermAttribute termAtt = (TermAttribute) tokenizer.getAttribute(TermAttribute.class);
//                String str = termAtt.term();
//                list.add(str);
//            }
//        } catch (IOException e) {
//            logger.error(e + "------------------分词出错------------------");
//        }
//        strArray = new String[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            strArray[i] = (String) list.get(i);
//        }
//
//        return strArray;
//    }
//
//}