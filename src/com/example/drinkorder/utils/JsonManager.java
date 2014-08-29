package com.example.drinkorder.utils;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;

import com.example.drinkorder.bean.jackson.DrinkData;
import com.example.drinkorder.bean.jackson.OrderedDrink;
import com.example.drinkorder.bean.jackson.OrderedDrinks;
import com.example.drinkorder.bean.jackson.User;
import com.example.drinkorder.exception.ServiceException;

public class JsonManager {
	public static List<OrderedDrink> getOrderedDrinks(Context context) throws ServiceException {

		String jsonStr = PreferencesManager.loadOrderedDrink(context);

		List<OrderedDrink> orderedDrinks = null;
		if (!StringUtil.isEmpty(jsonStr)) {
			ObjectMapper mapper = new ObjectMapper();

			OrderedDrinks orderedDrinksBean = null;
			try {
				orderedDrinksBean = mapper.readValue(jsonStr, OrderedDrinks.class);
				orderedDrinks = orderedDrinksBean.getDrinks();
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
		}
		return orderedDrinks;
	}

	public static DrinkData getDrinkData(Context context) throws ServiceException {
		String jsonStr = PreferencesManager.loadDrinkData(context);

		DrinkData drinkData = null;
		if (!StringUtil.isEmpty(jsonStr)) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				drinkData = mapper.readValue(jsonStr, DrinkData.class);
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
		}
		return drinkData;
	}

	public static User getSignInUser(Context context) throws ServiceException {
		String jsonStr = PreferencesManager.loadUser(context);

		User user = null;
		if (!StringUtil.isEmpty(jsonStr)) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				user = mapper.readValue(jsonStr, User.class);
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
		}
		return user;
	}

	public static Object parseSubmitOrderResponse(String jsonStr, Class<?> cls) throws ServiceException {
		Object response = null;
		if (!StringUtil.isEmpty(jsonStr)) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				response = mapper.readValue(jsonStr, cls);
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
		}
		return response;
	}

	// should be generic
	public static String toJsonString(Object request) throws ServiceException {
		String jsonStr = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(request);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return jsonStr;
	}

}
