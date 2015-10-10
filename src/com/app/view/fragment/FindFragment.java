package com.app.view.fragment;

import java.util.ArrayList;

import com.app.adapter.FindLabelAdapter;
import com.app.bean.FindLabelBean;
import com.app.ui.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class FindFragment extends Fragment implements OnClickListener {

	private View v;
	private boolean isHidden = false;
	private ListView ltv_find;
	private ArrayList<FindLabelBean> datalist = null;
	private FindLabelAdapter adapter = null;
	private FindLabelBean[] beans = new FindLabelBean[8];
	private String[] labels = { "河神办事处", "文青栖息地", "歌单&影单", "大工树洞", "淘趣卖场",
			"小羞羞的事", "表白墙", "意见与反馈",

	};
	private String[] intruduces = { "年轻的同学喔，你掉的是这张金学生卡还是...",
			"生活除了眼前的苟且，还有诗与远方。", "好东西要跟大家一起分享！", "有什么秘密就在这里说出来吧！",
			"买了床头柜，寝室四个学姐一起打包送你了", "这里收集了所有耻度较高的话题~", "我需要你知道，有人在爱着你。",
			"用户的吐槽是我们赖以生存的粮食。", };
	private int[] ivs = { R.drawable.pic_test_xuanchuan,
			R.drawable.pic_test_xuanchuan, R.drawable.pic_test_xuanchuan,
			R.drawable.pic_test_xuanchuan, R.drawable.pic_test_xuanchuan,
			R.drawable.pic_test_xuanchuan, R.drawable.pic_test_xuanchuan,
			R.drawable.pic_test_xuanchuan, };
	private int[] isMarkeds = { 1, 0, 1, 1, 0, 0, 1, 1, };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("isFindHidden")) {
				getFragmentManager().beginTransaction().hide(this).commit();
			}
		}
	}

	@SuppressLint("InlinedApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_find, null);
		initView(v);
		return v;
	}

	public void initView(View v) {
		ltv_find = (ListView) v.findViewById(R.id.ltv_find);
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View header = inflater.inflate(R.layout.item_find_header, null);
		View footer = inflater.inflate(R.layout.footer_find, null);
		ltv_find.addHeaderView(header, null, true);
		ltv_find.addFooterView(footer);
		datalist = new ArrayList<FindLabelBean>();
		int i = 0;
		for (FindLabelBean bean : beans) {
			bean = new FindLabelBean(ivs[i], labels[i], intruduces[i],
					isMarkeds[i]);
			datalist.add(bean);
			i++;
		}
		adapter = new FindLabelAdapter(getActivity(), datalist);
		ltv_find.setAdapter(adapter);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putBoolean("isFindHidden", isHidden);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

}
