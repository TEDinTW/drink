package com.example.drinkorder.adapter.bean;


public class OrderedDrinkAdapterElement extends ViewWithIdAdapterElement{
	String drinkName;
	String sugarLevel;
	String iceLevel;
	int quantity;

	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public String getSugarLevel() {
		return sugarLevel;
	}

	public void setSugarLevel(String sugarLevel) {
		this.sugarLevel = sugarLevel;
	}

	public String getIceLevel() {
		return iceLevel;
	}

	public void setIceLevel(String iceLevel) {
		this.iceLevel = iceLevel;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
