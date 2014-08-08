package com.example.drinkorder.activity;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;
import com.example.drinkorder.bean.jackson.AuthenticateRequest;
import com.example.drinkorder.bean.jackson.AuthenticateResponse;
import com.example.drinkorder.bean.jackson.User;
import com.example.drinkorder.dialog.GenericAlertDialog;
import com.example.drinkorder.utils.PreferencesManager;
import com.example.drinkorder.utils.WebServiceGateway;

public class SignInActivity extends Activity {
	private EditText etUserName, etPassword;
	private CheckBox chkRememberMe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean credentialSaved = PreferencesManager.isCredentialSaved(this);
		String jsonStr = PreferencesManager.loadUser(this);

		if (credentialSaved && jsonStr != null) {
			Intent intent = new Intent(this, DrinkOrderActivity.class);
			startActivity(intent);
			return;
		}

		setContentView(R.layout.sign_in);

		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		chkRememberMe = (CheckBox) findViewById(R.id.chkRememberMe);
	}

	public void signIn(View view) {
		String userName = etUserName.getText().toString().trim();
		String password = etPassword.getText().toString().trim();

		new WebServiceTask().execute(userName, password);

	}

	public void reset(View view) {
		etUserName.setText(null);
		etPassword.setText(null);
	}

	private void serializedToPreference(User user) {
		if (user != null) {

			ObjectMapper mapper = new ObjectMapper();
			try {
				String jsonStr = mapper.writeValueAsString(user);

				PreferencesManager.serializeUser(this, jsonStr);

				// if remember me is checked, turn on the auto sign in flag
				if (chkRememberMe.isChecked()) {
					PreferencesManager.setCredentialSaved(this, true);
				}

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

	private class WebServiceTask extends AsyncTask<String, Void, AuthenticateResponse> {
		@Override
		protected AuthenticateResponse doInBackground(String... params) {
			AuthenticateRequest authReq = new AuthenticateRequest();
			authReq.setUserId(params[0]);
			authReq.setUserPwd(params[1]);

			return WebServiceGateway.authenticate(authReq);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(AuthenticateResponse response) {
			if (!response.isHasErrors()) {
				// Serialize the authenticated user to preference file
				User user = new User();
				user.setUserId(response.getUserId());
				user.setUserName(response.getUserName());
				user.setUserPhone(response.getUserPhone());
				user.setUserAddress(response.getUserAddress());
				serializedToPreference(user);

				Intent intent = new Intent(SignInActivity.this, DrinkOrderActivity.class);
				startActivity(intent);
			} else {
				String errorMessage = null;
				if (response.getErrors() != null && !response.getErrors().isEmpty()) {
					// only retrieve the first error message
					errorMessage = response.getErrors().get(0).getErrorMessage();
				} else {
					errorMessage = SignInActivity.this.getResources().getString(R.string.error_msg_unexpected_error);
				}

				Bundle args = new Bundle();
				args.putString(Constants.GENERIC_ALERT_DIALOG_TITLE_PARAM_NAME, SignInActivity.this.getResources().getString(R.string.error_generic_title));
				args.putString(Constants.GENERIC_ALERT_DIALOG_TEXT_PARAM_NAME, errorMessage);
				DialogFragment newFragment = GenericAlertDialog.newInstance(args);
				newFragment.show(getFragmentManager(), "dialog");
			}

		}
	}

}
