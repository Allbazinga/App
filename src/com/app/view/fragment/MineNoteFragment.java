package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.MineNoteAdapter;
import com.app.bean.MineNoteBean;
import com.app.ui.R;
import com.app.utils.MyListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MineNoteFragment extends Fragment {

	private View v;
	private List<MineNoteBean> datalist;
	private MineNoteAdapter adapter;
	private PullToRefreshLayout prlt;
	private PullableListView ltv_mine_note;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_mine_note, null);
		initView();
		initListView();
		return v;
	}
	public void initView(){
		prlt = (PullToRefreshLayout) v.findViewById(R.id.refresh_mine_note);
		prlt.setOnRefreshListener(new MyListener());
	}
	
	public void initListView(){
	
		ltv_mine_note = (PullableListView) v.findViewById(R.id.ltv_mine_note);
		datalist = new ArrayList<MineNoteBean>();
		for (int i = 0; i < 4; i++) {
			MineNoteBean noteBean = new MineNoteBean(getResources().getDrawable(R.drawable.pic_test1),
					"我发誓，我真的活过", 
					"2015-09-15 21:25");
			datalist.add(noteBean);
		}
		adapter = new MineNoteAdapter(getActivity(), datalist);
		ltv_mine_note.setAdapter(adapter);
	}
}