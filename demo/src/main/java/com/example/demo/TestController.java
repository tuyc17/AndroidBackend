package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping //是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径
@RestController //用来返回Json
public class TestController {
    class Info {
        private String id;
        private String data;

        public String getId() {
            return id;
        }

        public String getData() {
            return data;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    /**
     * 注解中的第一个参数是访问的路径，第二个参数是请求方式
     * @return
     */
//    @RequestMapping(value = "/info", method = RequestMethod.GET)
//    public Info getInfo(){
//        Info info = new Info();
//        info.id = "59192";
//        info.data = "It's name is too complaint...";
//        return info;
//    }
}
