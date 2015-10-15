package com.cwl.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.cwl.app.R;
import com.cwl.app.adapter.VisitorAdapter;
import com.cwl.app.bean.VisitorBean;
import com.cwl.app.utils.Constants;

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
		VisitorBean visitor = new VisitorBean("",
				"UI做到我要吐了",
				"");
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
