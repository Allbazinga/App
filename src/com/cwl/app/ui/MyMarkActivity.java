package com.cwl.app.ui;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.adapter.MineMarkAdapter;
import com.cwl.app.bean.MineMarkBean;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.refresher.RefreshLayout;
import com.cwl.app.refresher.RefreshLayout.OnLoadListener;
import com.cwl.app.utils.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyMarkActivity extends Activity {

	private ListView ltv_my;
	private MineMarkAdapter adapter = null;
	private ArrayList<MineMarkBean> datalist = null;
	private RefreshLayout mSwipeRefresh = null;
	private static final int REFRESH_FAILED = 0;
	private static final int REFRESH_SUCCESS = 1;
	
	@SuppressLint("HandlerLeak")
	private Handler concernHandler = new Handler(){

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == REFRESH_SUCCESS){
				mSwipeRefresh.setRefreshing(false);
				datalist = (ArrayList<MineMarkBean>)msg.obj;
				adapter.bindData(datalist);
				ltv_my.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}else if(msg.what == REFRESH_FAILED){
				Toast.makeText(MyMarkActivity.this, "获取数据失败，请重试", Toast.LENGTH_SHORT).show();;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		initView();
	}

	@SuppressWarnings("deprecation")
	public void initView() {
		mSwipeRefresh = (RefreshLayout) this.findViewById(R.id.swipe_layout);
		mSwipeRefresh.setColorScheme(R.color.umeng_comm_lv_header_color1,
				R.color.umeng_comm_lv_header_color2,
				R.color.umeng_comm_lv_header_color3,
				R.color.umeng_comm_lv_header_color4);
		mSwipeRefresh.setOnRefreshListener(mSwipeRefreshListener);
		mSwipeRefresh.setOnLoadListener(mSwipeLoadListener);
		((TextView) this.findViewById(R.id.tv_actionbar))
				.setText(R.string.mark);
		;
		ltv_my = (ListView) findViewById(R.id.ltv_my);
		datalist = new ArrayList<MineMarkBean>();
		adapter = new MineMarkAdapter(this);
		refreshConcern();
	}

	public void back(View v) {
		this.finish();
	}
	
	public MyMarkActivity getInstance(Context context){
		return MyMarkActivity.this;
	}
	public void refreshConcern() {
		if (!mSwipeRefresh.isRefreshing()) {
			mSwipeRefresh.setRefreshing(true);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = HttpClientApi.GetConcerndListApi(PreferenceUtils.getInstance(
						MyMarkActivity.this).getSettingUserId());
				if(msg.obj != null){
					msg.what = REFRESH_SUCCESS;
					concernHandler.sendMessage(msg);
				}else{
					concernHandler.sendEmptyMessage(REFRESH_FAILED);
				}
			}
		}).start();
	}

	private OnRefreshListener mSwipeRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			refreshConcern();
		}
	};

	private OnLoadListener mSwipeLoadListener = new OnLoadListener() {

		@Override
		public void onLoad() {
			// TODO Auto-generated method stub
			mSwipeRefresh.setLoading(false);
		}
	};

}
