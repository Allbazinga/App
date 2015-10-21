package com.cwl.app.ui;

import com.cwl.app.R;
import com.cwl.app.cityselect.City;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;
import com.cwl.app.utils.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetMyInfoActivity extends Activity implements OnClickListener {

	private TextView tv_nick, tv_sign, tv_sex, tv_home, tv_start_scl, tv_major,
			tv_mine_name, tv_mine_id;
	private ImageView iv_mine_avatar;
	private AlertDialog yearDialog = null;
	private City city = null;
	private PreferenceUtils mPreferenceUtils;
	private LruCache<String, Bitmap> lruCache;
	private String sex = "";
	private ProgressDialog pd = null;
	private static final int REQUEST_CITY = 1;
	private static final int SEX_SET_SUCCESS = 2;
	private static final int SEX_SET_FAILED = 3;
	private static final int GRADE_SET_SUCCESS = 4;
	private static final int GRADE_SET_FAILED = 5;
	
	@SuppressLint("HandlerLeak")
	private Handler myInfoHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == SEX_SET_SUCCESS){
				pd.dismiss();
				mPreferenceUtils.setSettingUserSex(msg.obj.toString());
				tv_sex.setText(msg.obj.toString());
			}else if(msg.what == SEX_SET_FAILED){
				pd.dismiss();
				Toast.makeText(getApplicationContext(), "修改失败，请重试", Toast.LENGTH_SHORT).show();
			}else if(msg.what == GRADE_SET_SUCCESS){
				pd.dismiss();
				mPreferenceUtils.setSettingUserStartScl(msg.obj.toString()+"级");
				tv_start_scl.setText(msg.obj.toString()+"级");
			}else if(msg.what == GRADE_SET_FAILED){
				pd.dismiss();
				Toast.makeText(getApplicationContext(), "修改失败，请重试", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_myinfo);
		mPreferenceUtils = PreferenceUtils.getInstance(this);
		lruCache = ImageCache.GetLruCache(this);
		initView();
	}

	public void initView() {
		iv_mine_avatar = (ImageView) this.findViewById(R.id.iv_mine_avatar);
		tv_mine_name = (TextView) this.findViewById(R.id.tv_mine_name);
		tv_mine_id = (TextView) this.findViewById(R.id.tv_mine_id);
		tv_nick = (TextView) this.findViewById(R.id.tv_name);
		tv_sign = (TextView) this.findViewById(R.id.tv_sign);
		tv_sex = (TextView) this.findViewById(R.id.tv_sex);
		tv_home = (TextView) this.findViewById(R.id.tv_home);
		tv_start_scl = (TextView) this.findViewById(R.id.tv_start_scl);
		tv_major = (TextView) this.findViewById(R.id.tv_major);
	}

	public void refresh() {
		iv_mine_avatar.setImageResource(R.drawable.default_avatar);
		if (!mPreferenceUtils.getSettingUserPic().equals("")) {
			iv_mine_avatar.setTag(Constants.IP
					+ mPreferenceUtils.getSettingUserPic());
			new ImageCache(this, lruCache, iv_mine_avatar, Constants.IP
					+ mPreferenceUtils.getSettingUserPic(), "App", 320, 320);
		}
		tv_mine_name.setText(mPreferenceUtils.getSettingUserName());
		tv_mine_id.setText(mPreferenceUtils.getSettingUserId());
		tv_nick.setText(mPreferenceUtils.getSettingUserName());
		tv_sign.setText(mPreferenceUtils.getSettingUserSign());
		tv_sex.setText(mPreferenceUtils.getSettingUserSex());
		tv_home.setText(mPreferenceUtils.getSettingUserArea());
		tv_start_scl.setText(mPreferenceUtils.getSettingUserStartScl());
		tv_major.setText(mPreferenceUtils.getSettingUserMajor());
	}

	public void showYearPickerDialog() {
		yearDialog = new AlertDialog.Builder(this).create();
		yearDialog.show();
		yearDialog.getWindow().setContentView(R.layout.dialog_year);
		final String[] years = new String[26];
		for (int i = 1990; i < 2016; i++) {
			years[i - 1990] = Integer.toString(i);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.dialog_year_row, years);
		ListView ltv = (ListView) yearDialog.getWindow().findViewById(R.id.ltv);
		ltv.setAdapter(adapter);
		((RelativeLayout) yearDialog.getWindow().findViewById(
				R.id.rlt_yearDialog)).setOnClickListener(this);
		ltv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setGrade(years[position]);
			}
		});
	}

	public void setGrade(final String year){
		yearDialog.dismiss();
		pd = new ProgressDialog(SetMyInfoActivity.this);
		pd.setMessage("通讯中...");
		pd.show();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = HttpClientApi.SetUserInfoApi(mPreferenceUtils.getSettingUserId(), "userGrade", year);
				if (result == true) {
					Message msg = new Message();
					msg.obj = year;
					msg.what = GRADE_SET_SUCCESS;
					myInfoHandler.sendMessage(msg);
					return;
				}
				myInfoHandler.sendEmptyMessage(GRADE_SET_FAILED);
			}
		}).start();
	}
	
	public void setSex(View v){
		sex = mPreferenceUtils.getSettingUserSex();
		if(sex.equals("男")){
			setSex("女");
			return;
			}
		setSex("男");
	}
	
	public void setSex(final String sex){
		pd = new ProgressDialog(SetMyInfoActivity.this);
		pd.setMessage("通讯中...");
		pd.show();
		pd.setCanceledOnTouchOutside(false);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = HttpClientApi.SetUserInfoApi(mPreferenceUtils.getSettingUserId(), "userSex", sex);
				if(result == true){
					Message msg = new Message();
					msg.obj = sex;
					msg.what = SEX_SET_SUCCESS;
					myInfoHandler.sendMessage(msg);
					return;
				}
				myInfoHandler.sendEmptyMessage(SEX_SET_FAILED);
			}
		}).start();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == REQUEST_CITY) {
			city = data.getParcelableExtra("city");
			tv_home.setText(city.getProvince() + "-" + city.getCity() + "-"
					+ city.getDistrict());
		}
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		refresh();
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rlt_nick:
			startActivity(new Intent(this, NameSetActivity.class));
			break;
		case R.id.rlt_sign:
			startActivity(new Intent(this, SignSetActivity.class));
			break;
		case R.id.rlt_home:
			Intent intent = new Intent(this, CitySelectActivity.class);
			startActivityForResult(intent, REQUEST_CITY);
			break;
		case R.id.rlt_year:
			showYearPickerDialog();
			break;
		case R.id.rlt_major:
			Toast.makeText(SetMyInfoActivity.this, "暂未开放", Toast.LENGTH_SHORT).show();
			break;
		case R.id.iv_setInfo_back:
			Intent it = new Intent();
			it.putExtra("ifRefresh", true);
			setResult(RESULT_OK, it);
			finish();
			break;
		case R.id.rlt_yearDialog:
			if (yearDialog != null) {
				yearDialog.dismiss();
			}
			break;
		default:
			break;
		}
	}
}
