//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableConfigurationProperties(SecurityProperties.class)
//public class MySecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private SecurityProperties properties;
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeRequests()
//                .antMatchers("/user/**")
//                .permitAll()// 允许匿名访问的地址
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated() // 其它地址都需要进行认证
//                .and()
//                .formLogin() // 启用表单登录
//                //.loginPage(properties.getLoginPage()) // 登录页面
//                .loginPage("/login") // 登录页面
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/") // 默认登录成功后的跳转地址 //.permitAll()
//                .and()
////                .authorizeRequests() // 授权配置
////                .antMatchers("/login.html").permitAll() // login界面全部授权
////                .anyRequest() // 对所有请求生效
////                .authenticated() // 都需要认证
////                .and().csrf().disable(); // 关闭csrf，否则会导致登录不成功
//                .httpBasic();// http弹框验证-alert形式
//    }
//}