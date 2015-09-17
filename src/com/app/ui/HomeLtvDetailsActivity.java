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

public class HomeLtvDetailsActivity extends Activity {

	private PullableListView mListView;
	private PullToRefreshLayout ptrl;
	private List<ReplyDetailBean> mDataList;
	private ReplyDetailAdapter mHomeDetailAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homeltv_details);
		initView();
		initListView();
	}

	public void initView(){
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_home_detail));
		ptrl.setOnRefreshListener(new MyListener());
	}
	

	public void initListView(){
		mListView = (PullableListView) findViewById(R.id.ltv_home_detail);
	    mDataList = new ArrayList<ReplyDetailBean>();
	    LayoutInflater
		 lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View
		 headerView = lif.inflate(R.layout.headerview_home_detail, null);
        mListView.addHeaderView(headerView);
        
		ReplyDetailBean mBeam1 = new ReplyDetailBean(getResources().getDrawable(R.drawable.pic_msg_head1),
				"暗龙袭击", 
				getResources().getDrawable(R.drawable.pic_male), 
				"4小时前",
				"1楼", 
				"连三角架都带真是作死，不过看有护栏，应该是景区，估计也不是很难爬");
		ReplyDetailBean mBeam2 = new ReplyDetailBean(getResources().getDrawable(R.drawable.pic_head2),
				"迷路的安娜", 
				getResources().getDrawable(R.drawable.pic_female), 
				"昨天12:23",
				"2楼", 
				"只为15字！");
		mDataList.add(mBeam2);
		mDataList.add(mBeam1);
		mDataList.add(mBeam2);
		mDataList.add(mBeam1);
		mHomeDetailAdapter = new ReplyDetailAdapter(this, mDataList);
		mListView.setAdapter(mHomeDetailAdapter);
	}
	
}
