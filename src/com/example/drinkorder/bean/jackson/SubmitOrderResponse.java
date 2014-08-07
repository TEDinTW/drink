package com.example.drinkorder.bean.jackson;

import org.codehaus.jackson.annotate.JsonProperty;

public class SubmitOrderResponse  extends ResponseErrorIdentifier{
	private String orderId;

	@JsonProperty("OrderId")
	public String getOrderId() {
		return orderId;
	}

	@JsonProperty("OrderId")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
