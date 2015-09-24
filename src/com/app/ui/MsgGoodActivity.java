package com.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.MsgGoodAdapter;
import com.app.bean.MsgGoodBean;
import com.app.listener.MyListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MsgGoodActivity extends Activity {

	private PullableListView mListView;
	private PullToRefreshLayout ptrl;
	private List<MsgGoodBean> mDataList;
	private MsgGoodAdapter mMsgGoodAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_good);
		initView();
		initListView();
	}
    
	public void initView(){
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_msg_good));
		ptrl.setOnRefreshListener(new MyListener());
	}
	
	public void initListView(){
		mListView = (PullableListView) findViewById(R.id.ltv_msg_good);
		mDataList = new ArrayList<MsgGoodBean>();
		MsgGoodBean mBeam = new MsgGoodBean(getResources().getDrawable(R.drawable.pic_msg_head1), 
				"迷路的安娜", 
				getResources().getDrawable(R.drawable.pic_female), 
				"12:43", 
				"我们被教导要记住思想，而不是人，因为人可能被杀死或遗忘，而思想不会", 
				getResources().getDrawable(R.drawable.pic_test1));
		mDataList.add(mBeam);
		mDataList.add(mBeam);
		mMsgGoodAdapter = new MsgGoodAdapter(this, mDataList);
		mListView.setAdapter(mMsgGoodAdapter);
	}
}
