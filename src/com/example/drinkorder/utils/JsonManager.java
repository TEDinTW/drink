package com.example.drinkorder.utils;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.drinkorder.Constants;
import com.example.drinkorder.bean.jackson.OrderedDrink;
import com.example.drinkorder.bean.jackson.OrderedDrinks;
import com.example.drinkorder.bean.jackson.User;

public class JsonManager {
	public static List<OrderedDrink> getOrderedDrinks(Context context) {

		List<OrderedDrink> orderedDrinks = null;
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_ORDERED_DRINK, 0);
		String jsonStr = pref.getString(Constants.PREF_KEY_ORDERED_DRINK_JSON, null);
		if (!StringUtil.isEmpty(jsonStr)) {
			ObjectMapper mapper = new ObjectMapper();

			OrderedDrinks orderedDrinksBean = null;
			try {
				orderedDrinksBean = mapper.readValue(jsonStr, OrderedDrinks.class);
				orderedDrinks = orderedDrinksBean.getDrinks();
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return orderedDrinks;
	}

	public static User getSignInUser(Context context) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_CREDENTIAL, 0);
		String jsonStr = pref.getString(Constants.PREF_KEY_AUTHENTICATED_USER_JSON, null);
		User user = null;
		if (!StringUtil.isEmpty(jsonStr)) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				user = mapper.readValue(jsonStr, User.class);

			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}

	public static Object parseSubmitOrderResponse(String jsonStr, Class<?> cls) {
		Object response = null;
		if (!StringUtil.isEmpty(jsonStr)) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				response = mapper.readValue(jsonStr, cls);

			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return response;
	}

	// should be generic
	public static String toJsonString(Object request) {
		String jsonStr = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(request);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

}
