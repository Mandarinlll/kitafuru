package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

@Service
public class AIRecommendService {
	private final ProductMapper productMapper;

	public AIRecommendService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	public List<Product> findRecommendProducts(Integer userId) {
		return productMapper.findRecommendProducts(userId);
	}
}
