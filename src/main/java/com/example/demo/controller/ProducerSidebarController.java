package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entity.Producer;

@ControllerAdvice
public class ProducerSidebarController {
	@ModelAttribute
	public void addSidebarData(Model model, HttpSession session) {

		Producer loginProducer = (Producer) session.getAttribute("loginProducer");

		if (loginProducer != null) {
			model.addAttribute("loginProducer", loginProducer);
		}
	}
}