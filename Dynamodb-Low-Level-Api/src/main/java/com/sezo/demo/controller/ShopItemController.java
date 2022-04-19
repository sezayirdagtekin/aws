package com.sezo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sezo.demo.domain.ShopItem;
import com.sezo.demo.service.ItemService;

@RestController
@RequestMapping("/item")
public class ShopItemController {

	private final ItemService service;

	@Autowired
	public ShopItemController(ItemService service) {
		this.service = service;
	}

	@PostMapping("/save")
	public void putItem(@RequestBody ShopItem item) {
		service.putItem(item);
	}

	@GetMapping("/id")
	public ResponseEntity<ShopItem> getItem(@RequestParam String id) {
		return new ResponseEntity<>(service.getItem(id), HttpStatus.OK);
	}

}
