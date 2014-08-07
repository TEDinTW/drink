package com.example.drinkorder.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;
import com.example.drinkorder.adapter.OrderedDrinkAdapter;
import com.example.drinkorder.adapter.bean.OrderedDrinkAdapterElement;
import com.example.drinkorder.bean.jackson.Drink;
import com.example.drinkorder.bean.jackson.DrinkData;
import com.example.drinkorder.bean.jackson.OrderedDrink;
import com.example.drinkorder.bean.jackson.OrderedDrinks;
import com.example.drinkorder.dialog.DeleteConfirmationDialog;
import com.example.drinkorder.dialog.DrinkDetailPickerDialog;
import com.example.drinkorder.dialog.GenericConfirmationDialogListerner;
import com.example.drinkorder.dialog.GenericDeleteConfirmationDialogListerner;
import com.example.drinkorder.utils.BeansToAdapterElementsConverter;
import com.example.drinkorder.utils.JsonManager;
import com.example.drinkorder.utils.WebServiceGateway;

public class DrinkOrderActivity extends FragmentActivity implements GenericConfirmationDialogListerner, GenericDeleteConfirmationDialogListerner {
	private Spinner spChooseDrink;
	private ListView lvOrderDrinks;
	private TextView tvTotalQty, tvTotalAmount;
	private List<OrderedDrink> orderedDrinks;
	private DrinkData drinkData;
	private List<String> drinKNames;
	private List<String> sugarLevels;
	private List<String> iceLevels;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_drink);

		orderedDrinks = new ArrayList<OrderedDrink>();
		tvTotalQty = (TextView) findViewById(R.id.tvTotalQty);
		tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
		spChooseDrink = (Spinner) findViewById(R.id.spChooseDrink);
		new GetDrinkDataTask().execute();

		lvOrderDrinks = (ListView) findViewById(R.id.lvOrderDrinks);
		registerForContextMenu(lvOrderDrinks);
		lvOrderDrinks.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				arg0.showContextMenuForChild(arg1);
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, Constants.MENU_GENERIC_MTN_UPDATE, 0, R.string.menu_update);
		menu.add(0, Constants.MENU_GENERIC_MTN_DELETE, 0, R.string.menu_delete);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case Constants.MENU_GENERIC_MTN_UPDATE:
			OrderedDrink orderedDrink = orderedDrinks.get(info.position);
			Drink drink = getDrinkById(orderedDrink.getId());

			Bundle args = new Bundle();
			args.putInt(Constants.CMD, Constants.CMD_UPDATE);
			args.putInt(Constants.TARGET_ID, info.position);
			args.putStringArrayList(Constants.SUGAR_LEVELS_PARAM_NAME, (ArrayList<String>) sugarLevels);
			args.putStringArrayList(Constants.ICE_LEVELS_PARAM_NAME, (ArrayList<String>) iceLevels);
			args.putSerializable(Constants.DRINK_BEAN_PARAM_NAME, drink);
			args.putSerializable(Constants.ORDERED_DRINK_BEAN_PARAM_NAME, orderedDrinks.get(info.position));
			showDrinkDetailDialog(args);
			break;
		case Constants.MENU_GENERIC_MTN_DELETE:
			int targetId = (Integer) info.position;
			DialogFragment newFragment = DeleteConfirmationDialog.newInstance(targetId);
			newFragment.show(getFragmentManager(), "dialog");
			break;
		}

		return super.onContextItemSelected(item);
	}

	protected Drink getDrinkById(String id) {
		if (drinkData != null && drinkData.getDrinks() != null && drinkData.getDrinks().length > 0) {
			for (Drink drink : drinkData.getDrinks()) {
				if (drink.getId().equals(id)) {
					return drink;
				}
			}
		}

		return null;
	}

	public void addDrink(View view) {
		Bundle args = new Bundle();
		Drink selectedDrink = drinkData.getDrinks()[spChooseDrink.getSelectedItemPosition()];

		args.putSerializable(Constants.DRINK_BEAN_PARAM_NAME, selectedDrink);
		args.putInt(Constants.CMD, Constants.CMD_ADD);
		args.putStringArrayList(Constants.SUGAR_LEVELS_PARAM_NAME, (ArrayList<String>) sugarLevels);
		args.putStringArrayList(Constants.ICE_LEVELS_PARAM_NAME, (ArrayList<String>) iceLevels);

		showDrinkDetailDialog(args);
	}

	protected void showDrinkDetailDialog(Bundle args) {
		DialogFragment newFragment = DrinkDetailPickerDialog.newInstance(args);
		newFragment.show(getFragmentManager(), "dialog");
	}

	protected void refreshList(List<OrderedDrink> orderedDrinks) {
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

	
	
	
	@Override
	public void doPositiveClick(Bundle bundle) {
		int cmd = bundle.getInt(Constants.CMD);
		OrderedDrink orderedDrink = (OrderedDrink) bundle.getSerializable(Constants.ORDERED_DRINK_BEAN_PARAM_NAME);
		switch (cmd) {
		case Constants.CMD_ADD:
			orderedDrinks.add(orderedDrink);
			break;
		case Constants.CMD_UPDATE:
			int targetId = bundle.getInt(Constants.TARGET_ID);
			orderedDrinks.set(targetId, orderedDrink);
			break;
		}

		serializedOrderDrinks(orderedDrinks);
		refreshList(orderedDrinks);
	}

	@Override
	public void doNegativeClick(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDeletePositiveClick(Bundle bundle) {
		int targetId = bundle.getInt(Constants.TARGET_ID);
		orderedDrinks.remove(targetId);
		serializedOrderDrinks(orderedDrinks);
		refreshList(orderedDrinks);
	}

	@Override
	public void doDeleteNegativeClick(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		super.onResume();

		if(JsonManager.getOrderedDrinks(this)!=null){
			orderedDrinks = JsonManager.getOrderedDrinks(this);
		}
		
		refreshList(orderedDrinks);
	}
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.muSignOut:
			SharedPreferences pref = getSharedPreferences(Constants.PREF_FILE_CREDENTIAL, 0);
			SharedPreferences.Editor editor = pref.edit();
			editor.remove(Constants.PREF_KEY_AUTHENTICATED_USER_JSON);
			editor.remove(Constants.PREF_KEY_CREDENTIAL_SAVED);
			editor.commit();

			Intent intent = new Intent(this, SignInActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void serializedOrderDrinks(List<OrderedDrink> orderedDrinks) {
		if (orderedDrinks != null && !orderedDrinks.isEmpty()) {
			OrderedDrinks orderDrinksBean = new OrderedDrinks();
			orderDrinksBean.setDrinks(orderedDrinks);

			ObjectMapper mapper = new ObjectMapper();
			try {
				String jsonStr = mapper.writeValueAsString(orderDrinksBean);
				SharedPreferences pref = getSharedPreferences(Constants.PREF_FILE_ORDERED_DRINK, 0);
				SharedPreferences.Editor editor = pref.edit();
				editor.putString(Constants.PREF_KEY_ORDERED_DRINK_JSON, jsonStr);

				// Commit the edits!
				editor.commit();

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
		}
	}

	private class GetDrinkDataTask extends AsyncTask<Void, Void, DrinkData> {
		@Override
		protected DrinkData doInBackground(Void... xx) {
			return WebServiceGateway.getDrinkData();

		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(DrinkData pDrinkData) {
			drinkData = pDrinkData;

			drinKNames = new ArrayList<String>();

			sugarLevels = new ArrayList<String>();
			iceLevels = new ArrayList<String>();

			if (drinkData != null) {
				if (drinkData.getDrinks() != null && drinkData.getDrinks().length > 0) {
					for (Drink drink : drinkData.getDrinks()) {
						drinKNames.add(drink.getDrinkName());
					}
				}
				if (drinkData.getSugarLevels() != null && drinkData.getSugarLevels().length > 0) {
					for (String sugarLevel : drinkData.getSugarLevels()) {
						sugarLevels.add(sugarLevel);
					}
				}

				if (drinkData.getIceLevels() != null && drinkData.getIceLevels().length > 0) {
					for (String iceLevel : drinkData.getIceLevels()) {
						iceLevels.add(iceLevel);
					}
				}

			}

			ArrayAdapter<String> drinksAdapter = new ArrayAdapter<String>(DrinkOrderActivity.this, android.R.layout.simple_spinner_item, drinKNames);
			drinksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spChooseDrink.setAdapter(drinksAdapter);
		}
	}

	public void moveToReview(View view) {
		if (orderedDrinks == null || orderedDrinks.isEmpty()) {
			Toast.makeText(this, R.string.error_no_commerce_item_selected, Toast.LENGTH_LONG).show();
			return;
		}

		Intent intent = new Intent(this, OrderReviewActivity.class);
		startActivity(intent);

	}

	@Override
	protected void onPause() {
		super.onPause();
		
		serializedOrderDrinks(orderedDrinks);
		
	}

}
