package com.example.demo.config;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
/**
 * 登录专用类
 * 自定义类，实现了UserDetailsService接口，用户登录时调用的第一类
 *
 */
@Component
public class MyCustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
    @Override
    public UserDetails loadUserByUsername(String studentid) throws UsernameNotFoundException {
        //在这里可以自己调用数据库，对username进行查询，看看在数据库中是否存在
        MyUserDetails myUserDetail = new MyUserDetails();
        java.sql.Timestamp ctime = new java.sql.Timestamp(new java.util.Date().getTime());
        User tempuser;
        try {
            tempuser = userRepository.findBystudentId(studentid);
        }
        catch (Exception e){
            throw new UsernameNotFoundException("not found");
        }
        if (tempuser==null){
            throw new UsernameNotFoundException("not found");
        }
        //此处设置isonline,lastlogin
        tempuser.setLastLogin(ctime);
        tempuser.setOnline(true);
        userRepository.save(tempuser);
        myUserDetail.setUsername(tempuser.getUserName());
        myUserDetail.setPassword(tempuser.getPassWord());
        myUserDetail.setId(tempuser.getId());
        return myUserDetail;
    }
}
