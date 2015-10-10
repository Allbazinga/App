package com.app.ui;

import java.util.ArrayList;

import com.app.adapter.MineMarkAdapter;
import com.app.bean.MineMarkBean;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class MyMarkActivity extends Activity {

	private ListView ltv_my;
	private TextView tv_my_title;
	private MineMarkAdapter adapter = null;
	private ArrayList<MineMarkBean> datalist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		initView();
	}

	public void initView() {
		((TextView) this.findViewById(R.id.tv_actionbar)).setText(R.string.mark);;
		ltv_my = (ListView) findViewById(R.id.ltv_my);
		datalist = new ArrayList<MineMarkBean>();
		MineMarkBean bean = new MineMarkBean(getResources().getDrawable(R.drawable.pic_head3), "江左梅郎", "大连理工大学  2013级", "男", "1");
		datalist.add(bean);
		datalist.add(bean);
		datalist.add(bean);
		adapter = new MineMarkAdapter(this, datalist);
		ltv_my.setAdapter(adapter);
	}
}
