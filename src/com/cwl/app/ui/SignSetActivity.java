package com.cwl.app.ui;

import com.cwl.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SignSetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_sign);
	}

	
}
