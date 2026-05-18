package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.ProducerDashboardSummary;
import com.example.demo.service.ProducerDashboardService;

@Controller
public class ProducerDashboardController {
	private final ProducerDashboardService producerDashboardService;

	public ProducerDashboardController(ProducerDashboardService producerDashboardService) {
		this.producerDashboardService = producerDashboardService;
	}

	@GetMapping("/producer-dashboard")
	public String showDetail(Model model) {
		int producerId = ProducerDashboardService.DEFAULT_PRODUCER_ID;
		ProducerDashboardSummary summary = producerDashboardService.getSummary(producerId);

		model.addAttribute("summary", summary);
		model.addAttribute("businessDate", summary.getBusinessDate());
		model.addAttribute("salesReports", producerDashboardService.getSalesReports(producerId));
		return "producer/dashboard";
	}
}
