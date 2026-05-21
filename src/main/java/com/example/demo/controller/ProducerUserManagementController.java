package com.example.demo.controller;

import java.time.LocalDate;
import java.time.ZoneId;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Producer;
import com.example.demo.model.ProducerProfile;
import com.example.demo.service.ProducerProfileService;

@Controller
public class ProducerUserManagementController {
	private final ProducerProfileService producerProfileService;

	public ProducerUserManagementController(ProducerProfileService producerProfileService) {
		this.producerProfileService = producerProfileService;
	}

	@GetMapping("/producer/user-management")
	public String showUserManagement(Model model, HttpSession session) {
		Producer loginProducer = (Producer) session.getAttribute("loginProducer");
		if (loginProducer == null) {
			return "redirect:/producer/login";
		}
		model.addAttribute("profile", producerProfileService.getProfile(loginProducer.getId()));
		model.addAttribute("businessDate", LocalDate.now(ZoneId.of("Asia/Tokyo")));
		return "producer/user-management";
	}

	@PostMapping("/producer/user-management")
	public String updateProfile(@ModelAttribute("profile") ProducerProfile profile,
			RedirectAttributes redirectAttributes) {
		producerProfileService.updateProfile(profile);
		redirectAttributes.addFlashAttribute("savedMessage", "店舗・自社情報を保存しました。");
		return "redirect:/producer/user-management";
	}
}
