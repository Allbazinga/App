package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.MineMarkAdapter;
import com.app.bean.MineMarkBean;
import com.app.ui.R;
import com.app.utils.MyListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MineMarkFragment extends Fragment{
	
	private View v;
	private List<MineMarkBean> datalist;
	private MineMarkAdapter adapter;
	private PullToRefreshLayout prlt;
	private PullableListView ltv_mine_mark;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_mine_mark, null);
		initView();
		initListView();
		return v;
	}
	public void initView(){
		prlt = (PullToRefreshLayout) v.findViewById(R.id.refresh_mine_mark);
		prlt.setOnRefreshListener(new MyListener());
	}
	
	public void initListView(){
	
		ltv_mine_mark = (PullableListView) v.findViewById(R.id.ltv_mine_mark);
		datalist = new ArrayList<MineMarkBean>();
		for (int i = 0; i < 3; i++) {
			MineMarkBean noteBean = new MineMarkBean(getResources().getDrawable(R.drawable.pic_head3),
					"消失的邮轮");
			datalist.add(noteBean);
		}
		adapter = new MineMarkAdapter(getActivity(), datalist);
		ltv_mine_mark.setAdapter(adapter);
	}
}
