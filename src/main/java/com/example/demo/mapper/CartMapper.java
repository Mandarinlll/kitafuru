package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {

	List<Map<String, Object>> findByUserId(int userId);
}