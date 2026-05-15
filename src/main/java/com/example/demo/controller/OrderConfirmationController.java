package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderConfirmationController {
	@GetMapping("/order-confirmation")
	public String showDetail() {
		//producer/detail.htmlを表示する
		return "/order-confirmation";
	}

}
