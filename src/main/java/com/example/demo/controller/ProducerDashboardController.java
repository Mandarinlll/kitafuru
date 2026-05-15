package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProducerDashboardController {
	@GetMapping("/producer-dashboard")
	public String showDetail() {
		//producer/detail.htmlを表示する
		return "producer/dashboard";
	}
}