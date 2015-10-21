package com.cwl.app.ui;


import com.cwl.app.R;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.utils.PreferenceUtils;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private ProgressDialog pd = null;
	public static final String TAG = "RegisterActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		((Button) this.findViewById(R.id.btn_register))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						getUserId();
					}
				});
	}

	public void getUserId() {
		final String nickName = ((EditText) this.findViewById(R.id.edt_name))
				.getText().toString().trim();
		final String pwd = ((EditText) this.findViewById(R.id.edt_pwd))
				.getText().toString().trim();
		if (TextUtils.isEmpty(nickName) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		pd = new ProgressDialog(this);
		pd.setMessage("正在注册...");
		pd.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String userId = null;
				userId = HttpClientApi.RegisterApi(nickName, pwd);
					Log.i(TAG, userId);
					if(userId != null){
						register(userId, nickName, pwd);
					}
			}
		}).start();

	}

	public void register(final String userId, final String userName,
			final String pwd) {
		/*new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
*/				try {
					EMChatManager.getInstance().createAccountOnServer(userId,
							pwd);
					runOnUiThread(new Runnable() {
						public void run() {
							if (!RegisterActivity.this.isFinishing()) {
								PreferenceUtils.getInstance(
										RegisterActivity.this)
										.setSettingUserId(userId);
								PreferenceUtils.getInstance(
										RegisterActivity.this)
										.setSettingUserName(userName);
								PreferenceUtils.getInstance(
										RegisterActivity.this)
										.setSettingUserPwd(pwd);
								if (pd != null) {
									pd.dismiss();
								}
								Toast.makeText(RegisterActivity.this, "注册成功",
										Toast.LENGTH_SHORT).show();
								startActivity(new Intent(RegisterActivity.this,
										MainActivity.class));
								RegisterActivity.this.finish();
							}

						}
					});
				} catch (final EaseMobException e) {
					// TODO Auto-generated catch block
					runOnUiThread(new Runnable() {
						@SuppressLint("ShowToast")
						public void run() {
							if (!RegisterActivity.this.isFinishing())
								pd.dismiss();
							if (e != null && e.getMessage() != null) {
								String errorMsg = e.getMessage();
								if (errorMsg
										.indexOf("EMNetworkUnconnectedException") != -1) {
									Toast.makeText(getApplicationContext(),
											"网络异常，请检查网络！", 0).show();
								} else if (errorMsg.indexOf("conflict") != -1) {
									// 用户名重复
									Toast.makeText(getApplicationContext(),
											"未知错误，请重试！", 0).show();
								} else if (errorMsg
										.indexOf("not support the capital letters") != -1) {
									Toast.makeText(getApplicationContext(),
											"用户名不支持大写字母！", 0).show();
								} else {
									Toast.makeText(getApplicationContext(),
											"注册失败: " + e.getMessage(), 1)
											.show();
								}

							} else {
								Toast.makeText(getApplicationContext(),
										"注册失败: 未知异常", 1).show();
							}
						}
					});
				}
		/*	}
		}).start();*/
	}
}
