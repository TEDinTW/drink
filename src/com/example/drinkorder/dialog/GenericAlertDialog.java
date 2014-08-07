package com.example.drinkorder.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;

public class GenericAlertDialog extends DialogFragment {
	public static GenericAlertDialog newInstance(Bundle args) {
		GenericAlertDialog dialog = new GenericAlertDialog();
		dialog.setArguments(args);
		return dialog;
	}

	public GenericAlertDialog() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		String dialog_title = args.getString(Constants.GENERIC_ALERT_DIALOG_TITLE_PARAM_NAME);
		String dialog_text = args.getString(Constants.GENERIC_ALERT_DIALOG_TEXT_PARAM_NAME);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(dialog_title).setMessage(dialog_text).setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dismiss();
			}
		});

		return builder.create();
	}
}
