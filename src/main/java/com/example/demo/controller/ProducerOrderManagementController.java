package com.example.demo.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.ProducerDashboardService;
import com.example.demo.service.ProducerOrderManagementService;

@Controller
public class ProducerOrderManagementController {
	private final ProducerOrderManagementService orderManagementService;

	public ProducerOrderManagementController(ProducerOrderManagementService orderManagementService) {
		this.orderManagementService = orderManagementService;
	}

	@GetMapping("/producer/orders")
	public String showOrderList(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "status", required = false) String status,
			Model model) {
		int producerId = ProducerDashboardService.DEFAULT_PRODUCER_ID;
		model.addAttribute("orders", orderManagementService.findOrders(producerId, keyword, status));
		model.addAttribute("statusOptions", orderManagementService.getStatusOptions());
		model.addAttribute("keyword", keyword);
		model.addAttribute("status", status);
		model.addAttribute("businessDate", LocalDate.now(ZoneId.of("Asia/Tokyo")));
		return "producer/orders";
	}

	@PostMapping("/producer/orders/status")
	public String updateOrderStatus(@RequestParam(name = "orderIds", required = false) List<Integer> orderIds,
			@RequestParam String status,
			RedirectAttributes redirectAttributes) {
		int producerId = ProducerDashboardService.DEFAULT_PRODUCER_ID;
		int updatedCount = orderManagementService.updateStatus(producerId, orderIds, status);
		redirectAttributes.addFlashAttribute("savedMessage", updatedCount + "件の注文ステータスをデータベースに保存しました。");
		return "redirect:/producer/orders";
	}
}
