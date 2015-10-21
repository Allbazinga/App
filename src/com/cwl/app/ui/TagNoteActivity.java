package com.cwl.app.ui;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.adapter.HomeAdapter;
import com.cwl.app.bean.HomeBean;
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
import android.widget.TextView;
import android.widget.Toast;

public class TagNoteActivity extends Activity {

	private TextView tv_title;
	private String postTag = "";
	private String lastTime = "";
	private int offset = 0;
	private RefreshLayout mSwipeRefresh = null;
	private ArrayList<HomeBean> datalist = null;
	private HomeAdapter adapter = null;
	private ListView ltv;
	
	private static final int REFRESH_DATA_SUCCESS = 0;
	private static final int REFRESH_DATA_FAILED = 1;
	private static final int LOAD_DATA_SUCCESS = 2;
	private static final int LOAD_DATA_FAILED = 3;
	
	@SuppressLint("HandlerLeak")
	private Handler tagNoteHandler = new Handler(){

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == REFRESH_DATA_SUCCESS){
				datalist = (ArrayList<HomeBean>) msg.obj;
				offset = datalist.size();
				if(datalist.size() > 0){
					lastTime = datalist.get(offset-1).getTime();
				}
				adapter.bindData(datalist);
				ltv.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				mSwipeRefresh.setRefreshing(false);
			}else if(msg.what == REFRESH_DATA_FAILED){
				Toast.makeText(TagNoteActivity.this, "获取数据错误", Toast.LENGTH_SHORT).show();
			}
			if(msg.what == LOAD_DATA_SUCCESS){
				datalist.addAll((ArrayList<HomeBean>)msg.obj);
				if(datalist.size() > 0){
					lastTime = datalist.get(datalist.size()-1).getTime();
				}
				adapter.bindData(datalist);
				ltv.setAdapter(adapter);
				ltv.setSelection(offset);
				adapter.notifyDataSetChanged();
				offset = datalist.size();
				mSwipeRefresh.setLoading(false);
			}
			
		}
	};
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_note);
		Intent intent = getIntent();
		postTag = intent.getStringExtra("postTag");
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText(postTag);
		ltv = (ListView) this.findViewById(R.id.ltv);
		datalist = new ArrayList<HomeBean>();
		adapter = new HomeAdapter(TagNoteActivity.this);
		mSwipeRefresh = (RefreshLayout) this.findViewById(R.id.swipe_layout);
		mSwipeRefresh.setColorScheme(R.color.umeng_comm_lv_header_color1,
				R.color.umeng_comm_lv_header_color2,
				R.color.umeng_comm_lv_header_color3,
				R.color.umeng_comm_lv_header_color4);
		mSwipeRefresh.setOnRefreshListener(mSwipeRefreshListener);
		mSwipeRefresh.setOnLoadListener(mSwipeLoadListener);
		ltv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TagNoteActivity.this, CmtsReplyDetailActivity.class);
				intent.putExtra("postId", datalist.get(position).getNoteId());
				startActivity(intent);
			}
		});
	}
	
	public void refresh(){
		if(!mSwipeRefresh.isRefreshing()){
			mSwipeRefresh.setRefreshing(true);
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = HttpClientApi.GetListByTagApi(PreferenceUtils
						.getInstance(TagNoteActivity.this)
						.getSettingUserId(), postTag, "");
				if(msg.obj == null){
					tagNoteHandler.sendEmptyMessage(REFRESH_DATA_FAILED);
				}else{
					msg.what = REFRESH_DATA_SUCCESS;
					tagNoteHandler.sendMessage(msg);
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
    
	public void back(View v){
		finish();
	}
	
	private OnLoadListener mSwipeLoadListener = new OnLoadListener() {

		@Override
		public void onLoad() {
			// TODO Auto-generated method stub
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.obj = HttpClientApi.GetListByTagApi(PreferenceUtils
							.getInstance(TagNoteActivity.this)
							.getSettingUserId(), postTag, lastTime);
					if(msg.obj == null){
						tagNoteHandler.sendEmptyMessage(LOAD_DATA_FAILED);
					}else{
						msg.what = LOAD_DATA_SUCCESS;
						tagNoteHandler.sendMessage(msg);
					}
				}
			}).start();
		}
	};
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		refresh();
		super.onResume();
	}
}
