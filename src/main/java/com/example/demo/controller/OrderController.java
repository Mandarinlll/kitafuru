package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;

@Controller
public class OrderController {

	// 注文確認画面表示
	@GetMapping("/order/confirm")
	public String confirmOrder(
			HttpSession session) {

		return "order-confirmation";
	}

	// 注文完了画面表示
	@GetMapping("/order-complete")
	public String orderComplete(

			@RequestParam(value = "paymentMethod", required = false) String paymentMethod,

			Model model,
			HttpSession session) {

		// paymentMethodが送られていない時
		if (paymentMethod == null) {
			paymentMethod = "クレジットカード";
		}

		// session保存
		session.setAttribute(
				"paymentMethod",
				paymentMethod);

		// 合計金額取得
		Integer totalPrice = (Integer) session.getAttribute("totalPrice");

		if (totalPrice == null) {
			totalPrice = 0;
		}

		// ログインユーザー取得
		User user = (User) session.getAttribute("loginUser");
		//ログインしていない場合は、ログインページにリダイレクト
		if (user == null) {
			return "redirect:/login";
		}

		// 注文番号生成
		String date = LocalDate.now()
				.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		int random = (int) (Math.random() * 9000) + 1000;

		String orderNumber = "HKD-" + date + "-" + random;

		// お届け予定日
		LocalDate deliveryDate = LocalDate.now().plusDays(3);

		// HTMLへ渡す
		model.addAttribute(
				"orderNumber",
				orderNumber);

		model.addAttribute(
				"deliveryDate",
				deliveryDate);

		model.addAttribute(
				"totalPrice",
				totalPrice);

		model.addAttribute(
				"paymentMethod",
				paymentMethod);

		model.addAttribute(
				"user",
				user);

		return "order-complete";
	}
}