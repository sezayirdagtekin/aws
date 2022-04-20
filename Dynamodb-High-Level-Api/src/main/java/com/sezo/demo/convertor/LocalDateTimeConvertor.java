package com.sezo.demo.convertor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class LocalDateTimeConvertor implements DynamoDBTypeConverter<String, LocalDateTime> {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	@Override
	public String convert(LocalDateTime time) {

		return time.format(formatter);
	}

	@Override
	public LocalDateTime unconvert(String time) {

		return LocalDateTime.parse(time, formatter);
	}

}
