package com.example.demo.dao;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import com.example.demo.domain.Friend;
import com.example.demo.domain.ChatRecord;
import org.apache.ibatis.annotations.Select;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {


    // 获取所有关注的人
    @Query(value = "select friendid from friend where userid = :id",nativeQuery = true)
    List<Integer> getAllFriend(@Param("id") Integer id);

    // 获取所有关注你的人
    @Query(value = "select userid from friend where friendid = :id",nativeQuery = true)
    List<Integer> getAllFriended(@Param("id") Integer id);

    // 判断某人是否为好友
    @Query(value = "select * from friend where userid = :id and friendid = :friendId",nativeQuery = true)
    List<Object[]> isFriend(@Param("id") Integer id,@Param("friendId") Integer friendId);
    // 拿聊天记录
    @Query(value = "select id, content, isread, receiverid, sendtime, senderid" +
            " from chatrecord where (senderid = :id and receiverid = :friendId)or(senderid = :friendId and receiverid = :id)",nativeQuery = true)
    List<Object[]> getMsg(@Param("id") Integer id,@Param("friendId") Integer friendId);
    // 拿聊天记录同时记得update信息为已读

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
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO chatrecord(senderid, receiverid, content, isread, sendtime) VALUES (:id, :friendId,:content,1,:sendtime);" ,nativeQuery = true)
    int sendMsg(@Param("id") Integer id,@Param("friendId") Integer friendId,@Param("content") String content,@Param("sendtime")java.sql.Timestamp sendtime);
    // 发送添加好友请求
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO friendrequest(userid, targetid, ishandled, isrejected, requesttime, verifyinfo) VALUES (:id,:friendId,0,0,:sendtime,:verifyinfo);" ,nativeQuery = true)
    int sendRequest(@Param("id") Integer id,@Param("friendId") Integer friendId,@Param("sendtime")java.sql.Timestamp sendtime,@Param("verifyinfo") String verifyinfo);

    // 接受添加好友请求
    @Modifying
    @Transactional
    @Query(value = "UPDATE friendrequest SET  ishandled = 1, isrejected = 0 WHERE (userid = :friendId) and (targetid = :id);" ,nativeQuery = true)
    int admitRequest(@Param("id") Integer id,@Param("friendId") Integer friendId);
    // 拒绝添加好友请求
    @Modifying
    @Transactional
    @Query(value = "UPDATE friendrequest SET  ishandled = 0, isrejected = 1 WHERE (userid = :id) and (targetid = :friendId);" ,nativeQuery = true)
    int rejectRequest(@Param("id") Integer id,@Param("friendId") Integer friendId);
    // 获取当前未处理的好友请求
    @Query(value = "select userid, targetid, ishandled, isrejected, rejecttime, requesttime" +
            " from friendrequest where targetid = :id  and ishandled = 0",nativeQuery = true)
    List<Object[]> getRequest(@Param("id") Integer id);
}
