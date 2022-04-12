package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.amazonaws.lambda.demo.entity.Product;
import com.amazonaws.lambda.demo.response.ProductResponse;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;

public class FindProductLambda implements RequestHandler<APIGatewayProxyRequestEvent, ProductResponse> {

	private Product[] products;

	@Override
	public ProductResponse handleRequest(APIGatewayProxyRequestEvent request, Context context) {

		context.getLogger().log("Input: " + request);

		Map<String, String> inputParams = request.getQueryStringParameters();
		String productId = (String) inputParams.get("productId");

		Product product = getProductById(Integer.valueOf(productId));
		
		return new ProductResponse(product);
	}

	private Product getProductById(int id) {
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

		GetObjectRequest request = new GetObjectRequest("sezobucket", "catalog.json");
		S3Object response = s3.getObject(request);

		InputStream inputStream = response.getObjectContent();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

			products = new Gson().fromJson(br, Product[].class);

			for (Product product : products) {
				if (product.getId() == id) {
					return product;
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error occured while getting product");
		}

		return null;
	}

}
