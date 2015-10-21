package com.cwl.app.ui;

import com.cwl.app.R;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class OthersInfoDetailActivity extends Activity {

	private LruCache<String, Bitmap> lruCache;
	private String id, head, name, sex, home, grade, major;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_others_info_detail);
		lruCache = ImageCache.GetLruCache(this);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		head = intent.getStringExtra("head");
		name = intent.getStringExtra("name");
		sex = intent.getStringExtra("sex");
		home = intent.getStringExtra("home");
		grade = intent.getStringExtra("grade");
		major = intent.getStringExtra("major");
		initView();
	}

	public void back(View v) {
		finish();
	}

	public void initView() {
		ImageView iv_avatar = (ImageView) this.findViewById(R.id.iv_avatar);
		TextView tv_id = (TextView) this.findViewById(R.id.tv_id);
		TextView tv_name = (TextView) this.findViewById(R.id.tv_name);
		TextView tv_sex = (TextView) this.findViewById(R.id.tv_sex);
		TextView tv_home = (TextView) this.findViewById(R.id.tv_home);
		TextView tv_start_scl = (TextView) this.findViewById(R.id.tv_start_scl);
		TextView tv_major = (TextView) this.findViewById(R.id.tv_major);
		if (!head.equals("")) {
			iv_avatar.setTag(Constants.IP + head);
			new ImageCache(this, lruCache, iv_avatar, Constants.IP
					+ head, "APP", 120, 120);
		}
		tv_id.setText(id);
		tv_name.setText(name);
		tv_sex.setText(sex);
		tv_home.setText(home);
		tv_start_scl.setText(grade);
		tv_major.setText(major);
	}
}
