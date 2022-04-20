package com.sezo.demo.domain;

import java.time.LocalDateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sezo.demo.convertor.LocalDateTimeConvertor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@DynamoDBTable(tableName = "UserMessage")
public class UserMessage {
	
	@DynamoDBHashKey
	private String department;

	@DynamoDBRangeKey
	@DynamoDBTypeConverted(converter = LocalDateTimeConvertor.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime sentTime;
	
	private int urgency;
	
	private String message;

}
