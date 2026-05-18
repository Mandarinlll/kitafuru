package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Product;

@Mapper
public interface ProductMapper {
	// 商品一覧取得
	List<Product> findAll();

	List<Map<String, Object>> findAllSeason(int month);

	List<Product> findRecommendProducts(Integer userId);

	List<Product> searchForAi(String keyword);

	List<Product> searchForAiWithMaxPrice(String keyword, Integer maxPrice);

	List<Product> findByIds(List<Integer> ids);
}
