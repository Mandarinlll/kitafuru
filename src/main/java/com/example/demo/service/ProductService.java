package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

/*
 * 商品処理を行うService
 */
@Service
public class ProductService {

	// DB操作用Mapper
	private final ProductMapper productMapper;

	// Mapperを受け取る
	public ProductService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	/*
	 * 商品一覧取得
	 */
	public List<Product> getAllProducts() {

		// Mapperから商品取得
		return productMapper.findAll();
	}

	public List<Product> findTopGiftProducts() {
		return productMapper.findTopGiftProducts();
	}

	public List<Product> findOtherGiftProducts() {
		return productMapper.findOtherGiftProducts();
	}
}