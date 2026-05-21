package com.example.demo.service;

import com.example.demo.entity.Producer;

public interface ProducerLoginService {
	Producer login(String email, String password);
}
