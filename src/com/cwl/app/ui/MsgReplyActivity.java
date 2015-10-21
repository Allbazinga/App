package com.cwl.app.ui;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.adapter.MsgReplyAdapter;
import com.cwl.app.bean.MsgReplyBean;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.refresher.RefreshLayout;
import com.cwl.app.refresher.RefreshLayout.OnLoadListener;
import com.cwl.app.utils.PreferenceUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MsgReplyActivity extends Activity {

	private ListView ltv;
	private RefreshLayout mSwipeRefresh;
	private ArrayList<MsgReplyBean> datalist;
	private MsgReplyAdapter adapter;
	private static final int REFRESH_FAILED = 0;
	private static final int REFRESH_SUCCESS = 1;
	
	@SuppressLint("HandlerLeak")
	private Handler replyHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == REFRESH_FAILED) {
				mSwipeRefresh.setRefreshing(false);
				Toast.makeText(MsgReplyActivity.this, "获取数据失败，请重试", Toast.LENGTH_SHORT).show();
			} else if (msg.what == REFRESH_SUCCESS) {
				datalist = (ArrayList<MsgReplyBean>) msg.obj;
				adapter.bindData(datalist);
				ltv.setAdapter(adapter);
				mSwipeRefresh.setRefreshing(false);
			}
		}
	};
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_reply);
		mSwipeRefresh = (RefreshLayout) this.findViewById(R.id.swipe_layout);
		mSwipeRefresh.setColorScheme(R.color.umeng_comm_lv_header_color1,
				R.color.umeng_comm_lv_header_color2,
				R.color.umeng_comm_lv_header_color3,
				R.color.umeng_comm_lv_header_color4);
		mSwipeRefresh.setOnRefreshListener(mSwipeRefreshListener);
		mSwipeRefresh.setOnLoadListener(mSwipeLoadListener);
		ltv = (ListView) findViewById(R.id.ltv);
		adapter = new MsgReplyAdapter(this);
		Intent intent = getIntent();
		datalist = (ArrayList<MsgReplyBean>) intent.getExtras()
				.get("replylist");
		if (datalist != null) {
			adapter.bindData(datalist);
			ltv.setAdapter(adapter);
		} else {
			datalist = new ArrayList<MsgReplyBean>();
			refresh();
		}
		ltv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MsgReplyActivity.this, CmtsReplyDetailActivity.class);
				intent.putExtra("postId", datalist.get(position).getNote().getNoteId());
				startActivity(intent);
			}
		});
	}
	
	public void back(View v){
		finish();
	}
	
	public void refresh() {
		if (!mSwipeRefresh.isRefreshing()) {
			mSwipeRefresh.setRefreshing(true);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = HttpClientApi
						.GetReplyListApi(PreferenceUtils.getInstance(
								MsgReplyActivity.this).getSettingUserId());
				if (msg.obj == null) {
					replyHandler.sendEmptyMessage(REFRESH_FAILED);
				} else {
					msg.what = REFRESH_SUCCESS;
					replyHandler.sendMessage(msg);
				}
			}
		}).start();
	}
	
	private OnRefreshListener mSwipeRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			refresh();
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
