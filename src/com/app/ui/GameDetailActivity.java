package com.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.ReplyDetailAdapter;
import com.app.bean.ReplyDetailBean;
import com.app.utils.MyListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public class GameDetailActivity extends Activity {

	private PullToRefreshLayout prlt;
	private PullableListView ltv_game_detail;
	private ReplyDetailAdapter adapter;
	private List<ReplyDetailBean> datalist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_detail);
		initView();
		initListView();
	}

	public void initView(){
		prlt = (PullToRefreshLayout) findViewById(R.id.refresh_game_detail);
		prlt.setOnRefreshListener(new MyListener());
	}
	
	public void initListView(){
		 ltv_game_detail = (PullableListView) findViewById(R.id.ltv_game_detail);
		 datalist = new ArrayList<ReplyDetailBean>();
		    LayoutInflater
			 lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View
			 headerView = lif.inflate(R.layout.headerview_game_detail, null);
	        ltv_game_detail.addHeaderView(headerView);
	        
			ReplyDetailBean mBeam1 = new ReplyDetailBean("",
					"暗龙袭击", 
					"", 
					"4小时前",
					"1楼", 
					"连三角架都带真是作死，不过看有护栏，应该是景区，估计也不是很难爬");
			ReplyDetailBean mBeam2 = new ReplyDetailBean("",
					"迷路的安娜", 
					"", 
					"昨天12:23",
					"2楼", 
					"只为15字！");
			datalist.add(mBeam2);
			datalist.add(mBeam1);
			datalist.add(mBeam2);
			datalist.add(mBeam1);
			adapter = new ReplyDetailAdapter(this, datalist);
			ltv_game_detail.setAdapter(adapter);
		
	}
	
	public void addHeader(){
		
	}
}
