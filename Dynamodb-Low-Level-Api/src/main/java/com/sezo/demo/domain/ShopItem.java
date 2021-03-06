package com.sezo.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShopItem {

	private String id;

	private String name;

	private String description;

	private int amount;

}
