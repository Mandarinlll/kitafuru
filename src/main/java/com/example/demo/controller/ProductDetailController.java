package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.service.ProducerProfileService;
import com.example.demo.service.ProductService;

/*
 * 商品詳細画面Controller
 */
@Controller
public class ProductDetailController {

	// 商品処理Service
	private final ProductService productService;
	private final ProducerProfileService producerProfileService;

	// コンストラクタ
	public ProductDetailController(
			ProductService productService,
			ProducerProfileService producerProfileService) {

		this.productService = productService;
		this.producerProfileService = producerProfileService;
	}

	/*
	 * 商品詳細画面
	 *
	 * /products/1
	 * のようなURLを受け取る
	 */
	@GetMapping("/products/{product_id}")
	public String showDetail(
			@PathVariable("product_id") int productId,
			Model model) {

		// 商品取得
		Product product = productService.getProductById(productId);

		addDetailAttributes(model, product, 1);

		// product/detail.html
		return "product/detail";
	}

	/*
	 * 数量増加
	 */
	@PostMapping("/products/increase")
	public String increaseQuantity(
			@RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity,
			Model model) {

		// 数量を増やす
		quantity++;

		// 商品取得
		Product product = productService.getProductById(productId);

		addDetailAttributes(model, product, quantity);

		// detail画面表示
		return "product/detail";
	}

	/*
	 * 数量減少
	 */
	@PostMapping("/products/decrease")
	public String decreaseQuantity(
			@RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity,
			Model model) {

		// 1以下にならないようにする
		if (quantity > 1) {

			quantity--;
		}

		// 商品取得
		Product product = productService.getProductById(productId);

		addDetailAttributes(model, product, quantity);

		// detail画面表示
		return "product/detail";
	}

	private void addDetailAttributes(Model model, Product product, int quantity) {
		model.addAttribute("product", product);
		model.addAttribute("producer", producerProfileService.getProfile(product.getProducerId()));
		model.addAttribute("quantity", quantity);
	}
}
