package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Producer;

@Mapper
public interface ProducerMapper {

	// 生産者一覧
	List<Producer> findAll();

	// 生産者詳細（ID検索）
	Producer findById(Integer id);
}