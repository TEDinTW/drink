package com.example.drinkorder.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;

public class ChangeDeliveryContactDialog extends DialogFragment {
	public static ChangeDeliveryContactDialog newInstance(Bundle args) {
		ChangeDeliveryContactDialog dialog = new ChangeDeliveryContactDialog();
		dialog.setArguments(args);
		return dialog;
	}

	public ChangeDeliveryContactDialog() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.change_delivery_contact, container, false);

		getDialog().setTitle(R.string.change_delivery_contact_title);
		
		Bundle args = getArguments();

		String contactName = args.getString(Constants.DLV_CONTACT_NAME_PARAM_NAME);
		String contactPhone = args.getString(Constants.DLV_CONTACT_PHONE_PARAM_NAME);
		String contactAddress = args.getString(Constants.DLV_CONTACT_ADDR_PARAM_NAME);
		final EditText etDlvContactName = (EditText) layout.findViewById(R.id.etDlvContactName);
		final EditText etDlvContactPhone = (EditText) layout.findViewById(R.id.etDlvContactPhone);
		final EditText etDlvContactAddr = (EditText) layout.findViewById(R.id.etDlvContactAddr);
		Button btnOk = (Button) layout.findViewById(R.id.btnOk);
		Button btnCancel = (Button) layout.findViewById(R.id.btnCancel);
		
		etDlvContactName.setText(contactName);
		etDlvContactPhone.setText(contactPhone);
		etDlvContactAddr.setText(contactAddress);


		
		btnOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString(Constants.DLV_CONTACT_NAME_PARAM_NAME, etDlvContactName.getText().toString());
				bundle.putString(Constants.DLV_CONTACT_PHONE_PARAM_NAME, etDlvContactPhone.getText().toString());
				bundle.putString(Constants.DLV_CONTACT_ADDR_PARAM_NAME, etDlvContactAddr.getText().toString());
				((GenericConfirmationDialogListerner) getActivity()).doPositiveClick(bundle);
				dismiss();
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

		return layout;
	}
	
	
	
}
