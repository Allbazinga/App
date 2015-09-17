package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import com.app.adapter.MsgAdapter;
import com.app.bean.MsgBean;
import com.app.ui.MsgContactActivity;
import com.app.ui.MsgGoodActivity;
import com.app.ui.MsgReplyActivity;
import com.app.ui.R;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullToRefreshLayout.OnRefreshListener;
import com.app.view.baseview.PullableListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MsgFragment extends Fragment implements OnClickListener {

	private View v;
	private ListView ltv_msg;
	private MsgAdapter mMsgAdapter;
	private List<MsgBean> msgList;
    private RelativeLayout layout_msg_good, layout_msg_reply;
    

    
    private boolean isHidden = false;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			if(savedInstanceState.getBoolean("isMsgHidden")){
				getFragmentManager().beginTransaction().hide(this).commit();
			}
		}
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_msg, null);
		initView();
		return v;
	}

	public void initView(){
		ltv_msg = (ListView) v.findViewById(R.id.ltv_msg);
		addHeader();
	    msgList = new ArrayList<MsgBean>();
		for(int i = 0; i < 4; i++){
			MsgBean item = new MsgBean(getResources().getDrawable(R.drawable.pic_msg_head1)
					, "迷失的安娜"
					, getResources().getDrawable(R.drawable.pic_female)
					, "昨天20:34", "在茫茫人海中寻找你的身影");
			msgList.add(item);
		}
		mMsgAdapter = new MsgAdapter(getActivity(), msgList);
		ltv_msg.setAdapter(mMsgAdapter);
		ltv_msg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getActivity(), MsgContactActivity.class);
				startActivity(it);
			}
		});
	}

	public void addHeader() {

	   LayoutInflater
		 lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	   View
		 headerView = lif.inflate(R.layout.headerview_msg, null);
       ltv_msg.addHeaderView(headerView, null, true);
       layout_msg_good = (RelativeLayout) headerView.findViewById(R.id.layout_msg_good);
       layout_msg_reply = (RelativeLayout) headerView.findViewById(R.id.layout_msg_reply);
       layout_msg_good.setOnClickListener(this);
       layout_msg_reply.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.layout_msg_good:
			intent.setClass(getActivity(), MsgGoodActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_msg_reply:
			intent.setClass(getActivity(), MsgReplyActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putBoolean("isMsgHidden", isHidden);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		isHidden = false;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isHidden = true;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isHidden = true;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isHidden = true;
	}


}
