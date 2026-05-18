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

	private final CartService cartService;
	private final ProductMapper productMapper;

	public CartController(
			CartService cartService,
			ProductMapper productMapper) {
		this.cartService = cartService;
		this.productMapper = productMapper;
	}

	@GetMapping("/cart")
	public String showCart(
			HttpSession session,
			Model model) {

		List<CartItem> cart = cartService.getCart(session);

		List<Map<String, Object>> cartView = new ArrayList<>();

		int total = 0;

		for (CartItem item : cart) {

			Product product = productMapper.findById(
					item.getProduct_id());

			Map<String, Object> map = new HashMap<>();

			map.put("product", product);

			map.put("quantity",
					item.getQuantity());

			int subtotal = product.getPrice()
					* item.getQuantity();

			map.put("subtotal", subtotal);

			total += subtotal;

			cartView.add(map);
		}

		model.addAttribute(
				"cartItems",
				cartView);

		model.addAttribute(
				"total",
				total);

		int shipping = 0;

		if (total > 0) {

			shipping = 800;

		}

		model.addAttribute(
				"shipping",
				shipping);

		model.addAttribute(
				"grandTotal",
				total + shipping);

		return "cart";
	}

	@GetMapping("/cart/add")
	public String addCart(
			HttpSession session) {

		List<CartItem> cart = cartService.getCart(session);

		CartItem item = new CartItem();

		item.setProduct_id(1);

		item.setQuantity(1);

		cart.add(item);

		return "redirect:/cart";
	}

	@PostMapping("/cart/remove")
	public String removeCart(
			@RequestParam("productId") int productId,
			HttpSession session) {

		cartService.removeItem(session, productId);

		return "redirect:/cart";
	}

	@PostMapping("/cart/increase")
	public String increase(
			@RequestParam("productId") int productId,
			HttpSession session) {

		cartService.increaseQuantity(
				session,
				productId);

		return "redirect:/cart";
	}

	@PostMapping("/cart/decrease")
	public String decrease(
			@RequestParam("productId") int productId,
			HttpSession session) {

		cartService.decreaseQuantity(
				session,
				productId);

		return "redirect:/cart";
	}
}