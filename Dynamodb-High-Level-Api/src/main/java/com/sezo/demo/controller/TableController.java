package com.sezo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sezo.demo.domain.ShopItem;
import com.sezo.demo.service.TableService;

@RestController
@RequestMapping("/table")
public class TableController {
	private final TableService service;

	@Autowired
	public TableController(TableService service) {
		this.service = service;
	}

	@PostMapping("/create")
	public void createTable() {
	   service.createTable(ShopItem.class.getClass());
	}
}
