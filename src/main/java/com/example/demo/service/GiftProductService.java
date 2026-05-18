package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

@Service
public class GiftProductService {

	private final ProductMapper productMapper;

	//ProductMapperを受け取り代入
	public GiftProductService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	//人気ギフトTOP3を取得
	public List<Product> findTopGiftProducts() {
		return productMapper.findTopGiftProducts();
	}

	//その他ギフト商品を取得
	public List<Product> findOtherGiftProducts() {
		return productMapper.findOtherGiftProducts();
	}
}