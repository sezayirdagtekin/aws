package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class LambdaFunctionHandler implements RequestHandler<Map<String,String>, String> {
	


	@Override
	public String handleRequest(Map<String,String> input, Context context) {
		context.getLogger().log("Input: " + input);

		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

		String output = "";

		GetObjectRequest request = new GetObjectRequest("sezobucket", "hello.txt");
		S3Object response = s3.getObject(request);

		InputStream inputStream = response.getObjectContent();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

			output = br.lines().collect(Collectors.joining(",", "{", "}"));

		} catch (Exception e) {
			context.getLogger().log("Erorr occurs when reading file: " + e.getStackTrace());
		}

		return output;
	}

}
