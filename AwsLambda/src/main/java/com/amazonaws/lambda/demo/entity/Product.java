package com.amazonaws.lambda.demo.entity;

public class Product {

	private int id;
	private String toolType;
	private String brand;
	private String name;
	private int count;

	public long getId() {
		return id;
	}

	public String getToolType() {
		return toolType;
	}

	public String getBrand() {
		return brand;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", toolType=" + toolType + ", brand=" + brand + ", name=" + name + ", count="
				+ count + "]";
	}

}
