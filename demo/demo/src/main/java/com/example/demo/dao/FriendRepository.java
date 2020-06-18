package com.example.demo.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
