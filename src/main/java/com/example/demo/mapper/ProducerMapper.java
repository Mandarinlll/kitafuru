package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Producer;

@Mapper
public interface ProducerMapper {

	List<Producer> findAll();

	Producer findById(Integer id);

}