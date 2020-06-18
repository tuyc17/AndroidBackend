package com.example.demo.dao;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Friend;
import org.apache.ibatis.annotations.Select;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Query(value = "select friendid from friend where userid = :id",nativeQuery = true)
    List<Integer> getAllFriend(@Param("id") Integer id);
}
