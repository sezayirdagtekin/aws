package com.sezo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.sezo.demo.domain.ShopItem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemService {

	private final DynamoDBMapper mapper;

	@Autowired
	public ItemService(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	public void putItem(ShopItem shopItem) {
		try {
			mapper.save(shopItem);
			log.info("item is saved");
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}

	public ShopItem getItem(String id) {
		try {
			return mapper.load(ShopItem.class, id);
		} catch (AmazonServiceException e) {

			e.printStackTrace();
		}
		return null;
	}

	public void deleteItem(String id) {
		try {
			ShopItem item = new ShopItem();
			item.setId(id);

			mapper.delete(item);
			log.info("item is deleted with id:" + id);
		} catch (AmazonServiceException e) {

			e.printStackTrace();
		}

	}
	
	

	public List<ShopItem> getAll() {
		try {
			//this is not efficient if table is big
			return mapper.scan(ShopItem.class, new  DynamoDBScanExpression());
		} catch (AmazonServiceException e) {

			e.printStackTrace();
		}
		return null;
	}
}
