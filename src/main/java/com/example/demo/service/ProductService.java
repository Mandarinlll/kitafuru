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

		return productMapper.findAll();
	}

	/*
	 * 商品詳細取得
	 */
	public Product getProductById(int id) {

		return productMapper.findDetailById(id);
	}

	/*
	 * 商品ID取得
	 * （注文確認画面用）
	 */
	public Product findById(int id) {

		return getProductById(id);
	}

	public List<Product> getProductsByProducerId(
			int producerId,
			int limit) {

		return productMapper.findByProducerId(
				producerId);
	}

	public List<Product> findTopGiftProducts() {

		return productMapper.findTopGiftProducts();
	}

	public List<Product> findOtherGiftProducts() {

		return productMapper.findOtherGiftProducts();
	}
}