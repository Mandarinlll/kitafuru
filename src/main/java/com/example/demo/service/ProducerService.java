package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Producer;
import com.example.demo.mapper.ProducerMapper;

@Service
public class ProducerService {

	private final ProducerMapper producerMapper;

	public ProducerService(ProducerMapper producerMapper) {
		this.producerMapper = producerMapper;
	}

	public Producer findById(Integer id) {
		return producerMapper.findById(id);
	}
}