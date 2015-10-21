package com.cwl.app.ui;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.adapter.MineNoteAdapter;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.bean.User;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.refresher.RefreshLayout;
import com.cwl.app.refresher.RefreshLayout.OnLoadListener;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;
import com.cwl.app.utils.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends Activity implements OnClickListener {

	private ListView ltv_other_info;
	private TextView tv_title;
	private MineNoteAdapter adapter = null;
	private ArrayList<HomeBean> datalist = null;
	private RefreshLayout mSwipeRefresh;
	private ImageView iv_avatar, iv_sex;
	private TextView tv_name, tv_scl, tv_sign, tv_num_note;
	private Button btn_yo, btn_concern;
	private String userId = "";
	private String lastTime = "";
	private LruCache<String, Bitmap> lruCache;
	private User user = null;
	private boolean ifIsTempUser = false;
	private boolean hasConcerned = false;
	private int offSet = 0;
	private static final int REFRESH_NOTES_FAILED = 0;
	private static final int REFRESH_NOTES_SUCCESS = 1;
	private static final int LOAD_NOTES_FAILED = 2;
	private static final int LOAD_NOTES_SUCCESS = 3;
	private static final int REFRESH_HEAD_FAILEED = 4;
	private static final int REFRESH_HEAD_SUCCESS = 5;
	private static final int CONCERN_SUCCESS = 6;
	private static final int CONCERN_FAILED = 7;
	private static final int UN_CONCERN_SUCCESS = 8;
	private static final int UN_CONCERN_FAILED = 9;
	
	@SuppressLint("HandlerLeak")
	private Handler userInfoHandler = new Handler() {

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
				ltv_other_info.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else if (msg.what == REFRESH_NOTES_FAILED) {
				mSwipeRefresh.setRefreshing(false);
				Toast.makeText(UserInfoActivity.this, "获取数据失败，请重试",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == LOAD_NOTES_SUCCESS) {
				mSwipeRefresh.setLoading(false);
				datalist.addAll((ArrayList<HomeBean>)msg.obj);
				adapter.bindData(datalist);
				ltv_other_info.setAdapter(adapter);
				ltv_other_info.setSelection(offSet);
				offSet = datalist.size();
				if(offSet>0)
					lastTime = datalist.get(offSet-1).getTime();
				adapter.notifyDataSetChanged();
			} else if (msg.what == LOAD_NOTES_FAILED) {
				mSwipeRefresh.setLoading(false);
				Toast.makeText(UserInfoActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
			} else if (msg.what == REFRESH_HEAD_SUCCESS) {
				user = (User) msg.obj;
				refreshHeader(user);
				refreshNotes();
			} else if (msg.what == REFRESH_HEAD_FAILEED) {
				Toast.makeText(UserInfoActivity.this, "获取数据失败，请重试",
						Toast.LENGTH_SHORT).show();
				btn_yo.setVisibility(View.GONE);
				btn_concern.setVisibility(View.GONE);
				if (mSwipeRefresh.isRefreshing())
				mSwipeRefresh.setRefreshing(false);
			}else if(msg.what == CONCERN_SUCCESS){
				hasConcerned = !hasConcerned;
				btn_concern.setText("取消关注");
			}else if(msg.what == UN_CONCERN_SUCCESS){
				hasConcerned = !hasConcerned;
				btn_concern.setText("+关注");
			}else if(msg.what == CONCERN_FAILED){
				
			}else if(msg.what == UN_CONCERN_FAILED){
				
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		lruCache = ImageCache.GetLruCache(this);
		Intent intent = getIntent();
		userId = intent.getStringExtra("userId");
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		ifIsTempUser = PreferenceUtils.getInstance(this).getSettingUserId()
				.equals(userId);
		if (ifIsTempUser) {
			tv_title.setText("我的资料");
		} else {
			tv_title.setText("TA的资料");
		}
		initView();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	public void initView() {
		mSwipeRefresh = (RefreshLayout) this.findViewById(R.id.swipe_layout);
		// 设置下拉刷新时的颜色值,颜色值需要定义在xml中
		mSwipeRefresh.setColorScheme(R.color.umeng_comm_lv_header_color1,
				R.color.umeng_comm_lv_header_color2,
				R.color.umeng_comm_lv_header_color3,
				R.color.umeng_comm_lv_header_color4);
		mSwipeRefresh.setOnRefreshListener(mSwipeRefreshListener);
		mSwipeRefresh.setOnLoadListener(mSwipeLoadListener);
		ltv_other_info = (ListView) findViewById(R.id.ltv_other_info);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View headerview = inflater
				.inflate(R.layout.headerview_other_info, null);
		((TextView) headerview.findViewById(R.id.tv_others_gain))
				.setOnClickListener(this);
		((TextView) headerview.findViewById(R.id.tv_others_info))
				.setOnClickListener(this);
		iv_avatar = (ImageView) headerview.findViewById(R.id.iv_avatar);
		tv_name = (TextView) headerview.findViewById(R.id.tv_other_name);
		iv_sex = (ImageView) headerview.findViewById(R.id.iv_other_sex);
		tv_scl = (TextView) headerview.findViewById(R.id.tv_other_scl);
		tv_sign = (TextView) headerview.findViewById(R.id.tv_other_sign);
		tv_num_note = (TextView) headerview.findViewById(R.id.tv_num_note);
		btn_yo = (Button) headerview.findViewById(R.id.btn_yo);
		btn_concern = (Button) headerview.findViewById(R.id.btn_concern);
		if (ifIsTempUser) {
			btn_yo.setVisibility(View.GONE);
			btn_concern.setVisibility(View.GONE);
		}
		getHeaderDatas();
		ltv_other_info.addHeaderView(headerview, null, false);
		datalist = new ArrayList<HomeBean>();
		adapter = new MineNoteAdapter(this);
		adapter.bindData(datalist);
		// ltv_other_info.setAdapter(adapter);
		ltv_other_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserInfoActivity.this, CmtsReplyDetailActivity.class);
				intent.putExtra("postId", datalist.get(position-1).getNoteId());
				startActivity(intent);
			}
		});
	}

	public void back(View v) {
		finish();
	}

	public void concern() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(hasConcerned){
					boolean result = HttpClientApi.CancelConcernApi(PreferenceUtils
							.getInstance(UserInfoActivity.this)
							.getSettingUserId(), userId);
					if(result){
						userInfoHandler.sendEmptyMessage(UN_CONCERN_SUCCESS);
						return;
					}
					userInfoHandler.sendEmptyMessage(UN_CONCERN_FAILED);
				}else{
					boolean result = HttpClientApi.SetConcernApi(PreferenceUtils
							.getInstance(UserInfoActivity.this)
							.getSettingUserId(), userId);
					if(result){
						userInfoHandler.sendEmptyMessage(CONCERN_SUCCESS);
						return;
					}
					userInfoHandler.sendEmptyMessage(CONCERN_FAILED);
				}
				
			}
		}).start();
	}

	public void refreshHeader(User user) {
		iv_avatar.setImageResource(R.drawable.default_avatar);
		if (!user.getHeaderurl().equals("")) {
			iv_avatar.setTag(Constants.IP + user.getHeaderurl());
			new ImageCache(this, lruCache, iv_avatar, Constants.IP
					+ user.getHeaderurl(), "APP", 120, 120);
		}
		if (user.getSex().equals("女")) {
			iv_sex.setImageResource(R.drawable.pic_female);
		} else {
			iv_sex.setImageResource(R.drawable.pic_male);
		}
		tv_name.setText(user.getName());
		if (user.getScl().equals("") && !user.getGrade().equals("")) {
			tv_scl.setText(user.getScl() + user.getGrade() + "级");
		} else if (!user.getScl().equals("")) {
			tv_scl.setText(user.getScl() + "  " + user.getGrade());
		}
		tv_sign.setText("“" + user.getSign() + "”");
		hasConcerned = Boolean.parseBoolean(user.getHasConcerned());
		if(hasConcerned){
			btn_concern.setText("取消关注");
		}else{
			btn_concern.setText("+关注");
		}
		btn_concern.setOnClickListener(this);
		btn_yo.setOnClickListener(this);
	}

	public void getHeaderDatas() {
		if (!mSwipeRefresh.isRefreshing()) {
			mSwipeRefresh.setRefreshing(true);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = HttpClientApi.GetUserInfoApi(userId, PreferenceUtils
						.getInstance(UserInfoActivity.this).getSettingUserId());
				if (msg.obj != null) {
					msg.what = REFRESH_HEAD_SUCCESS;
					userInfoHandler.sendMessage(msg);
				} else {
					userInfoHandler.sendEmptyMessage(REFRESH_HEAD_FAILEED);
				}
			}
		}).start();
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
				msg.obj = HttpClientApi.UserNotesListApi(userId,
						PreferenceUtils.getInstance(UserInfoActivity.this)
								.getSettingUserId(), "");
				if (msg.obj != null) {
					msg.what = REFRESH_NOTES_SUCCESS;
					userInfoHandler.sendMessage(msg);
				} else {
					userInfoHandler.sendEmptyMessage(REFRESH_NOTES_FAILED);
				}
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.tv_others_gain:
			Toast.makeText(UserInfoActivity.this, "暂未开放", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.tv_others_info:
			if(PreferenceUtils.getInstance(UserInfoActivity.this).getSettingUserId().equals(userId)){
				intent.setClass(UserInfoActivity.this, SetMyInfoActivity.class);
				startActivity(intent);
			}else{
				intent.setClass(this, OthersInfoDetailActivity.class);
		    	intent.putExtra("id", userId);
	    		intent.putExtra("head", user.getHeaderurl());
			    intent.putExtra("name", user.getName());
		    	intent.putExtra("grade", user.getGrade());
		    	intent.putExtra("home", user.getHome());
		    	intent.putExtra("sex", user.getSex());
	    		intent.putExtra("major", user.getMajor());
	    		startActivity(intent);
			}
			break;
		case R.id.btn_yo:
			intent.setClass(this, ChatActivity.class);
			intent.putExtra("userId", userId);
			intent.putExtra("userName", user.getName());
			intent.putExtra("headerUrl", user.getHeaderurl());
			startActivity(intent);
			break;
		case R.id.btn_concern:
			concern();
			break;
		default:
			break;
		}
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
					msg.obj = HttpClientApi.UserNotesListApi(userId, PreferenceUtils
							.getInstance(UserInfoActivity.this)
							.getSettingUserId(), lastTime);
					if(msg.obj != null){
						msg.what = LOAD_NOTES_SUCCESS;
						userInfoHandler.sendMessage(msg);
						return;
					}
					userInfoHandler.sendEmptyMessage(LOAD_NOTES_FAILED);
				}
			}).start();
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
