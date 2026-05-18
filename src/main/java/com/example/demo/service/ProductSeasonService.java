package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.ProductMapper;

@Service
public class ProductSeasonService {
	private ProductMapper productMapper;

	public ProductSeasonService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	public List<Map<String, Object>> getSeasonProducts() {
		int currentMonth = LocalDate.now().getMonthValue();
		return productMapper.findAllSeason(currentMonth);
	}
}
