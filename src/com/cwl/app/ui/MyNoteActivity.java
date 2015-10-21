package com.cwl.app.ui;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.adapter.MineNoteAdapter;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.refresher.RefreshLayout;
import com.cwl.app.refresher.RefreshLayout.OnLoadListener;
import com.cwl.app.utils.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyNoteActivity extends Activity {

	private ListView ltv_my;
	private MineNoteAdapter adapter = null;
	private ArrayList<HomeBean> datalist = null;
	private RefreshLayout mSwipeRefresh = null;
	private TextView tv_num_note;
	private String userId = "";
	private int offSet = 0;
	private String lastTime = "";
	private static final int REFRESH_NOTES_FAILED = 0;
	private static final int REFRESH_NOTES_SUCCESS = 1;
	private static final int LOAD_NOTES_FAILED = 2;
	private static final int LOAD_NOTES_SUCCESS = 3;
	@SuppressLint("HandlerLeak")
	private Handler myNoteHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == REFRESH_NOTES_SUCCESS) {
				mSwipeRefresh.setRefreshing(false);
				datalist.clear();
				datalist = (ArrayList<HomeBean>) msg.obj;
				offSet = datalist.size();
				if (offSet > 0) {
					lastTime = datalist.get(offSet - 1).getTime();
					tv_num_note.setText(datalist.get(0).getNoteNum() + "篇帖子");
				}
				adapter.bindData(datalist);
				ltv_my.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else if (msg.what == REFRESH_NOTES_FAILED) {
				mSwipeRefresh.setRefreshing(false);
				Toast.makeText(MyNoteActivity.this, "获取数据失败，请重试",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == LOAD_NOTES_SUCCESS) {
				mSwipeRefresh.setLoading(false);
				datalist.addAll((ArrayList<HomeBean>)msg.obj);
				adapter.bindData(datalist);
				ltv_my.setAdapter(adapter);
				ltv_my.setSelection(offSet);
				offSet = datalist.size();
				if(offSet>0)
					lastTime = datalist.get(offSet-1).getTime();
				adapter.notifyDataSetChanged();
			} else if (msg.what == LOAD_NOTES_FAILED) {
				mSwipeRefresh.setLoading(false);
				Toast.makeText(MyNoteActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		userId = PreferenceUtils.getInstance(this).getSettingUserId();
		initView();
	}

	@SuppressLint("InflateParams")
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
				.setText(R.string.note);
		ltv_my = (ListView) findViewById(R.id.ltv_my);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View headerview = inflater.inflate(R.layout.headerview_my_note, null);
		tv_num_note = (TextView) headerview.findViewById(R.id.tv_num_note);
		ltv_my.addHeaderView(headerview, null, true);
		datalist = new ArrayList<HomeBean>();
		adapter = new MineNoteAdapter(this);
		refreshNotes();
		ltv_my.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyNoteActivity.this, CmtsReplyDetailActivity.class);
				intent.putExtra("postId", datalist.get(position-1).getNoteId());
				startActivity(intent);
			}
		});
	}

	public void refreshNotes() {
		if (!mSwipeRefresh.isRefreshing()) {
			mSwipeRefresh.setRefreshing(true);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = HttpClientApi.UserNotesListApi(userId, userId, "");
				if (msg.obj != null) {
					msg.what = REFRESH_NOTES_SUCCESS;
					myNoteHandler.sendMessage(msg);
				} else {
					myNoteHandler.sendEmptyMessage(REFRESH_NOTES_FAILED);
				}
			}
		}).start();
	}

	private OnRefreshListener mSwipeRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			refreshNotes();
		}
	};

	private OnLoadListener mSwipeLoadListener = new OnLoadListener() {

		@Override
		public void onLoad() {
			// TODO Auto-generated method stub
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.obj = HttpClientApi.UserNotesListApi(userId, 
							userId, lastTime);
					if(msg.obj != null){
						msg.what = LOAD_NOTES_SUCCESS;
						myNoteHandler.sendMessage(msg);
						return;
					}
					myNoteHandler.sendEmptyMessage(LOAD_NOTES_FAILED);
				}
			}).start();
		}
	};

	public void back(View v){
		this.finish();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
