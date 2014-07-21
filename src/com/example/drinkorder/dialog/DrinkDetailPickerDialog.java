package com.example.drinkorder.dialog;

import java.util.Date;

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
import com.example.drinkorder.bean.DrinkBean;

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

		String drinkName;

		final TextView tvDrinkName = (TextView) v.findViewById(R.id.tvDrinkName);

		final Spinner spSugarLevel = (Spinner) v.findViewById(R.id.spSugarLevel);
		final Spinner spIceLevel = (Spinner) v.findViewById(R.id.spIceLevel);
		final EditText etDrinkQuantity = (EditText) v.findViewById(R.id.etDrinkQuantity);

		switch (cmd) {
		case Constants.CMD_ADD:
			etDrinkQuantity.setText("1");
			drinkName = args.getString(Constants.DRINK_NAME_PARAM_NAME);
			tvDrinkName.setText(drinkName);
			break;
		case Constants.CMD_UPDATE:
			DrinkBean drinkbean = (DrinkBean) args.getSerializable(Constants.DRINK_BEAN_PARAM_NAME);
			drinkName = drinkbean.getDrinkName();
			args.putString(Constants.DRINK_NAME_PARAM_NAME, drinkName);
			tvDrinkName.setText(drinkName);
			spSugarLevel.setSelection(((ArrayAdapter<String>) spSugarLevel.getAdapter()).getPosition(drinkbean.getSugarLevel()));
			spIceLevel.setSelection(((ArrayAdapter<String>) spIceLevel.getAdapter()).getPosition(drinkbean.getIceLevel()));
			etDrinkQuantity.setText(String.valueOf(drinkbean.getQuantity()));
			break;
		}

		Button btnConfirmDrinkDetail = (Button) v.findViewById(R.id.btnConfirmDrinkDetail);
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
				DrinkBean drinkbean = new DrinkBean();

				// workaround way to set the id
				drinkbean.setId((int) (new Date()).getTime());
				drinkbean.setIceLevel(spIceLevel.getSelectedItem().toString());
				drinkbean.setSugarLevel(spSugarLevel.getSelectedItem().toString());
				drinkbean.setQuantity(Integer.parseInt(etDrinkQuantity.getText().toString()));
				bundle.putSerializable(Constants.DRINK_BEAN_PARAM_NAME, drinkbean);
				bundle.putInt(Constants.CMD, cmd);

				switch (cmd) {
				case Constants.CMD_ADD:
					drinkbean.setDrinkName(getArguments().getString(Constants.DRINK_NAME_PARAM_NAME));
					break;
				case Constants.CMD_UPDATE:
					drinkbean.setDrinkName(getArguments().getString(Constants.DRINK_NAME_PARAM_NAME));
					bundle.putInt(Constants.TARGET_ID, targetId);
					break;
				}

				((GenericConfirmationDialogListerner) getActivity()).doPositiveClick(bundle);
				dismiss();
			}
		});

		return v;
	}

}
