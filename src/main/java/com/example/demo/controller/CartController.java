package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.CartService;

@Controller
public class CartController {
	// カート関連の処理を行うService
	private final CartService cartService;

	// 商品情報をDBから取得するMapper
	private final ProductMapper productMapper;

	// コンストラクタインジェクション
	public CartController(
			CartService cartService,
			ProductMapper productMapper) {
		this.cartService = cartService;
		this.productMapper = productMapper;
	}

	// カート画面表示
	@GetMapping("/cart")
	public String showCart(
			HttpSession session,
			Model model) {

		// sessionからカート情報取得
		List<CartItem> cart = cartService.getCart(session);

		// HTML表示用のデータを格納するList
		List<Map<String, Object>> cartView = new ArrayList<>();

		// 商品合計金額
		int total = 0;

		// カートの商品を1件ずつ処理
		for (CartItem item : cart) {

			// product_idから商品情報取得
			Product product = productMapper.findById(
					item.getProduct_id());

			// 商品情報をまとめるMap
			Map<String, Object> map = new HashMap<>();

			// 商品情報を保存
			map.put("product", product);

			// 数量を保存
			map.put("quantity",
					item.getQuantity());

			// 小計計算
			int subtotal = product.getPrice()
					* item.getQuantity();

			// 小計を保存
			map.put("subtotal", subtotal);

			// 商品合計に追加
			total += subtotal;

			// cartViewに追加
			cartView.add(map);
		}
		// 画面へ商品一覧を渡す
		model.addAttribute(
				"cartItems",
				cartView);
		// 商品合計を渡す
		model.addAttribute(
				"total",
				total);

		// 送料
		int shipping = 0;

		// 商品が1つでもあれば送料800円
		if (total > 0) {

			shipping = 800;

		}

		// 送料を画面へ渡す
		model.addAttribute(
				"shipping",
				shipping);

		// 合計金額を画面へ渡す
		model.addAttribute(
				"grandTotal",
				total + shipping);

		// cart.html を表示
		return "cart";
	}

	// 商品追加
	@GetMapping("/cart/add")
	public String addCart(
			HttpSession session) {

		// sessionからカート取得
		List<CartItem> cart = cartService.getCart(session);

		// 新しいカート商品作成
		CartItem item = new CartItem();

		// 商品IDを設定
		item.setProduct_id(1);

		// 数量を設定
		item.setQuantity(1);

		// カートへ追加
		cart.add(item);

		// カート画面へ戻る
		return "redirect:/cart";
	}

	// 商品削除
	@PostMapping("/cart/remove")
	public String removeCart(
			@RequestParam("productId") int productId,
			HttpSession session) {

		// 指定商品を削除
		cartService.removeItem(session, productId);

		return "redirect:/cart";
	}

	// 数量増加
	@PostMapping("/cart/increase")
	public String increase(
			@RequestParam("productId") int productId,
			HttpSession session) {

		// 数量を1増やす
		cartService.increaseQuantity(
				session,
				productId);

		return "redirect:/cart";
	}

	// 数量減少
	@PostMapping("/cart/decrease")
	public String decrease(
			@RequestParam("productId") int productId,
			HttpSession session) {

		// 数量を1減らす
		cartService.decreaseQuantity(
				session,
				productId);

		return "redirect:/cart";
	}
}