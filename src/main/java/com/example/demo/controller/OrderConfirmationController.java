package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;

@Controller
public class OrderConfirmationController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductService productService;

	@GetMapping("/order-confirmation")
	public String showDetail(
			Model model,
			HttpSession session) {

		// ユーザー取得（仮）
		User user = userRepository.findById(1);

		// セッションからカート取得
		List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

		// null対策
		if (cartItems == null) {
			cartItems = new ArrayList<>();
		}

		int subtotal = 0;

		List<Map<String, Object>> displayItems = new ArrayList<>();

		for (CartItem item : cartItems) {

			// 商品取得
			Product product = productService.findById(
					item.getProduct_id());

			// 小計
			int itemSubtotal = product.getPrice()
					* item.getQuantity();

			subtotal += itemSubtotal;

			// 表示用データ
			Map<String, Object> data = new HashMap<>();

			data.put(
					"name",
					product.getName());

			data.put(
					"price",
					product.getPrice());

			data.put(
					"quantity",
					item.getQuantity());

			data.put(
					"subtotal",
					itemSubtotal);

			displayItems.add(data);
		}

		// 送料
		int shipping = 800;

		// ギフト料金
		int gift = 0;

		// 合計
		int total = subtotal
				+ shipping
				+ gift;

		model.addAttribute(
				"user",
				user);

		model.addAttribute(
				"displayItems",
				displayItems);

		model.addAttribute(
				"subtotal",
				subtotal);

		model.addAttribute(
				"shipping",
				shipping);

		model.addAttribute(
				"gift",
				gift);

		model.addAttribute(
				"total",
				total);

		return "order-confirmation";
	}
}