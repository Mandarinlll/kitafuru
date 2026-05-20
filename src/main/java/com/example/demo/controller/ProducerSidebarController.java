package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entity.Producer;
import com.example.demo.service.ProducerService;

@ControllerAdvice
public class ProducerSidebarController {

	private static final Integer DEFAULT_PRODUCER_ID = 1;

	private final ProducerService producerService;

	public ProducerSidebarController(ProducerService producerService) {
		this.producerService = producerService;
	}

	@ModelAttribute
	public void addSidebarData(Model model) {

		Producer producer = producerService.findById(DEFAULT_PRODUCER_ID);

		model.addAttribute("loginProducer", producer);
	}
}