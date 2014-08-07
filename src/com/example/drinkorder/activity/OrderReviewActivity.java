package com.example.drinkorder.activity;

import java.util.List;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;
import com.example.drinkorder.adapter.OrderedDrinkAdapter;
import com.example.drinkorder.adapter.bean.OrderedDrinkAdapterElement;
import com.example.drinkorder.bean.jackson.OrderedDrink;
import com.example.drinkorder.bean.jackson.SubmitOrderRequest;
import com.example.drinkorder.bean.jackson.SubmitOrderResponse;
import com.example.drinkorder.bean.jackson.User;
import com.example.drinkorder.dialog.ChangeDeliveryContactDialog;
import com.example.drinkorder.dialog.GenericAlertDialog;
import com.example.drinkorder.dialog.GenericConfirmationDialogListerner;
import com.example.drinkorder.utils.BeansToAdapterElementsConverter;
import com.example.drinkorder.utils.JsonManager;
import com.example.drinkorder.utils.WebServiceGateway;

public class OrderReviewActivity extends FragmentActivity implements GenericConfirmationDialogListerner {
	private ListView lvOrderDrinks;
	private TextView tvTotalQty, tvTotalAmount, tvContactName, tvContactPhone, tvDeliveryAddr;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_review);

		lvOrderDrinks = (ListView) findViewById(R.id.lvOrderDrinks);
		tvTotalQty = (TextView) findViewById(R.id.tvTotalQty);
		tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
		tvContactName = (TextView) findViewById(R.id.tvContactName);
		tvContactPhone = (TextView) findViewById(R.id.tvContactPhone);
		tvDeliveryAddr = (TextView) findViewById(R.id.tvDeliveryAddr);

		loadOrderedDrink();
		populateUser();
	}

	protected void populateUser() {
		User user = JsonManager.getSignInUser(this);
		if (user != null) {
			tvContactName.setText(user.getUserName());
			tvContactPhone.setText(user.getUserPhone());
			tvDeliveryAddr.setText(user.getUserAddress());
		}
	}

	protected void loadOrderedDrink() {
		List<OrderedDrink> orderedDrinks = JsonManager.getOrderedDrinks(this);

		OrderedDrinkAdapter adapter = null;
		List<OrderedDrinkAdapterElement> elements = BeansToAdapterElementsConverter.convert(orderedDrinks);
		if (elements != null && !elements.isEmpty()) {
			adapter = new OrderedDrinkAdapter(this, R.layout.ordered_drink_line_item, elements);

			int totalQty = 0;
			int totalAmount = 0;
			for (OrderedDrink orderedDrink : orderedDrinks) {
				totalQty += orderedDrink.getQuantity();
				totalAmount += orderedDrink.getSubTotal();
			}
			tvTotalQty.setText(String.valueOf(totalQty));
			tvTotalAmount.setText(String.valueOf(totalAmount));
		}

		lvOrderDrinks.setAdapter(adapter);
	}


	
	public void submitOrder(View view) {

		SubmitOrderRequest submitOrder = new SubmitOrderRequest();

		User user = JsonManager.getSignInUser(this);
		if (user != null) {
			submitOrder.setUserId(user.getUserId());
		}

		String contactName = tvContactName.getText().toString();
		String contactPhone = tvContactPhone.getText().toString();
		String deliveryAddr = tvDeliveryAddr.getText().toString();

		submitOrder.setContactName(contactName);
		submitOrder.setContactPhone(contactPhone);
		submitOrder.setDeliveryAddress(deliveryAddr);

		List<OrderedDrink> orderedDrinks = JsonManager.getOrderedDrinks(this);
		submitOrder.setDrinks(orderedDrinks);

		int orderTotal = 0;
		for (OrderedDrink orderedDrink : orderedDrinks) {
			orderTotal += orderedDrink.getSubTotal();
		}
		submitOrder.setTotal(orderTotal);

		new SubmitOrderTask().execute(submitOrder);
	}

	public void changePhoneAddr(View view) {
		Bundle args = new Bundle();
		args.putString(Constants.DLV_CONTACT_NAME_PARAM_NAME, tvContactName.getText().toString());
		args.putString(Constants.DLV_CONTACT_PHONE_PARAM_NAME, tvContactPhone.getText().toString());
		args.putString(Constants.DLV_CONTACT_ADDR_PARAM_NAME, tvDeliveryAddr.getText().toString());
		showChangeDeliveryContactDialog(args);
	}

	protected void showChangeDeliveryContactDialog(Bundle args) {
		DialogFragment newFragment = ChangeDeliveryContactDialog.newInstance(args);
		newFragment.show(getFragmentManager(), "dialog");
	}

	@Override
	public void doPositiveClick(Bundle bundle) {
		String contactName = bundle.getString(Constants.DLV_CONTACT_NAME_PARAM_NAME);
		String contactPhone = bundle.getString(Constants.DLV_CONTACT_PHONE_PARAM_NAME);
		String contactAddress = bundle.getString(Constants.DLV_CONTACT_ADDR_PARAM_NAME);
		tvContactName.setText(contactName);
		tvContactPhone.setText(contactPhone);
		tvDeliveryAddr.setText(contactAddress);
	}

	@Override
	public void doNegativeClick(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	private class SubmitOrderTask extends AsyncTask<SubmitOrderRequest, Void, SubmitOrderResponse> {
		@Override
		protected SubmitOrderResponse doInBackground(SubmitOrderRequest... requests) {
			return WebServiceGateway.submitOrder(requests[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(SubmitOrderResponse response) {
			if (response.isHasErrors()) {
				
				String errorMessage=null;
				if(response.getErrors()!=null && !response.getErrors().isEmpty()){
					// only retrieve the first error message
					errorMessage=response.getErrors().get(0).getErrorMessage();
				}else{
					errorMessage=OrderReviewActivity.this.getResources().getString(R.string.error_msg_unexpected_error);
				}
				

				Bundle args=new Bundle();
				args.putString(Constants.GENERIC_ALERT_DIALOG_TITLE_PARAM_NAME, OrderReviewActivity.this.getResources().getString(R.string.error_generic_title));
				args.putString(Constants.GENERIC_ALERT_DIALOG_TEXT_PARAM_NAME, errorMessage);
				DialogFragment newFragment = GenericAlertDialog.newInstance(args);
				newFragment.show(getFragmentManager(), "dialog");

			} else {
				// Once the order is successfully submitted, clear the ordered list from preference
				SharedPreferences pref = getSharedPreferences(Constants.PREF_FILE_ORDERED_DRINK, 0);
				SharedPreferences.Editor editor = pref.edit();
				editor.remove(Constants.PREF_KEY_ORDERED_DRINK_JSON);
				editor.commit();
				
				
				Intent intent = new Intent(OrderReviewActivity.this, OrderConfirmationActivity.class);
				intent.putExtra(Constants.ORDER_ID_PARAM_NAME, response.getOrderId());
				intent.putExtra(Constants.DLV_CONTACT_NAME_PARAM_NAME, tvContactName.getText());
				intent.putExtra(Constants.DLV_CONTACT_PHONE_PARAM_NAME, tvContactPhone.getText());
				intent.putExtra(Constants.DLV_CONTACT_ADDR_PARAM_NAME, tvDeliveryAddr.getText());

				startActivity(intent);
			}
		}
	}

}
