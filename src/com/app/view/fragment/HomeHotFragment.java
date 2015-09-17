package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.app.adapter.HomeHotAdapter;
import com.app.bean.ADInfo;
import com.app.bean.HomeBean;
import com.app.ui.GameDetailActivity;
import com.app.ui.HomeLtvDetailsActivity;
import com.app.ui.R;
import com.app.utils.MyListener;
import com.app.view.baseview.ImageCycleView;
import com.app.view.baseview.ImageCycleView.ImageCycleViewListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullableListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeHotFragment extends Fragment {

	private View v;
	private PullableListView mListView;
	private PullToRefreshLayout ptrl;
	private List<HomeBean> mDataList;
	private HomeHotAdapter mHomeHotAdapter;
	private boolean isHidden = false;

	private ImageCycleView mAdView;


	private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();

	private String[] imageUrls = {
			"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
			"http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
			"http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
			"http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
			"http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("isHotHidden")) {
				getFragmentManager().beginTransaction().hide(this).commit();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_home_hot, null);
		initView();
		initListView();
		return v;
	}

	public void initView() {

		ptrl = ((PullToRefreshLayout) v.findViewById(R.id.refresh_home_hot));
		ptrl.setOnRefreshListener(new MyListener());
	}

	public void initListView() {

		mListView = (PullableListView) v.findViewById(R.id.ltv_home_hot);
		mDataList = new ArrayList<HomeBean>();
		addHeader();
		HomeBean item1 = new HomeBean(getResources().getDrawable(
				R.drawable.pic_msg_head1), "一瓶可乐", getResources().getDrawable(
				R.drawable.pic_male), "昨天10:34",
				"我们被教导要记住思想，而不是人，因为人可能被杀死或是被遗忘，而思想不会。", null, "一些思考", "9", "18");
		HomeBean item = new HomeBean(getResources().getDrawable(
				R.drawable.pic_head2), "迷路的安娜", getResources().getDrawable(
				R.drawable.pic_female), "今天13:23", "我承认，我真的活过。", getResources()
				.getDrawable(R.drawable.pic_test1), "一些思考", "9", "18");
		mDataList.add(item1);
		mDataList.add(item);
		mDataList.add(item1);
		mDataList.add(item);
		mHomeHotAdapter = new HomeHotAdapter(getActivity(), mDataList);
		mListView.setAdapter(mHomeHotAdapter);
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

	public void addHeader() {
		LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		View headerView = lif.inflate(R.layout.headerview_home_hot, null);

		for(int i=0;i < imageUrls.length; i ++){
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
			info.setContent("top-->" + i);
			infos.add(info);
		}
		mAdView = (ImageCycleView) headerView.findViewById(R.id.ad_view);
		mAdView.setImageResources(infos, mAdCycleViewListener);
		
		mListView.addHeaderView(headerView, null, true);
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			Toast.makeText(getActivity(), "content->"+info.getContent(), Toast.LENGTH_SHORT).show();
			Intent it = new Intent();
			it.setClass(getActivity(), GameDetailActivity.class);
			startActivity(it);
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);// 使用ImageLoader对图片进行加装！
		}
	};
	
	
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

	/*
	 * @Override public void onStart() { // TODO Auto-generated method stub
	 * scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	 * // 当Activity显示出来后，每两秒钟切换一次图片显示
	 * scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
	 * TimeUnit.SECONDS); super.onStart();
	 * 
	 * }
	 */
	
	@Override
	public void onResume() {
		super.onResume();
		mAdView.startImageCycle();
	};
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		// scheduledExecutorService.shutdown();
		super.onStop();
		isHidden = true;
		mAdView.pushImageCycle();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isHidden = true;
		mAdView.pushImageCycle();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isHidden = true;
		mAdView.pushImageCycle();
	}

}
