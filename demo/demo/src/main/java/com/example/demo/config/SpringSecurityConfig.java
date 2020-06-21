package com.example.demo.config;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService myCustomUserService;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域攻击防护
        http.csrf().disable();
        http.authenticationProvider(authenticationProvider());
        http.httpBasic()
                //未登录时，进行json格式的提示，很喜欢这种写法，不用单独写一个又一个的类
                .authenticationEntryPoint((request,response,authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",403);
                    map.put("message","未登录");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                });
        http
                // .ignoring().antMatchers()
                .authorizeRequests()
                .antMatchers( "/login", "/user/logon") // 不需要登录就可以访问
                .permitAll()
//                .antMatchers("/user/**","/friend/**","/article/**").hasAnyRole("USER") // 需要具有ROLE_USER角色才能访问
//                .antMatchers("/adminOnly/**").hasAnyRole("ADMIN") // 需要具有ROLE_ADMIN角色才能访问
                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login") // 设置登录页面
//                .loginProcessingUrl("/authentication/form")
//                .defaultSuccessUrl("/user/getAllUser") // 设置默认登录成功后跳转的页面

                .and()
                .formLogin() //使用自带的登录
                .permitAll()
                //登录失败，返回json
                .failureHandler((request,response,ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",401);
                    if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
                        map.put("message","用户名或密码错误");
                    } else if (ex instanceof DisabledException) {
                        map.put("message","账户被禁用");
                    } else {
                        map.put("message","登录失败!");
                    }
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                //登录成功，返回json
                .successHandler((request,response,authentication) -> {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",200);
                    map.put("message","登录成功");
                    map.put("data",authentication.getPrincipal());
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .exceptionHandling()
                //没有权限，返回json
                .accessDeniedHandler((request,response,ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",403);
                    map.put("message", "权限不足");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                //退出成功，返回json
                .logoutSuccessHandler((request,response,authentication) -> {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",200);
                    map.put("message","退出成功");
                    map.put("data",authentication);
                    response.setContentType("application/json;charset=utf-8");

                    //此处设置下线
                    //此处设置isonline,lastlogin
                    MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
                    User tempuser;
                    try {
                        tempuser = userRepository.findById(myUserDetails.getId()).get();
                    }
                    catch (Exception e){
                        throw new UsernameNotFoundException("not found");
                    }
                    tempuser.setOnline(false);
                    userRepository.save(tempuser);
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
        ;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(myPasswordEncoder);
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(myCustomUserService);
        return authenticationProvider;
    }
}