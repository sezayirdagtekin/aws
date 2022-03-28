package com.sezo.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSConnection  {

	@Autowired
	private AWSProperties properties;
	
@Bean
	public AmazonS3 getAwsClient() {
		AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());

		return AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.US_EAST_1)
				  .build();

	}
	

}
