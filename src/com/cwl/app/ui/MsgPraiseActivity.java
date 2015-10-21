package com.cwl.app.ui;

import java.util.ArrayList;
import com.cwl.app.R;
import com.cwl.app.adapter.MsgGoodAdapter;
import com.cwl.app.bean.MsgGoodBean;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MsgPraiseActivity extends Activity {

	private ListView ltv;
	private RefreshLayout mSwipeRefresh;
	private ArrayList<MsgGoodBean> datalist;
	private MsgGoodAdapter adapter;
	private static final int REFRESH_FAILED = 0;
	private static final int REFRESH_SUCCESS = 1;

	@SuppressLint("HandlerLeak")
	private Handler praiseHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == REFRESH_FAILED) {
				mSwipeRefresh.setRefreshing(false);
				Toast.makeText(MsgPraiseActivity.this, "获取数据失败，请重试", Toast.LENGTH_SHORT).show();
			} else if (msg.what == REFRESH_SUCCESS) {
				datalist = (ArrayList<MsgGoodBean>) msg.obj;
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
		setContentView(R.layout.activity_msg_good);
		mSwipeRefresh = (RefreshLayout) this.findViewById(R.id.swipe_layout);
		mSwipeRefresh.setColorScheme(R.color.umeng_comm_lv_header_color1,
				R.color.umeng_comm_lv_header_color2,
				R.color.umeng_comm_lv_header_color3,
				R.color.umeng_comm_lv_header_color4);
		mSwipeRefresh.setOnRefreshListener(mSwipeRefreshListener);
		mSwipeRefresh.setOnLoadListener(mSwipeLoadListener);
		ltv = (ListView) this.findViewById(R.id.ltv);
		Intent intent = getIntent();
		datalist = (ArrayList<MsgGoodBean>) intent.getExtras()
				.get("praiselist");
		adapter = new MsgGoodAdapter(this);
		if (datalist != null) {
			adapter.bindData(datalist);
			ltv.setAdapter(adapter);
		} else {
			datalist = new ArrayList<MsgGoodBean>();
			refresh();
		}
		ltv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MsgPraiseActivity.this, CmtsReplyDetailActivity.class);
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
						.GetPraiseListApi(PreferenceUtils.getInstance(
								MsgPraiseActivity.this).getSettingUserId());
				if (msg.obj == null) {
					praiseHandler.sendEmptyMessage(REFRESH_FAILED);
				} else {
					msg.what = REFRESH_SUCCESS;
					praiseHandler.sendMessage(msg);
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
