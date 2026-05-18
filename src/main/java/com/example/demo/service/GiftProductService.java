package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

@Service
public class GiftProductService {

	private final ProductMapper productMapper;

	public GiftProductService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	public List<Product> findTopGiftProducts() {
		return productMapper.findTopGiftProducts();
	}

	public List<Product> findOtherGiftProducts() {
		return productMapper.findOtherGiftProducts();
	}
}