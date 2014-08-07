package com.example.drinkorder.dialog;

import java.util.List;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;
import com.example.drinkorder.bean.jackson.Drink;
import com.example.drinkorder.bean.jackson.OrderedDrink;

public class DrinkDetailPickerDialog extends DialogFragment {

	public static DrinkDetailPickerDialog newInstance(Bundle args) {
		DrinkDetailPickerDialog dialog = new DrinkDetailPickerDialog();
		dialog.setArguments(args);
		return dialog;
	}

	public DrinkDetailPickerDialog() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.drink_detail_dialog, container, false);

		Bundle args = getArguments();
		final int cmd = args.getInt(Constants.CMD);
		final int targetId = args.getInt(Constants.TARGET_ID);
		final List<String> sugarLevels = args.getStringArrayList(Constants.SUGAR_LEVELS_PARAM_NAME);
		final List<String> iceLevels = args.getStringArrayList(Constants.ICE_LEVELS_PARAM_NAME);

		final Spinner spSugarLevel = (Spinner) v.findViewById(R.id.spSugarLevel);
		final Spinner spIceLevel = (Spinner) v.findViewById(R.id.spIceLevel);
		final EditText etDrinkQuantity = (EditText) v.findViewById(R.id.etDrinkQuantity);
		final Drink targetDrinkBean;

		ArrayAdapter<String> sugarLevelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sugarLevels);
		sugarLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSugarLevel.setAdapter(sugarLevelAdapter);

		ArrayAdapter<String> iceLevelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, iceLevels);
		iceLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spIceLevel.setAdapter(iceLevelAdapter);

		targetDrinkBean = (Drink) args.getSerializable(Constants.DRINK_BEAN_PARAM_NAME);
		this.getDialog().setTitle(targetDrinkBean.getDrinkName());

		switch (cmd) {
		case Constants.CMD_ADD:
			etDrinkQuantity.setText("1");
			break;
		case Constants.CMD_UPDATE:
			OrderedDrink orderedDrink = (OrderedDrink) args.getSerializable(Constants.ORDERED_DRINK_BEAN_PARAM_NAME);
			spSugarLevel.setSelection(((ArrayAdapter<String>) spSugarLevel.getAdapter()).getPosition(orderedDrink.getSugarLevel()));
			spIceLevel.setSelection(((ArrayAdapter<String>) spIceLevel.getAdapter()).getPosition(orderedDrink.getIceLevel()));
			etDrinkQuantity.setText(String.valueOf(orderedDrink.getQuantity()));
			break;
		}

		Button btnConfirmDrinkDetail = (Button) v.findViewById(R.id.btnConfirmDrinkDetail);
		Button btnCancel = (Button) v.findViewById(R.id.btnCancel);		
		Button btnQtyInc = (Button) v.findViewById(R.id.btnQtyInc);
		Button btnQtyDec = (Button) v.findViewById(R.id.btnQtyDec);

		btnQtyInc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String curQtyStr = etDrinkQuantity.getText().toString();
				try {
					Integer curQty = Integer.parseInt(curQtyStr);
					etDrinkQuantity.setText(String.valueOf(curQty + 1));
				} catch (NumberFormatException e) {

				}
			}
		});

		btnQtyDec.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String curQtyStr = etDrinkQuantity.getText().toString();
				try {
					Integer curQty = Integer.parseInt(curQtyStr);
					if (curQty.intValue() > 1) {
						etDrinkQuantity.setText(String.valueOf(curQty - 1));
					}
				} catch (NumberFormatException e) {

				}
			}
		});

		btnConfirmDrinkDetail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				OrderedDrink orderedDrink = new OrderedDrink();

				// workaround way to set the id
				orderedDrink.setId(targetDrinkBean.getId());
				orderedDrink.setDrinkName(targetDrinkBean.getDrinkName());
				orderedDrink.setIceLevel(spIceLevel.getSelectedItem().toString());
				orderedDrink.setSugarLevel(spSugarLevel.getSelectedItem().toString());
				orderedDrink.setQuantity(Integer.parseInt(etDrinkQuantity.getText().toString()));
				orderedDrink.setUnitPrice(targetDrinkBean.getUnitPrice());
				bundle.putSerializable(Constants.ORDERED_DRINK_BEAN_PARAM_NAME, orderedDrink);
				bundle.putInt(Constants.CMD, cmd);

				switch (cmd) {
				case Constants.CMD_ADD:
					break;
				case Constants.CMD_UPDATE:
					bundle.putInt(Constants.TARGET_ID, targetId);
					break;
				}

				((GenericConfirmationDialogListerner) getActivity()).doPositiveClick(bundle);
				dismiss();
			}
		});
		
		
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

		return v;
	}

}
