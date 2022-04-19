package com.sezo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.sezo.demo.util.Utils;


@Service
public class TableService {
	
	@Autowired
	private AmazonDynamoDB dynamoDB ;



	public  void createTable(Class<?> domainClass) {
		
		Utils.createTable(domainClass, dynamoDB);
		
	}




 

}
