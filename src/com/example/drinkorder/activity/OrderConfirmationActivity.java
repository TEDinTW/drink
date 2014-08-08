package com.example.drinkorder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;

public class OrderConfirmationActivity extends Activity {

	private TextView tvOrderId, tvContactName, tvContactPhone, tvContactAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_confirmation);

		tvOrderId=(TextView)findViewById(R.id.tvOrderId);
		tvContactName=(TextView)findViewById(R.id.tvContactName);
		tvContactPhone=(TextView)findViewById(R.id.tvContactPhone);
		tvContactAddress=(TextView)findViewById(R.id.tvContactAddress);
		
		String orderId = getIntent().getExtras().getString(Constants.ORDER_ID_PARAM_NAME);
		String contactName = getIntent().getExtras().getString(Constants.DLV_CONTACT_NAME_PARAM_NAME);
		String contactPhone = getIntent().getExtras().getString(Constants.DLV_CONTACT_PHONE_PARAM_NAME);
		String contactAddress = getIntent().getExtras().getString(Constants.DLV_CONTACT_ADDR_PARAM_NAME);

		tvOrderId.setText(orderId);
		tvContactName.setText(contactName);
		tvContactPhone.setText(contactPhone);
		tvContactAddress.setText(contactAddress);
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		// In order confirmation page, when back key is pressed, return to the drink choosing activity
		Intent intent = new Intent(this, DrinkOrderActivity.class);
		startActivity(intent);
	}
	
	
}
