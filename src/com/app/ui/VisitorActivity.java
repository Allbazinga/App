package com.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.VisitorAdapter;
import com.app.bean.VisitorBean;
import com.app.utils.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class VisitorActivity extends Activity {

	private ListView ltv_visitor;
	private List<VisitorBean> datalist;
	private VisitorAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visitor);
		initView();
		
	}

	public void initView(){
	
		ltv_visitor = (ListView) findViewById(R.id.ltv_visitor);
		datalist = new ArrayList<VisitorBean>();
		datalist.clear();
		VisitorBean today = new VisitorBean(null, Constants.VISITOR_TODAY, null);
		datalist.add(today);
		VisitorBean visitor = new VisitorBean(getResources().getDrawable(R.drawable.pic_head3),
				"UI做到我要吐了",
				getResources().getDrawable(R.drawable.pic_male));
		for(int i = 0; i < 4; i++){
			datalist.add(visitor);
		}
		VisitorBean yesterday = new VisitorBean(null, Constants.VISITOR_YESTERDAY, null);
		datalist.add(yesterday);
		for(int i = 0; i < 4; i++){
			datalist.add(visitor);
		}
		adapter = new VisitorAdapter(this, datalist);
		ltv_visitor.setAdapter(adapter);
	}
	
	
}
