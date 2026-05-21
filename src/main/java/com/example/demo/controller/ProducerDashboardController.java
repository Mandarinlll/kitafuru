package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Producer;
import com.example.demo.model.ProducerDashboardSummary;
import com.example.demo.service.ProducerDashboardService;

@Controller
public class ProducerDashboardController {
	private final ProducerDashboardService producerDashboardService;

	public ProducerDashboardController(ProducerDashboardService producerDashboardService) {
		this.producerDashboardService = producerDashboardService;
	}

	@GetMapping("/producer-dashboard")
	public String showDetail(Model model, HttpSession session) {
		Producer loginProducer = (Producer) session.getAttribute("loginProducer");
		if (loginProducer == null) {
			return "redirect:/producer/login";
		}
		int producerId = loginProducer.getId();
		ProducerDashboardSummary summary = producerDashboardService.getSummary(producerId);

		model.addAttribute("summary", summary);
		model.addAttribute("businessDate", summary.getBusinessDate());
		model.addAttribute("salesReports", producerDashboardService.getSalesReports(producerId));
		return "producer/dashboard";
	}
}
