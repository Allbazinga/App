package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.MineCardAdapter;
import com.app.adapter.MineCollectAdapter;
import com.app.bean.MineCardBean;
import com.app.bean.MineCollectBean;
import com.app.ui.R;
import com.app.utils.MyListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MineCollectFragment extends Fragment {
	
	private View v;
	private List<MineCollectBean> datalist;
	private MineCollectAdapter adapter;
	private PullToRefreshLayout prlt;
	private PullableListView ltv_mine_collect;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_mine_collect, null);
		initView();
		initListView();
		return v;
	}
	public void initView(){
		prlt = (PullToRefreshLayout) v.findViewById(R.id.refresh_mine_collect);
		prlt.setOnRefreshListener(new MyListener());
	}
	
	public void initListView(){
	
		ltv_mine_collect = (PullableListView) v.findViewById(R.id.ltv_mine_collect);
		datalist = new ArrayList<MineCollectBean>();
		for (int i = 0; i < 2; i++) {
			MineCollectBean collectBean = new MineCollectBean(getResources().getDrawable(R.drawable.pic_head3),
					"一瓶可乐",
					getResources().getDrawable(R.drawable.pic_male),
					"2015-09-14 21:21",
					"昨天，下了一场大雨，很大很大的雨",
					getResources().getDrawable(R.drawable.pic_test1));
			datalist.add(collectBean);
		}
		adapter = new MineCollectAdapter(getActivity(), datalist);
		ltv_mine_collect.setAdapter(adapter);
	}

}