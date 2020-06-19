package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域攻击防护
        http.csrf().disable();
        http
                // .ignoring().antMatchers()
                .authorizeRequests()
                .antMatchers("/user/**", "/login","/friend/**","/article/**") // 不需要登录就可以访问
                .permitAll()
                .antMatchers("/userOnly/**").hasAnyRole("USER") // 需要具有ROLE_USER角色才能访问
                .antMatchers("/adminOnly/**").hasAnyRole("ADMIN") // 需要具有ROLE_ADMIN角色才能访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // 设置登录页面
                .loginProcessingUrl("/authentication/form")
                .defaultSuccessUrl("/user/getAllUser") // 设置默认登录成功后跳转的页面
        ;
    }

    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // 重写方法，自定义用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("tyc").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN","USER");
        auth.inMemoryAuthentication().withUser("szp").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
    }
}