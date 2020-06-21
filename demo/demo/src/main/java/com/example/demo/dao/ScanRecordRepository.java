package com.example.demo.dao;

import com.example.demo.domain.ScanRecord;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ScanRecordRepository extends JpaRepository<ScanRecord, Integer> {

}
