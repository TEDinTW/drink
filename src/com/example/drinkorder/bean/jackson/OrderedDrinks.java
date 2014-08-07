package com.example.drinkorder.bean.jackson;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class OrderedDrinks {

	List<OrderedDrink> drinks;

	@JsonProperty("Drinks")
	public List<OrderedDrink> getDrinks() {
		return drinks;
	}

	@JsonProperty("Drinks")
	public void setDrinks(List<OrderedDrink> drinks) {
		this.drinks = drinks;
	}
}
