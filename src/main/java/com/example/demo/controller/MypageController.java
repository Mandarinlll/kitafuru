package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.service.OrderService;

@Controller
public class MypageController {

	private final OrderService orderService;

	public MypageController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/mypage-order")
	public String showMyPage(HttpSession session, Model model) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}

		// 最近の注文2件を取得
		List<Order> recentOrders = orderService.getRecentOrders(loginUser.getId());

		model.addAttribute("user", loginUser);
		model.addAttribute("recentOrders", recentOrders);

		return "mypage-order";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}