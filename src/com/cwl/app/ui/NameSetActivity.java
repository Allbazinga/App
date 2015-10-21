package com.cwl.app.ui;

import com.cwl.app.R;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.utils.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class NameSetActivity extends Activity {

	private static final int SET_SUCCESS = 0;
	private static final int SET_FAILED = 1;
	private EditText edt_name;
	private ProgressDialog pd = null;
	private String name;
    @SuppressLint("HandlerLeak")
	private Handler nameSetHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == SET_SUCCESS){
				pd.dismiss();
				Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
				PreferenceUtils.getInstance(NameSetActivity.this).setSettingUserName(name);
				NameSetActivity.this.finish();
			}else if(msg.what == SET_FAILED){
				pd.dismiss();
				Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
			}
		}
    	
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_name);
		edt_name = (EditText) this.findViewById(R.id.edt_name_set);
		edt_name.setHint(PreferenceUtils.getInstance(this).getSettingUserName());
	}

	public void back(View v) {
		this.finish();
	}

	public void setName(View v) {
		name = edt_name.getText().toString().trim();
		if (name.equals("")) {
			Toast.makeText(NameSetActivity.this, "昵称不能为空", Toast.LENGTH_SHORT)
					.show();
		} else {
			pd = new ProgressDialog(NameSetActivity.this);
			pd.setMessage("通讯中...");
			pd.show();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					boolean result = HttpClientApi.SetUserInfoApi(PreferenceUtils
							.getInstance(NameSetActivity.this).getSettingUserId(),
							"userName", name);
					if(result == true){
						nameSetHandler.sendEmptyMessage(SET_SUCCESS);
					}else{
						nameSetHandler.sendEmptyMessage(SET_FAILED);
					}
				}
			}).start();
		}

	}
}
