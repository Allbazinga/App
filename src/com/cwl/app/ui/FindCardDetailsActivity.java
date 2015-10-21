package com.cwl.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.cwl.app.R;
import com.cwl.app.adapter.ReplyDetailAdapter;
import com.cwl.app.bean.ReplyDetailBean;
import com.cwl.app.listener.MyListener;
import com.cwl.app.widget.PullToRefreshLayout;
import com.cwl.app.widget.PullableListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;

public class FindCardDetailsActivity extends Activity {

	private PullableListView mListView;
	private PullToRefreshLayout ptrl;
	private List<ReplyDetailBean> mDataList;
	private ReplyDetailAdapter mReplyDetailAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_details);
		initView();
		initListView();
	}

	public void initView(){
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_card_detail));
		ptrl.setOnRefreshListener(new MyListener());
	}
	

	public void initListView(){
		mListView = (PullableListView) findViewById(R.id.ltv_card_detail);
	    mDataList = new ArrayList<ReplyDetailBean>();
	    LayoutInflater
		 lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View
		 headerView = lif.inflate(R.layout.headerview_card_detail, null);
		headerView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mListView.addHeaderView(headerView);
        
		/*ReplyDetailBean mBeam1 = new ReplyDetailBean("",
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
		mDataList.add(mBeam2);
		mDataList.add(mBeam1);
		mDataList.add(mBeam2);
		mDataList.add(mBeam1);*/
		mReplyDetailAdapter = new ReplyDetailAdapter(this);
		mListView.setAdapter(mReplyDetailAdapter);
	}
	
}
