package com.cwl.app.ui;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.adapter.GainAdapter;
import com.cwl.app.bean.GainBean;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class OthersGainActivity extends Activity {

	private ListView ltv_my_gain;
	private GainAdapter adapter = null;
	private ArrayList<GainBean> datalist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		initView();
	}

	public void initView() {
		((TextView) this.findViewById(R.id.tv_actionbar)).setText("TA的成就");
		ltv_my_gain = (ListView) findViewById(R.id.ltv_my);
		LayoutInflater inflater = LayoutInflater.from(this);
		View header = inflater.inflate(R.layout.headerview_others_gain, null);
		ltv_my_gain.addHeaderView(header, null, false);
		datalist = new ArrayList<GainBean>();
		GainBean bean1 = new GainBean(getResources().getDrawable(
				R.drawable.pic_test1), "这是哪，这又是哪？", "在综一的教室迷路", "15", "0");
		datalist.add(bean1);
		GainBean bean2 = new GainBean(getResources().getDrawable(
				R.drawable.pic_test1), "嘿，你的球！", "在福佳足球场帮忙捡一次别人提到外面的球", "15",
				"1");
		datalist.add(bean2);
		adapter = new GainAdapter(this, datalist);
		ltv_my_gain.setAdapter(adapter);
	}
}
