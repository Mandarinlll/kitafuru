package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Product;

/*
 * DB操作を行うMapper
 */
@Mapper
public interface ProductMapper {

	// 商品一覧取得
	List<Product> findAll();
}