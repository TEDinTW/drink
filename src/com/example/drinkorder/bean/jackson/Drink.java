package com.example.drinkorder.bean.jackson;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class Drink implements Serializable{
	private String id;
	private String drinkName;
	private int unitPrice;

	@JsonProperty("id")
	public String getId() {
		return id;
	}
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("DrinkName")
	public String getDrinkName() {
		return drinkName;
	}
	@JsonProperty("DrinkName")
	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	@JsonProperty("UnitPrice")
	public int getUnitPrice() {
		return unitPrice;
	}
	@JsonProperty("UnitPrice")
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
}
