package com.example.demo.controller;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.ProducerProfile;
import com.example.demo.service.ProducerDashboardService;
import com.example.demo.service.ProducerProfileService;

@Controller
public class ProducerDetailController {
	private final ProducerProfileService producerProfileService;

	public ProducerDetailController(ProducerProfileService producerProfileService) {
		this.producerProfileService = producerProfileService;
	}

	@GetMapping("/producer-page")
	public String showDetail(Model model) {
		model.addAttribute("profile", producerProfileService.getProfile(ProducerDashboardService.DEFAULT_PRODUCER_ID));
		model.addAttribute("businessDate", LocalDate.now(ZoneId.of("Asia/Tokyo")));
		return "producer/detail";
	}

	@PostMapping("/producer-page")
	public String updateProfile(@ModelAttribute("profile") ProducerProfile profile,
			RedirectAttributes redirectAttributes) {
		producerProfileService.updateProfile(profile);
		redirectAttributes.addFlashAttribute("savedMessage", "店舗・自社情報を保存しました。");
		return "redirect:/producer-page";
	}
}
