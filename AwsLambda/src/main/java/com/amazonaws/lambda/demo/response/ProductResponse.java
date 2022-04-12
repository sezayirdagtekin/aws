package com.amazonaws.lambda.demo.response;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.lambda.demo.entity.Product;
import com.google.gson.Gson;

public class ProductResponse {

	private String body;
	private String statusCode = "200";
	private Map<String, String> headers = new HashMap<>();

	public ProductResponse() {
		super();
		this.headers.put("Contetnt-Type", "application/json");
	}

	public ProductResponse(Product product) {
		this();
		this.body = new Gson().toJson(product);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

}
