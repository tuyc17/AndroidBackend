package com.example.demo.dao;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Friend;
import com.example.demo.domain.ChatRecord;
import org.apache.ibatis.annotations.Select;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    // 获取所有好友
    @Query(value = "select friendid from friend where userid = :id",nativeQuery = true)
    List<Integer> getAllFriend(@Param("id") Integer id);
    // 判断某人是否为好友
    @Query(value = "select * from friend where userid = :id and friendid = :friendId",nativeQuery = true)
    List<Object[]> isFriend(@Param("id") Integer id,@Param("friendId") Integer friendId);
    // 拿聊天记录
    @Query(value = "select * from chatrecord where (senderid = :id and receiverid = :friendId)or(senderid = :friendId and receiverid = :id)",nativeQuery = true)
    List<Object[]> getMsg(@Param("id") Integer id,@Param("friendId") Integer friendId);
    // 添加好友
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO friend(userid, friendid) VALUES (:id, :friendId);" ,nativeQuery = true)
    int addFriend(@Param("id") Integer id,@Param("friendId") Integer friendId);

    // 删除好友
    @Modifying
    @Transactional
    @Query(value = "DELETE from friend WHERE (userid = :id) and (friendid = :friendId);" ,nativeQuery = true)
    int deleteFriend(@Param("id") Integer id,@Param("friendId") Integer friendId);

    // 向好友发送信息
    // 发送添加好友请求
    // 接受添加好友请求
    // 获取当前好友请求
}
