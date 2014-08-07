package com.example.drinkorder.bean.jackson;

import org.codehaus.jackson.annotate.JsonProperty;

public class AuthenticateRequest {
	private String userId;
	private String userPwd;

	@JsonProperty("UserId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("UserPwd")
	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
}
