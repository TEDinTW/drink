package com.example.drinkorder.bean.jackson;

import org.codehaus.jackson.annotate.JsonProperty;

public class AuthenticateResponse extends ResponseErrorIdentifier{

	private String userId;
	private String userName;
	private String userPhone;
	private String userAddress;



	public String getUserId() {
		return userId;
	}

	@JsonProperty("UserId")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	@JsonProperty("UserName")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	@JsonProperty("UserPhone")
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserAddress() {
		return userAddress;
	}

	@JsonProperty("UserAddress")
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}



}
