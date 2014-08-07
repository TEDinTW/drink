package com.example.drinkorder.bean.jackson;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class OrderedDrink implements Serializable {
	private String id;
	private String drinkName;
	private String sugarLevel;
	private String iceLevel;
	private int quantity;
	private int unitPrice;
	private int subTotal;

	@JsonProperty("DrinkName")
	public String getDrinkName() {
		return drinkName;
	}

	@JsonProperty("DrinkName")
	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	@JsonProperty("SugarLevel")
	public String getSugarLevel() {
		return sugarLevel;
	}

	@JsonProperty("SugarLevel")
	public void setSugarLevel(String sugarLevel) {
		this.sugarLevel = sugarLevel;
	}

	@JsonProperty("IceLevel")
	public String getIceLevel() {
		return iceLevel;
	}

	@JsonProperty("IceLevel")
	public void setIceLevel(String iceLevel) {
		this.iceLevel = iceLevel;
	}

	@JsonProperty("Quantity")
	public int getQuantity() {
		return quantity;
	}

	@JsonProperty("Quantity")
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@JsonProperty("UnitPrice")
	public int getUnitPrice() {
		return unitPrice;
	}

	@JsonProperty("UnitPrice")
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	@JsonProperty("Subtotal")
	public int getSubTotal() {
		return quantity * unitPrice;
	}

	@JsonProperty("Subtotal")
	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	@JsonProperty("DrinkId")
	public String getId() {
		return id;
	}

	@JsonProperty("DrinkId")
	public void setId(String id) {
		this.id = id;
	}

}
