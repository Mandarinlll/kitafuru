package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Producer;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProducerMapper;
import com.example.demo.service.ProductService;

/*
 * 商品一覧画面のController
 */
@Controller
public class ProductListController {

	// 商品取得用Service
	private final ProductService productService;

	// 生産者取得用Mapper
	private final ProducerMapper producerMapper;

	// コンストラクタ
	public ProductListController(
			ProductService productService,
			ProducerMapper producerMapper) {

		this.productService = productService;
		this.producerMapper = producerMapper;
	}

	/*
	 * /products にアクセスされた時の処理
	 */
	@GetMapping("/products")
	public String showProductList(Model model) {

		// DBから商品一覧取得
		List<Product> productList = productService.getAllProducts();

		// 生産者一覧取得
		List<Producer> producerList = producerMapper.findAll();

		// producerId → producerName のMap作成
		Map<Integer, String> producerMap = new HashMap<>();

		for (Producer producer : producerList) {
			producerMap.put(producer.getId(), producer.getName());
		}

		// HTMLへ渡す
		model.addAttribute("productList", productList);
		model.addAttribute("producerMap", producerMap);

		// product/list.html を表示
		return "product/list";
	}
}