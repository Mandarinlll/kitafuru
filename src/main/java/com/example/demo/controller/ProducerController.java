package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Producer;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProducerMapper;
import com.example.demo.mapper.ProductMapper;

@Controller
public class ProducerController {

	private final ProducerMapper producerMapper;
	private final ProductMapper productMapper;

	public ProducerController(ProducerMapper producerMapper,
			ProductMapper productMapper) {
		this.producerMapper = producerMapper;
		this.productMapper = productMapper;
	}

	@GetMapping("/producer/{id}")
	public String showProducer(@PathVariable Integer id, Model model) {

		// 生産者
		Producer producer = producerMapper.findById(id);

		// 取扱商品
		List<Product> products = productMapper.findByProducerId(id, id);

		model.addAttribute("producer", producer);
		model.addAttribute("products", products);

		return "producer/producer";
	}
}