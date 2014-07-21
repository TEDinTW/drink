package com.example.drinkorder.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;
import com.example.drinkorder.adapter.OrderedDrinkAdapter;
import com.example.drinkorder.adapter.bean.OrderedDrinkAdapterElement;
import com.example.drinkorder.bean.DrinkBean;
import com.example.drinkorder.dialog.DeleteConfirmationDialog;
import com.example.drinkorder.dialog.DrinkDetailPickerDialog;
import com.example.drinkorder.dialog.GenericConfirmationDialogListerner;
import com.example.drinkorder.dialog.GenericDeleteConfirmationDialogListerner;
import com.example.drinkorder.utils.BeansToAdapterElementsConverter;

public class DrinkOrderActivity extends FragmentActivity implements GenericConfirmationDialogListerner, GenericDeleteConfirmationDialogListerner {
	Spinner spChooseDrink;
	ListView lvOrderDrinks;
	List<DrinkBean> orderedDrinks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_drink);

		orderedDrinks = new ArrayList<DrinkBean>();

		String[] drinks = new String[] { "藍山咖啡", "深海烏龍", "西雅圖咖啡", "天堂咖啡" };

		spChooseDrink = (Spinner) findViewById(R.id.spChooseDrink);
		ArrayAdapter<String> drinksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, drinks);
		drinksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spChooseDrink.setAdapter(drinksAdapter);

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
			Bundle args = new Bundle();
			args.putInt(Constants.CMD, Constants.CMD_UPDATE);
			args.putInt(Constants.TARGET_ID, info.position);
			args.putSerializable(Constants.DRINK_BEAN_PARAM_NAME, orderedDrinks.get(info.position));
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

	public void addDrink(View view) {
		Bundle args = new Bundle();
		args.putString(Constants.DRINK_NAME_PARAM_NAME, spChooseDrink.getSelectedItem().toString());
		args.putInt(Constants.CMD, Constants.CMD_ADD);
		showDrinkDetailDialog(args);
	}

	protected void showDrinkDetailDialog(Bundle args) {
		DialogFragment newFragment = DrinkDetailPickerDialog.newInstance(args);
		newFragment.show(getFragmentManager(), "dialog");
	}

	protected void refreshList(List<DrinkBean> orderedDrinks) {
		OrderedDrinkAdapter adapter = null;
		List<OrderedDrinkAdapterElement> elements = BeansToAdapterElementsConverter.convert(orderedDrinks);
		if (elements != null && !elements.isEmpty()) {
			adapter = new OrderedDrinkAdapter(this, R.layout.ordered_drink_line_item, elements);
		}

		lvOrderDrinks.setAdapter(adapter);
	}

	@Override
	public void doPositiveClick(Bundle bundle) {
		int cmd = bundle.getInt(Constants.CMD);
		DrinkBean drinkbean = (DrinkBean) bundle.getSerializable(Constants.DRINK_BEAN_PARAM_NAME);
		switch (cmd) {
		case Constants.CMD_ADD:
			orderedDrinks.add(drinkbean);
			break;
		case Constants.CMD_UPDATE:
			int targetId = bundle.getInt(Constants.TARGET_ID);
			orderedDrinks.set(targetId, drinkbean);
			break;
		}

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
		refreshList(orderedDrinks);
	}

	@Override
	public void doDeleteNegativeClick(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		super.onResume();

		refreshList(orderedDrinks);
	}

}
