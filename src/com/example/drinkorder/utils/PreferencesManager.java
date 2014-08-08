package com.example.drinkorder.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.drinkorder.Constants;

public class PreferencesManager {

	public static void serializeUser(Context context, String jsonStr) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_CREDENTIAL, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(Constants.PREF_KEY_AUTHENTICATED_USER_JSON, jsonStr);
		editor.commit();
	}
	
	public static String loadUser(Context context) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_CREDENTIAL, 0);
		return pref.getString(Constants.PREF_KEY_AUTHENTICATED_USER_JSON, null);
	}
	
	public static void setCredentialSaved(Context context, boolean toSave){
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_CREDENTIAL, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(Constants.PREF_KEY_CREDENTIAL_SAVED, toSave);
		editor.commit();
	}
	
	public static boolean isCredentialSaved(Context context){
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_CREDENTIAL, 0);
		return pref.getBoolean(Constants.PREF_KEY_CREDENTIAL_SAVED, false);
	}
	
	public static void removeSavedCredential(Context context) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_CREDENTIAL, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(Constants.PREF_KEY_AUTHENTICATED_USER_JSON);
		editor.remove(Constants.PREF_KEY_CREDENTIAL_SAVED);
		editor.commit();
	}



	public static void serializeDrinkData(Context context, String jsonStr) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_ORDERED_DRINK, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(Constants.PREF_KEY_DRINK_DATA_JSON, jsonStr);
		editor.commit();
	}

	public static String loadDrinkData(Context context) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_ORDERED_DRINK, 0);
		return pref.getString(Constants.PREF_KEY_DRINK_DATA_JSON, null);
	}

	public static void serializeOrderedDrink(Context context, String jsonStr) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_ORDERED_DRINK, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(Constants.PREF_KEY_ORDERED_DRINK_JSON, jsonStr);
		editor.commit();
	}

	public static String loadOrderedDrink(Context context) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_ORDERED_DRINK, 0);
		return pref.getString(Constants.PREF_KEY_ORDERED_DRINK_JSON, null);
	}

	public static void removeOrderedDrink(Context context) {
		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_FILE_ORDERED_DRINK, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(Constants.PREF_KEY_ORDERED_DRINK_JSON);
		editor.commit();
	}

}
