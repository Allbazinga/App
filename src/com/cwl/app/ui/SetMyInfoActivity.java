package com.cwl.app.ui;

import com.cwl.app.R;
import com.cwl.app.cityselect.City;
import com.cwl.app.utils.PreferenceUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SetMyInfoActivity extends Activity implements OnClickListener {

	private TextView tv_nick, tv_sign, tv_sex, tv_home, tv_start_scl, tv_major,
			tv_mine_name, tv_mine_id;
	private AlertDialog yearDialog = null;

	private City city = null;
	private static final int REQUEST_CITY = 1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_myinfo);
		initView();
	}

	public void initView() {
		tv_mine_name = (TextView) this.findViewById(R.id.tv_mine_name);
		tv_mine_name.setText(PreferenceUtils.getInstance(this)
				.getSettingUserNickName());
		tv_mine_id = (TextView) this.findViewById(R.id.tv_mine_id);
		tv_nick = (TextView) this.findViewById(R.id.tv_name);
		tv_nick.setText(PreferenceUtils.getInstance(this)
				.getSettingUserNickName());
		tv_sign = (TextView) this.findViewById(R.id.tv_sign);
		tv_sex = (TextView) this.findViewById(R.id.tv_sex);
		tv_home = (TextView) this.findViewById(R.id.tv_home);
		tv_start_scl = (TextView) this.findViewById(R.id.tv_start_scl);
		tv_major = (TextView) this.findViewById(R.id.tv_major);

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
				tv_start_scl.setText(years[position]);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == REQUEST_CITY){
			city = data.getParcelableExtra("city");
			tv_home.setText(city.getProvince() + "-" + city.getCity() + "-" + city.getDistrict());
		}
		
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
		case R.id.rlt_sex:

			break;
		case R.id.rlt_home:
			Intent intent = new Intent(this, CitySelectActivity.class);
			intent.putExtra("city", city);
			startActivityForResult(intent, REQUEST_CITY);
			break;
		case R.id.rlt_year:
			showYearPickerDialog();
			break;
		case R.id.rlt_major:
			break;
		case R.id.iv_setInfo_back:
			finish();
			break;
		case R.id.rlt_yearDialog:
			if(yearDialog != null){
				yearDialog.dismiss();
			}
			break;
		default:
			break;
		}
	}
}
