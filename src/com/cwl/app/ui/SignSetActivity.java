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

public class SignSetActivity extends Activity {

	private static final int SET_SUCCESS = 0;
	private static final int SET_FAILED = 1;
	private EditText edt_sign;
	private ProgressDialog pd = null;
	private String sign = "";
    @SuppressLint("HandlerLeak")
	private Handler nameSetHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == SET_SUCCESS){
				pd.dismiss();
				Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
				PreferenceUtils.getInstance(SignSetActivity.this).setSettingUserSign(sign);
				SignSetActivity.this.finish();
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
		setContentView(R.layout.activity_edit_sign);
		edt_sign = (EditText) this.findViewById(R.id.edt_sign_set);
		if(PreferenceUtils.getInstance(this).getSettingUserSign().equals("")){
			edt_sign.setHint("这个人很懒，什么都没留下");
		}else{
			edt_sign.setHint(PreferenceUtils.getInstance(this).getSettingUserSign());
		}
	}
	
    public void back(View v){
    	SignSetActivity.this.finish();
    }
    
    public void setSign(View v){
    	sign = edt_sign.getText().toString().trim();
    	if(sign.equals("")){
    		Toast.makeText(getApplicationContext(), "签名不能为空", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	pd = new ProgressDialog(SignSetActivity.this);
    	pd.setMessage("通讯中...");
    	pd.show();
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = HttpClientApi.SetUserInfoApi(PreferenceUtils
						.getInstance(SignSetActivity.this).getSettingUserId(),
						"userNote", sign);
				if(result == true){
					nameSetHandler.sendEmptyMessage(SET_SUCCESS);
				}else{
					nameSetHandler.sendEmptyMessage(SET_FAILED);
				}
			}
		}).start();
    }
	
}
