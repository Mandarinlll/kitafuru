package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Producer;
import com.example.demo.mapper.ProducerMapper;

@Controller
public class ProducerController {

	private final ProducerMapper producerMapper;

	public ProducerController(ProducerMapper producerMapper) {
		this.producerMapper = producerMapper;
	}

	@GetMapping("/producer/{id}")
	public String showProducer(@PathVariable int id, Model model) {

		Producer producer = producerMapper.findById(id);

		model.addAttribute("producer", producer);

		return "producer/producer";
	}
}