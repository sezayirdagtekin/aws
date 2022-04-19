package com.sezo.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class AwsConnection {

	@Autowired
	private AWSProperties properties;

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());

	}

	@Bean
	public AmazonDynamoDB dyanmoDB() {

		AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(amazonAWSCredentials());

		return AmazonDynamoDBClientBuilder.standard().withClientConfiguration(getClientConfig())
				.withRegion(Regions.US_EAST_1).withCredentials(provider).build();

	}

	@Bean
	public DynamoDBMapper mapper() {
		return new DynamoDBMapper(dyanmoDB());
	}

	private ClientConfiguration getClientConfig() {
		return new ClientConfiguration().withMaxErrorRetry(0).withConnectionTimeout(2000);
	}
}
