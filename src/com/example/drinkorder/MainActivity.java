package com.example.drinkorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drinkorder.activity.SignInActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void showSignIn(View view){
		Intent intent = new Intent(this, SignInActivity.class);
		startActivity(intent);
	}
	
}
