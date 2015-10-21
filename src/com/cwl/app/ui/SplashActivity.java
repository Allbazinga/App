package com.cwl.app.ui;

import java.util.Timer;
import java.util.TimerTask;

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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity implements EMCallBack {

	private String userId;
	private String pwd;
	private final static String TAG = "SplashActivity";
	private static final int LOGIN_EM_SUCCESS = 0;
	private static final int LOGIN_EM_FAILED = 1;
	// private static final int LOGIN_SEVER_SUCCESS = 2;
	private static final int LOGIN_SEVER_FAILED = 3;
	private User user = null;
	private ProgressDialog pd = null;
	private TextView tv_version;
	@SuppressLint("HandlerLeak")
	private Handler loginHandler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == LOGIN_EM_FAILED) {
				pd.dismiss();
				startActivity(new Intent(SplashActivity.this,
						LoginActivity.class));
			} else if (msg.what == LOGIN_EM_SUCCESS && user != null) {
				PreferenceUtils mPreferenceUtils = PreferenceUtils
						.getInstance(SplashActivity.this);
				mPreferenceUtils.setSettingUserId(user.getId());
				mPreferenceUtils.setSettingUserName(user.getName());
				if(!user.getSex().equals("")){
					mPreferenceUtils.setSettingUserSex(user.getSex());
				}
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
				startActivity(new Intent(SplashActivity.this,
						MainActivity.class));
				SplashActivity.this.finish();
			} else if (msg.what == LOGIN_SEVER_FAILED) {
				pd.dismiss();
				startActivity(new Intent(SplashActivity.this,
						LoginActivity.class));
				SplashActivity.this.finish();
			}
		}

	};

	@SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_version = (TextView) this.findViewById(R.id.tv_version);
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			tv_version.setText("v " + version);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available,
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		PreferenceUtils.getInstance(this).setSettingUserSex("男");
		userId = PreferenceUtils.getInstance(SplashActivity.this)
				.getSettingUserId();
		pwd = PreferenceUtils.getInstance(SplashActivity.this)
				.getSettingsUserPwd();

		Log.i(TAG, userId + pwd);
		if (userId.equals("") || pwd.equals("")) {
			splash();
		} else {
			login();
		}

	}

	public void splash() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity(new Intent(SplashActivity.this,
						LoginActivity.class));
				SplashActivity.this.finish();
			}
		}, 2 * 1000);
	}

	public void login() {
		pd = new ProgressDialog(SplashActivity.this);
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
							SplashActivity.this);
				} else {
					loginHandler.sendEmptyMessage(LOGIN_SEVER_FAILED);
				}
			}
		}).start();
	}

	@Override
	public void onError(int arg0, String arg1) {
		// TODO Auto-generated method stub
		loginHandler.sendEmptyMessage(LOGIN_EM_FAILED);
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
