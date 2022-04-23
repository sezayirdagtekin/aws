package com.sezo.demo.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.TransactionalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Put;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItem;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest;
import com.amazonaws.services.dynamodbv2.model.Update;
import com.sezo.demo.domain.Order;

@Service
public class OrderService {

	@Autowired
	private AmazonDynamoDB dynamoDB; // DynamoDBMapper not used. 

	
	
	/**
	 * Used old transaction API
	 * @param order
	 */
	public void save(Order order) {

		try {
			Update updateItem = new Update().withTableName("ShopItem")
					.withKey(Map.of("id", new AttributeValue().withS(order.getItemId())))
					.withConditionExpression("amount > :purchaseAmount")
					.withUpdateExpression("SET  amount=amount- :purchaseAmount")
					.withReturnValuesOnConditionCheckFailure("ALL_OLD").withExpressionAttributeValues(
							Map.of(":purchaseAmount", new AttributeValue().withN(Integer.toString(order.getAmount()))));

			Put newOrder = new Put().withTableName("Orders")
					.withItem(Map.of("id", new AttributeValue().withS(UUID.randomUUID().toString()), "itemId",
							new AttributeValue().withS(order.getItemId()), "amount",
							new AttributeValue().withS(Integer.toString(order.getAmount()))));

			List<TransactWriteItem> actions = List.of(new TransactWriteItem().withUpdate(updateItem), new TransactWriteItem().withPut(newOrder));

			TransactWriteItemsRequest placeOrderTransaction = new TransactWriteItemsRequest().withTransactItems(actions)
					.withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL);

			dynamoDB.transactWriteItems(placeOrderTransaction);

		} catch (TransactionalException e) {

			e.printStackTrace();
		}
	}

}
