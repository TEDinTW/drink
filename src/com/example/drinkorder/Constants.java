package com.example.drinkorder;

public class Constants {
	// generic
	public static final String CMD = "cmd";
	public static final String TARGET_ID = "target_id";
	public static final int MENU_GENERIC_MTN_ADD = 1;
	public static final int MENU_GENERIC_MTN_DELETE = 2;
	public static final int MENU_GENERIC_MTN_UPDATE = 3;
	public static final int CMD_ADD = 1;
	public static final int CMD_UPDATE = 2;
	public static final int CMD_VIEW = 3;
	public static final int CMD_DELETE = 4;
	public static final int DISPLAY_MODE_VIEW = 1;
	public static final int DISPLAY_MODE_ADD = 2;
	public static final int DISPLAY_MODE_EDIT = 3;
	public static final int INTENT_REQUEST_CODE_ADD = 1;
	public static final int INTENT_REQUEST_CODE_UPDATE = 2;
	public static final int INTENT_REQUEST_CODE_DELETE = 3;
	public static final String TARGET_ID_PARAM_NAME = "target_id";
	public static final String INITIAL_DATE_PARAM_NAME = "initial_date";
	public static final String GENERIC_ALERT_DIALOG_TITLE_PARAM_NAME="generic_alert_dialog_title";	
	public static final String GENERIC_ALERT_DIALOG_TEXT_PARAM_NAME="generic_alert_dialog_text";	
	public static final String DIALOG_RETURN_BUNDLE_PARAM_NAME="dialog_return_bundle";
	public static final String ERROR_CODE_UNEXPECTED_ERROR="9999";
	
	
	
	// preference file name
	public static final String PREF_FILE_ORDERED_DRINK="shared_preference_ordered_drink";
	public static final String PREF_FILE_CREDENTIAL="shared_preference_credential";
	public static final String PREF_FILE_SETTING="shared_preference_setting";	
	
	// preference key
	public static final String PREF_KEY_ORDERED_DRINK_JSON="ordered_drink_json";
	public static final String PREF_KEY_AUTHENTICATED_USER_JSON="authenticated_user_json";
	public static final String PREF_KEY_CREDENTIAL_SAVED="credential_saved";
	
	// function specific
	public static final String USER_NAME_PARAM="user_name_param";
	public static final String ORDERED_DRINK_BEAN_PARAM_NAME="ordered_drink_bean";
	public static final String DRINK_BEAN_PARAM_NAME="drink_bean";	
	public static final String SUGAR_LEVEL_PARAM_NAME="sugar_level";
	public static final String ICE_LEVEL_PARAM_NAME="ice_level";
	public static final String SUGAR_LEVELS_PARAM_NAME="sugar_levels";
	public static final String ICE_LEVELS_PARAM_NAME="ice_levels";
	public static final String QUANTITY_PARAM_NAME="quantity";
	public static final String DLV_CONTACT_NAME_PARAM_NAME="delivery_contact_name";
	public static final String DLV_CONTACT_PHONE_PARAM_NAME="delivery_contact_phone";
	public static final String DLV_CONTACT_ADDR_PARAM_NAME="delivery_contact_address";
	public static final String ORDER_ID_PARAM_NAME="order_id";
}
