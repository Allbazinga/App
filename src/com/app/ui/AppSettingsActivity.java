package com.app.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppSettingsActivity extends Activity implements OnClickListener {

	private RelativeLayout rlt_name_set, rlt_sign_set;
	private ImageView iv_settings_back;
	private TextView tv_name_set, tv_sign_set;
	private final static int NAME_SET = 0;
	private final static int SIGN_SET = 1;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_settings);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
		initView();

	}

	public void initView() {

		rlt_name_set = (RelativeLayout) findViewById(R.id.rlt_name_set);
		rlt_sign_set = (RelativeLayout) findViewById(R.id.rlt_sign_set);
		iv_settings_back = (ImageView) findViewById(R.id.iv_settings_back);
		rlt_name_set.setOnClickListener(this);
		rlt_sign_set.setOnClickListener(this);
		iv_settings_back.setOnClickListener(this);
		tv_name_set = (TextView) findViewById(R.id.tv_name_set);
		tv_sign_set = (TextView) findViewById(R.id.tv_sign_set);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.rlt_name_set:
			intent.setClass(this, NameSetActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_sign_set:
			intent.setClass(this, SignSetActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_settings_back:
			finish();
			break;
		default:
			break;
		}
	}

}
