package com.example.drinkorder.bean.jackson;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {
	private String userId;
	private String userName;
	private String userPhone;
	private String userAddress;

	@JsonProperty("UserId")
	public String getUserId() {
		return userId;
	}

	@JsonProperty("UserId")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("UserName")
	public String getUserName() {
		return userName;
	}

	@JsonProperty("UserName")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("UserPhone")
	public String getUserPhone() {
		return userPhone;
	}

	@JsonProperty("UserPhone")
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@JsonProperty("UserAddress")
	public String getUserAddress() {
		return userAddress;
	}

	@JsonProperty("UserAddress")
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
}
