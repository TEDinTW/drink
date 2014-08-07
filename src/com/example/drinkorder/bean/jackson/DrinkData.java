package com.example.drinkorder.bean.jackson;

import org.codehaus.jackson.annotate.JsonProperty;

public class DrinkData {
	private Drink[] drinks;
	private String[] sugarLevels;
	private String[] iceLevels;

	@JsonProperty("Drinks")
	public Drink[] getDrinks() {
		return drinks;
	}
	@JsonProperty("Drinks")
	public void setDrinks(Drink[] drinks) {
		this.drinks = drinks;
	}

	@JsonProperty("SugarLevels")
	public String[] getSugarLevels() {
		return sugarLevels;
	}
	@JsonProperty("SugarLevels")
	public void setSugarLevels(String[] sugarLevels) {
		this.sugarLevels = sugarLevels;
	}

	@JsonProperty("IceLevels")
	public String[] getIceLevels() {
		return iceLevels;
	}
	@JsonProperty("IceLevels")
	public void setIceLevels(String[] iceLevels) {
		this.iceLevels = iceLevels;
	}
}
