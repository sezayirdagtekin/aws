package com.sezo.demo.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@DynamoDBTable(tableName = "ShopItems")
public class ShopItem {

	@DynamoDBAutoGeneratedKey //Generate random UUID
	@DynamoDBHashKey
	private String id;

	@DynamoDBAttribute
	private String name;
	
	@DynamoDBAttribute
	private String description;
	
	@DynamoDBAttribute
	private int amount;
	
	@DynamoDBIgnore
	private boolean inStock;
	
	
	

}
