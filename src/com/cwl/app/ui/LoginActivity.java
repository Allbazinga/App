package com.cwl.app.ui;

import com.cwl.app.R;
import com.cwl.app.bean.User;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.utils.CommonUtils;
import com.cwl.app.utils.PreferenceUtils;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements EMCallBack {

	private static final String TAG = "LoginActivity";
	private static final int LOGIN_EM_SUCCESS = 0;
	private static final int LOGIN_EM_FAILED = 1;
    //private static final int LOGIN_SEVER_SUCCESS = 2;
	private static final int LOGIN_SEVER_FAILED = 3;
	private EditText edt_name, edt_pwd;
	private Button btn_login;
	private ProgressDialog pd = null;
	private String userId = "";
	private String pwd = "";
	private User user = null;
	@SuppressLint("HandlerLeak")
	private Handler loginHandler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == LOGIN_EM_FAILED) {
				Log.d("main", "登陆聊天服务器失败！");
				Toast.makeText(LoginActivity.this, msg.obj.toString(), 0)
						.show();
				pd.dismiss();
			} else if (msg.what == LOGIN_EM_SUCCESS && user != null) {
				PreferenceUtils mPreferenceUtils = PreferenceUtils
						.getInstance(LoginActivity.this);
				mPreferenceUtils.setSettingUserId(user.getId());
				mPreferenceUtils.setSettingUserName(user.getName());
				mPreferenceUtils.setSettingUserPwd(pwd);
				mPreferenceUtils.setSettingUserStartScl(user.getGrade());
				mPreferenceUtils.setSettingUserSex(user.getSex());
				mPreferenceUtils.setSettingUserPic(user.getHeaderurl());
				mPreferenceUtils.setSettingUserSign(user.getSign());
				mPreferenceUtils.setSettingUserArea(user.getHome());
				mPreferenceUtils.setSettingUserScl(user.getScl());
			    mPreferenceUtils.setSettingUserStartScl(user.getGrade());
				mPreferenceUtils.setSettingUserMajor(user.getMajor());
				Log.i(TAG, user.getId() + user.getName());
				EMGroupManager.getInstance().loadAllGroups();
				EMChatManager.getInstance().loadAllConversations();
				Log.d("main", "登陆聊天服务器成功！");
				pd.dismiss();
				startActivity(new Intent(LoginActivity.this, MainActivity.class));
			} else if (msg.what == LOGIN_SEVER_FAILED) {
				Toast.makeText(LoginActivity.this, "登陆失败", 0).show();
				pd.dismiss();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		edt_name = (EditText) this.findViewById(R.id.edt_name);
		edt_pwd = (EditText) this.findViewById(R.id.edt_pwd);
		btn_login = (Button) this.findViewById(R.id.btn_login);
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available,
					Toast.LENGTH_SHORT).show();
			return;
		}
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
			}
		});
		((Button) this.findViewById(R.id.btn_register))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(LoginActivity.this,
								RegisterActivity.class));
						finish();
					}
				});
	}

	public void login() {
		userId = edt_name.getText().toString().trim();
		pwd = edt_pwd.getText().toString().trim();
		if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		pd = new ProgressDialog(LoginActivity.this);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("正在登陆...");
		pd.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				user = HttpClientApi.LoginApi(userId, pwd);
				if (user != null) {
					EMChatManager.getInstance().login(userId, pwd,
							LoginActivity.this);
				} else {
					loginHandler.sendEmptyMessage(LOGIN_SEVER_FAILED);
				}
			}
		}).start();
		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * EMChatManager.getInstance().login(userId, pwd, new EMCallBack() {//
		 * 回调
		 * 
		 * @Override public void onSuccess() { runOnUiThread(new Runnable() {
		 * public void run() { EMGroupManager.getInstance() .loadAllGroups();
		 * EMChatManager.getInstance() .loadAllConversations(); Log.d("main",
		 * "登陆聊天服务器成功！"); pd.dismiss(); PreferenceUtils.getInstance(
		 * LoginActivity.this) .setSettingUserName(userId);
		 * PreferenceUtils.getInstance( LoginActivity.this)
		 * .setSettingUserPwd(pwd); startActivity(new Intent(
		 * LoginActivity.this, MainActivity.class));
		 * LoginActivity.this.finish(); } }); }
		 * 
		 * @Override public void onProgress(int progress, String status) {
		 * 
		 * }
		 * 
		 * @Override public void onError(int code, final String message) {
		 * runOnUiThread(new Runnable() { public void run() { Log.d("main",
		 * "登陆聊天服务器失败！"); Toast.makeText(LoginActivity.this, message, 0).show();
		 * pd.dismiss(); } }); } }); } }).start();
		 */
	}

	@Override
	public void onError(int arg0, final String arg1) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.obj = arg1;
		msg.what = LOGIN_EM_FAILED;
		loginHandler.sendMessage(msg);
	}

	@Override
	public void onProgress(int arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		loginHandler.sendEmptyMessage(LOGIN_EM_SUCCESS);
	}
}
