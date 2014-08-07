package com.example.drinkorder.bean.jackson;

import org.codehaus.jackson.annotate.JsonProperty;

public class Error {
	private String erroCode;
	private String errorMessage;

	public Error() {
	};

	public Error(String code, String msg) {
		this.erroCode = code;
		this.errorMessage = msg;
	}

	public String getErroCode() {
		return erroCode;
	}

	@JsonProperty("ErrorCode")
	public void setErroCode(String erroCode) {
		this.erroCode = erroCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@JsonProperty("ErrorMessage")
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
