package com.example.drinkorder.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.drinkorder.adapter.bean.OrderedDrinkAdapterElement;
import com.example.drinkorder.bean.jackson.OrderedDrink;

public class BeansToAdapterElementsConverter {
	public static List<OrderedDrinkAdapterElement> convert(List<OrderedDrink> beans) {
		List<OrderedDrinkAdapterElement> elements = new ArrayList<OrderedDrinkAdapterElement>();
		if (beans != null && !beans.isEmpty()) {
			for (OrderedDrink bean : beans) {
				OrderedDrinkAdapterElement element = new OrderedDrinkAdapterElement();
				// workaround way
				element.setElementId(Integer.parseInt(bean.getId()));
				element.setDrinkName(bean.getDrinkName());
				element.setIceLevel(bean.getIceLevel());
				element.setSugarLevel(bean.getSugarLevel());
				element.setQuantity(bean.getQuantity());
				element.setUnitPrice(bean.getUnitPrice());
				element.setSubTotal(bean.getSubTotal());
				elements.add(element);
			}
		}
		return elements;
	}

}
