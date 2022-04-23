package com.sezo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sezo.demo.domain.Order;
import com.sezo.demo.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	private final OrderService service;

	@Autowired
	public OrderController(OrderService service) {
		this.service = service;
	}

	@PostMapping("/save")
	public void newOrder(@RequestBody Order order) {
	   service.save(order);
	}
}
