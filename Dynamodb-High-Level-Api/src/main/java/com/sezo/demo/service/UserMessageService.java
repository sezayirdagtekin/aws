package com.sezo.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.sezo.demo.domain.UserMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserMessageService {

	private final DynamoDBMapper mapper;

	@Autowired
	public UserMessageService(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	public void putItem(UserMessage message) {
		try {
			mapper.save(message);
			log.info("item is saved");
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}

	public UserMessage getMessage(String department, String time) {
		try {
			return mapper.load(UserMessage.class, department, time);
		} catch (AmazonServiceException e) {

			e.printStackTrace();
		}
		return null;
	}

	public void deleteMessage(String department, LocalDateTime time) {
		try {
			UserMessage message = new UserMessage();
			message.setDepartment(department);
			message.setSentTime(time);

			mapper.delete(message);
			log.info("item is deleted with id:" + message.toString());
		} catch (AmazonServiceException e) {

			e.printStackTrace();
		}

	}

	public List<UserMessage> getAllForDepartment(String department) {
		try {

			DynamoDBQueryExpression<UserMessage> queryExpression = new DynamoDBQueryExpression<UserMessage>()
					.withScanIndexForward(false).withKeyConditionExpression("department = :department")
					.withExpressionAttributeValues(Map.of(":department", new AttributeValue().withS(department)));

			return mapper.query(UserMessage.class, queryExpression);

		} catch (AmazonServiceException e) {

			e.printStackTrace();
		}
		return null;
	}

	public List<UserMessage> getAllForDepartmentOnDate(String department, String day) {
		try {
			DynamoDBQueryExpression<UserMessage> queryExpression = new DynamoDBQueryExpression<UserMessage>()
					.withKeyConditionExpression("department=:department AND begins_with(sentTime, :day)")
					.withExpressionAttributeValues(Map.of(":department", new AttributeValue().withS(department), 
							":day",new AttributeValue().withS(day)));
			return mapper.query(UserMessage.class, queryExpression);

		} catch (AmazonServiceException e) {

			e.printStackTrace();
		}
		return null;
	}

	public List<UserMessage> getAllUrgencyMessages(String department, String urgency) {
		try {

			DynamoDBQueryExpression<UserMessage> queryExpression = new DynamoDBQueryExpression<UserMessage>()
					.withKeyConditionExpression("department=:department").withFilterExpression("urgency > :urgency")
					.withExpressionAttributeValues(Map.of(":department", new AttributeValue().withS(department),
							":urgency", new AttributeValue().withN(urgency)

					));

			return mapper.query(UserMessage.class, queryExpression);
		} catch (AmazonServiceException e) {

			e.printStackTrace();
		}
		return null;
	}

}
