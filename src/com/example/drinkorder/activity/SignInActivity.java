package com.example.drinkorder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drinkorder.Constants;
import com.example.drinkorder.R;

public class SignInActivity extends Activity {
	EditText etUserName, etPassword;
	Button btnSignIn, btnAbort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);

		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
	}

	public void signIn(View view) {
		String userName=etUserName.getText().toString();
		String password=etPassword.getText().toString();
		
		if("jason".equals(userName) && "1234".equals(password)){
			Intent intent = new Intent(this, DrinkOrderActivity.class);
			intent.putExtra(Constants.USER_NAME_PARAM, userName);
			startActivity(intent);
		}else{
			Toast.makeText(this, R.string.sign_in_failure, Toast.LENGTH_LONG).show();
		}
	}
	
	public void reset(View view){
		etUserName.setText(null);
		etPassword.setText(null);
	}

}
