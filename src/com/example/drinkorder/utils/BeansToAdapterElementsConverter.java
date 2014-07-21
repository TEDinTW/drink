package com.example.drinkorder.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.drinkorder.adapter.bean.OrderedDrinkAdapterElement;
import com.example.drinkorder.bean.DrinkBean;

public class BeansToAdapterElementsConverter {
	public static List<OrderedDrinkAdapterElement> convert(List<DrinkBean> beans) {
		List<OrderedDrinkAdapterElement> elements = new ArrayList<OrderedDrinkAdapterElement>();
		if (beans != null && !beans.isEmpty()) {
			for (DrinkBean bean : beans) {
				OrderedDrinkAdapterElement element = new OrderedDrinkAdapterElement();
				element.setElementId(bean.getId());
				element.setDrinkName(bean.getDrinkName());
				element.setIceLevel(bean.getIceLevel());
				element.setSugarLevel(bean.getSugarLevel());
				element.setQuantity(bean.getQuantity());
				elements.add(element);
			}
		}
		return elements;
	}

}
