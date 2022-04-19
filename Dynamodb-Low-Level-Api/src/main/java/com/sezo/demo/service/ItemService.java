package com.sezo.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.sezo.demo.domain.ShopItem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemService {

	private static final String TABLE_NAME = "ShopItem";
	private final AmazonDynamoDB dynamoDB;

	@Autowired
	public ItemService(AmazonDynamoDB dynamoDB) {
		this.dynamoDB = dynamoDB;
	}

	public void putItem(ShopItem shopItem) {

		try {
			Map<String, AttributeValue> itemMap = new HashMap<>();

			itemMap.put("id", new AttributeValue().withS(shopItem.getId()));
			itemMap.put("name", new AttributeValue().withS(shopItem.getName()));
			itemMap.put("description", new AttributeValue().withS(shopItem.getDescription()));
			itemMap.put("amount", new AttributeValue().withN(Integer.toString(shopItem.getAmount())));

			PutItemRequest request = new PutItemRequest(TABLE_NAME, itemMap);

			PutItemResult result = dynamoDB.putItem(request);

			log.info(result.toString());
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}

	public ShopItem getItem(String id) {

		Map<String, AttributeValue> itemMap = new HashMap<>();

		ShopItem item = new ShopItem();

		try {
			itemMap.put("id", new AttributeValue().withS(id));

			GetItemRequest request = new GetItemRequest().withTableName(TABLE_NAME).withKey(itemMap)
					.withConsistentRead(true);

			GetItemResult result = dynamoDB.getItem(request);
			Map<String, AttributeValue> dynamoDbItem = result.getItem();

			item.setId(dynamoDbItem.get("id").getS());
			item.setName(dynamoDbItem.get("name").getS());
			item.setDescription(dynamoDbItem.get("description").getS());
			item.setAmount(Integer.parseInt(dynamoDbItem.get("amount").getN()));

		} catch (AmazonServiceException | NumberFormatException e) {
	
			e.printStackTrace();
		}
		return item;

	}
}
