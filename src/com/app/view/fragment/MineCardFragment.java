package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.MineCardAdapter;
import com.app.bean.MineCardBean;
import com.app.ui.R;
import com.app.utils.MyListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MineCardFragment extends Fragment {

	private View v;
	private List<MineCardBean> datalist;
	private MineCardAdapter adapter;
	private PullToRefreshLayout prlt;
	private PullableListView ltv_mine_card;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_mine_card, null);
		initView();
		initListView();
		return v;
	}
	public void initView(){
		prlt = (PullToRefreshLayout) v.findViewById(R.id.refresh_mine_card);
		prlt.setOnRefreshListener(new MyListener());
	}
	
	public void initListView(){
	
		ltv_mine_card = (PullableListView) v.findViewById(R.id.ltv_mine_card);
		datalist = new ArrayList<MineCardBean>();
		for (int i = 0; i < 2; i++) {
			MineCardBean cardBean = new MineCardBean(getResources().getDrawable(R.drawable.pic_test1),
					"在大黑山回來的路上", 
					"我发誓，我真的活过", 
					"16",
					"33");
			datalist.add(cardBean);
		}
		adapter = new MineCardAdapter(getActivity(), datalist);
		ltv_mine_card.setAdapter(adapter);
	}

}
