package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
	List<Product> findAllSeason(int month);

	List<Product> findRecommendProducts(Integer userId);
}
