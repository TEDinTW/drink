package com.example.drinkorder.bean.jackson;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SubmitOrderRequest {
	private String userId;
	private String contactName;
	private String deliveryAddress;
	private String contactPhone;
	private List<OrderedDrink> drinks;
	private int total;

	@JsonProperty("UserId")
	public String getUserId() {
		return userId;
	}

	@JsonProperty("UserId")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("ContactName")
	public String getContactName() {
		return contactName;
	}

	@JsonProperty("ContactName")
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	@JsonProperty("DeliveryAddress")
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	@JsonProperty("DeliveryAddress")
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@JsonProperty("ContactPhone")
	public String getContactPhone() {
		return contactPhone;
	}

	@JsonProperty("ContactPhone")
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@JsonProperty("Drinks")
	public List<OrderedDrink> getDrinks() {
		return drinks;
	}

	@JsonProperty("Drinks")
	public void setDrinks(List<OrderedDrink> drinks) {
		this.drinks = drinks;
	}

	@JsonProperty("Total")
	public int getTotal() {
		return total;
	}

	@JsonProperty("Total")
	public void setTotal(int total) {
		this.total = total;
	}
}
