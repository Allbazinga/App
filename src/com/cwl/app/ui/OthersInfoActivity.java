package com.cwl.app.ui;

import java.util.ArrayList;
import com.cwl.app.R;
import com.cwl.app.adapter.MineNoteAdapter;
import com.cwl.app.bean.MineNoteBean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class OthersInfoActivity extends Activity implements OnClickListener {

	private ListView ltv_other_info;
	private MineNoteAdapter adapter = null;
	private ArrayList<MineNoteBean> datalist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_others_info);
		initView();
	}

	public void initView() {
		ltv_other_info = (ListView) findViewById(R.id.ltv_other_info);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View headerview = inflater
				.inflate(R.layout.headerview_other_info, null);
		((TextView) headerview.findViewById(R.id.tv_others_gain))
				.setOnClickListener(this);
		((TextView) headerview.findViewById(R.id.tv_others_info))
				.setOnClickListener(this);
		ltv_other_info.addHeaderView(headerview, null, true);

		datalist = new ArrayList<MineNoteBean>();
		MineNoteBean bean1 = new MineNoteBean(null,
				"我们被教导要记住思想，而不是人，因为人可能会失败，被杀死或是被遗忘，但是思想不会，不会不会不会不会不会不会",
				"2015年9月28日", "文青聚集地");
		datalist.add(bean1);
		MineNoteBean bean2 = new MineNoteBean(getResources().getDrawable(
				R.drawable.pic_test1), "我发誓，我真的活过", "2015年10月9日", "文青聚集地");
		for (int i = 0; i < 4; i++) {
			datalist.add(bean2);
		}
		adapter = new MineNoteAdapter(this, datalist);
		ltv_other_info.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.tv_others_gain:
			intent.setClass(this, OthersGainActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_others_info:
			intent.setClass(this, OthersInfoDetailActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
