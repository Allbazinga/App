package com.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.MsgReplyAdapter;
import com.app.bean.MsgReplyBean;
import com.app.listener.MyListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MsgReplyActivity extends Activity {

	private PullableListView mListView;
	private PullToRefreshLayout ptrl;
	private List<MsgReplyBean> mDataList;
	private MsgReplyAdapter mMsgReplyAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_reply);
		initView();
		initListView();
	}
	
	public void initView(){
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_msg_reply));
		ptrl.setOnRefreshListener(new MyListener());
	}
	
	public void initListView(){
		mListView = (PullableListView) findViewById(R.id.ltv_msg_reply);
		mDataList = new ArrayList<MsgReplyBean>();
		MsgReplyBean mBeam = new MsgReplyBean(getResources().getDrawable(R.drawable.pic_msg_head1), 
				"迷路的安娜", 
				getResources().getDrawable(R.drawable.pic_female), 
				"12:43", 
				"凑你妹啊，这又不是贴吧，就算凑够了十五字也不会有经验的", 
				"我们被教导要记住思想，而不是人，因为人可能被杀死或遗忘，而思想不会");
		mDataList.add(mBeam);
		mDataList.add(mBeam);
		mMsgReplyAdapter = new MsgReplyAdapter(this, mDataList);
		mListView.setAdapter(mMsgReplyAdapter);
	}
	
}
