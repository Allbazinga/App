package com.cwl.app.ui;

import com.cwl.app.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class AppSettingsActivity extends Activity implements OnClickListener {


	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_settings);
		initView();

	}

	public void initView() {
	}

	public void back(View v){
		finish();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		default:
			break;
		}
	}

}
