package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;

@Service
public class OrderService {

	private final OrderMapper orderMapper;

	public OrderService(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	public List<Order> getRecentOrders(Integer userId) {
		return orderMapper.findRecentOrdersByUserId(userId);
	}
}