package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Product;

@Mapper
public interface ProductMapper {
	List<Product> findAllSeason(int month);

	List<Product> findRecommendProducts(Integer userId);
}
