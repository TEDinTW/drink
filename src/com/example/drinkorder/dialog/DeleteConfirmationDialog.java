package com.example.drinkorder.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;

public class DeleteConfirmationDialog extends DialogFragment {
	public static DeleteConfirmationDialog newInstance(int targetId) {
		DeleteConfirmationDialog dialog = new DeleteConfirmationDialog();
		Bundle args = new Bundle();
		args.putInt(Constants.TARGET_ID, targetId);
		dialog.setArguments(args);
		return dialog;
	}

	public DeleteConfirmationDialog() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new AlertDialog.Builder(getActivity()).setMessage(R.string.dlg_msg_confirm_delete)
				.setPositiveButton(R.string.dlg_btn_yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((GenericDeleteConfirmationDialogListerner) getActivity()).doDeletePositiveClick(getArguments());
					}
				}).setNegativeButton(R.string.dlg_btn_no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((GenericDeleteConfirmationDialogListerner) getActivity()).doDeleteNegativeClick(getArguments());
					}
				}).create();

		return dialog;
	}
}
