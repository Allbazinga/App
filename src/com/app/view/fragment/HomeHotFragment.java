package com.app.view.fragment;

import java.util.ArrayList;

import com.app.adapter.HomeHotAdapter;
import com.app.bean.ADInfo;
import com.app.bean.HomeBean;
import com.app.client.ClientApi;
import com.app.ui.GameDetailActivity;
import com.app.ui.HomeLtvDetailsActivity;
import com.app.ui.R;
import com.app.view.baseview.ImageCycleView;
import com.app.view.baseview.ImageCycleView.ImageCycleViewListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullToRefreshLayout.OnRefreshListener;
import com.app.view.baseview.PullableListView;
import com.app.view.baseview.PullableListView.OnLoadListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeHotFragment extends Fragment implements OnRefreshListener, OnLoadListener{

	private View v;
	private PullableListView mListView;
	private PullToRefreshLayout pullToRefreshManager;
	private PullToRefreshLayout pullToLoadManager;
	private ArrayList<HomeBean> dataList;
	private HomeHotAdapter mHomeHotAdapter;
	private boolean isHidden = false;
    private RelativeLayout rlt_loading;
    private LinearLayout llt_data;
	private TextView tv_loading = null;
	private ImageCycleView mAdView = null;
	private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
	private String[] imageUrls = {
			"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
			"http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
			"http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
			"http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
			"http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg" };
	private static final int INIT = 0;
	private static final int REFRESH = 1;
	private static final int LOAD = 2;
	private int offset = 5;
	private static final String REFRESH_URL = "http://202.118.76.72/App/action/PostListAction.php";
	
	private Handler getDataHandler = new Handler(){

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.obj == null){
				Toast.makeText(getActivity(), "网络异常，请检查设置！", Toast.LENGTH_SHORT).show();
				if(msg.what == INIT){
					tv_loading.setText("重新加载!");
					tv_loading.setClickable(true);
				}
				if(msg.what == REFRESH){
					pullToRefreshManager.refreshFinish(PullToRefreshLayout.FAIL);
				}
				if(msg.what == LOAD){
					pullToLoadManager.refreshFinish(PullToRefreshLayout.FAIL);
				}
			}else{
				if(msg.what == INIT){
					rlt_loading.setVisibility(View.GONE);
					llt_data.setVisibility(View.VISIBLE);
					dataList.clear();
					dataList = (ArrayList<HomeBean>) msg.obj;
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					mHomeHotAdapter.notifyDataSetChanged();
				}
				if(msg.what == REFRESH){
					dataList.clear();
					dataList =(ArrayList<HomeBean>) msg.obj;
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					pullToRefreshManager
					.refreshFinish(PullToRefreshLayout.SUCCEED);
					mHomeHotAdapter.notifyDataSetChanged();
				}
				if(msg.what == LOAD){
					dataList.addAll((ArrayList<HomeBean>) msg.obj);
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					mListView.setSelection(offset);
					mListView.finishLoading();
					mHomeHotAdapter.notifyDataSetChanged();
				}
			}
		}
	};
	
	
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
		initData();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), HomeLtvDetailsActivity.class);
				intent.putExtra("note", dataList.get(position - 1));
				intent.putExtra("postUser", dataList.get(position - 1).getUserId());
				startActivity(intent);
			}
		});
		mListView.setOnLoadListener(this);
		return v;
	}

	public void initView() {
		dataList = new ArrayList<HomeBean>();
		mListView = (PullableListView) v.findViewById(R.id.ltv_home_hot);
		addHeader();
		mHomeHotAdapter = new HomeHotAdapter(getActivity());
	    ((PullToRefreshLayout) v.findViewById(R.id.refresh_home_hot)).setOnRefreshListener(this);;;
		rlt_loading = (RelativeLayout) v.findViewById(R.id.rlt_loading);
		llt_data = (LinearLayout) v.findViewById(R.id.llt_data);
		tv_loading = (TextView) v.findViewById(R.id.tv_loading);
		tv_loading.setText(R.string.loading4);
		tv_loading.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_loading.setText(R.string.loading4);
				initData();
				
			}
		});
		tv_loading.setClickable(false);
	}

	public void initData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.obj = ClientApi.getHomeData(REFRESH_URL, null);
				msg.what = INIT;
				getDataHandler.sendMessage(msg);
			}
		}).start();
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

	@Override
	public void onResume() {
		super.onResume();
		if(mAdView != null){
			mAdView.startImageCycle();
		}
	};
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		// scheduledExecutorService.shutdown();
		super.onStop();
		isHidden = true;
		if(mAdView != null){
			mAdView.pushImageCycle();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isHidden = true;
		if(mAdView != null){
			mAdView.pushImageCycle();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isHidden = true;
		if(mAdView != null){
			mAdView.pushImageCycle();
		}
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		pullToRefreshManager = pullToRefreshLayout;
         new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.obj = ClientApi.getHomeData(REFRESH_URL, null);
				msg.what = REFRESH;
				getDataHandler.sendMessage(msg);
			}
		}).start();
	}

	@Override
	public void onLoad(PullableListView pullableListView) {
		// TODO Auto-generated method stub
          new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.obj = ClientApi.getHomeData(REFRESH_URL, null);
				msg.what = LOAD;
				getDataHandler.sendMessage(msg);
			}
		}).start();
	}

	/*@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		pullToRefreshManager = pullToRefreshLayout;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.obj = ClientApi.getHomeData(REFRESH_URL, null);
				msg.what = REFRESH;
				getDataHandler.sendMessage(msg);
			}
		}).start();
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		pullToLoadManager = pullToRefreshLayout;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.obj = ClientApi.getHomeData(REFRESH_URL, null);
				msg.what = LOAD;
				getDataHandler.sendMessage(msg);
			}
		}).start();
	}
	 */
	
}
