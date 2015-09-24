package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.HomeNearAdapter;
import com.app.bean.HomeBean;
import com.app.listener.MyListener;
import com.app.ui.HomeLtvDetailsActivity;
import com.app.ui.R;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class HomeNearFragment extends Fragment {

	private View v;
	private PullableListView mListView;
	private PullToRefreshLayout ptrl;
	private List<HomeBean> mDataList;
	private HomeNearAdapter mHomeNearAdapter;
	
	private boolean isHidden = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			if(savedInstanceState.getBoolean("isNearHidden")){
				getFragmentManager().beginTransaction().hide(this).commit();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_home_near, null);
		initView();
		initListView();
		return v;
	}
 
	
	public void initView(){
		ptrl = ((PullToRefreshLayout) v.findViewById(R.id.refresh_home_near));
		ptrl.setOnRefreshListener(new MyListener());
	}
	public void initListView(){
		mListView = (PullableListView) v.findViewById(R.id.ltv_home_near);
		mDataList = new ArrayList<HomeBean>();
		HomeBean item1 = new HomeBean("",
				"",
				"",
				
				"一瓶可乐",
				"",
				"昨天10:34",
				"我们被教导要记住思想，而不是人，因为人可能被杀死或是被遗忘，而思想不会。",
		        null,
				"一些思考",
				"9", 
				"18");
		HomeBean item = new HomeBean("",
				"",
				"",
			    "迷路的安娜",
				"",
				"今天13:23",
				"我承认，我真的活过。",
			    "",
				"一些思考",
				"9", 
				"18");
		mDataList.add(item1);
		mDataList.add(item);
		mDataList.add(item1);
		mDataList.add(item);
		mHomeNearAdapter = new HomeNearAdapter(getActivity(), mDataList);
		mListView.setAdapter(mHomeNearAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), HomeLtvDetailsActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putBoolean("isNearHidden", isHidden);
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
