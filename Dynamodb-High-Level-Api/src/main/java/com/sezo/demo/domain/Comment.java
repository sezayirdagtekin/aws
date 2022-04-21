package com.sezo.demo.domain;

import java.time.LocalDateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
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
@DynamoDBTable(tableName = "Comments")
public class Comment {
	
	@DynamoDBHashKey
	private String itemId;

	@DynamoDBRangeKey
	@DynamoDBTypeConverted(converter = LocalDateTimeConvertor.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	@DynamoDBIndexRangeKey(globalSecondaryIndexName = "global-index-userId")
	private LocalDateTime sentTime;
	
	@DynamoDBIndexHashKey(globalSecondaryIndexName = "global-index-userId")
	private String userId;
	
	private String message;
	
	@DynamoDBIndexRangeKey(localSecondaryIndexName = "local-index-rating")
	private int rating;

}
