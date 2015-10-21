package com.cwl.app.ui;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.adapter.ReplyDetailAdapter;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.bean.ReplyDetailBean;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CmtsReplyDetailActivity extends Activity implements
		OnClickListener {

	private ListView mListView;
	private ArrayList<ReplyDetailBean> datalist;
	private ReplyDetailAdapter adapter;
	private String postId = "";
	private LruCache<String, Bitmap> lruCache;
	private static final int PRAISE_SUCCESS = 0;
	private static final int PRAISE_FAILED = 1;
	private static final int COMMENT_SUCCESS = 2;
	private static final int COMMENT_FAILED = 3;
	private static final int GET_REPLY_SUCCESS_INIT = 4;
	private static final int GET_REPLY_FAILED = 5;
	// private boolean hasPraised = false;
	private InputMethodManager imm;
	private View v;
	private ImageView iv_praise;
	private TextView tv_praise;
	private EditText edt_reply;
	private TextView tv_cmt;
	private ImageView avatar, cntImg;
	private TextView name;
	private ImageView sex;
	private TextView time;
	private TextView cnt;
	private RelativeLayout rlt_tag;
	private TextView tag;
	private Button btn_reply;
	private int cmtNum = 0;
	private HomeBean note = null;
	private RefreshLayout mSwipeRefresh = null;

	@SuppressLint("HandlerLeak")
	private Handler detailsHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case PRAISE_SUCCESS:
				iv_praise.setImageResource(R.drawable.pic_good_sel);
				tv_praise.setText(String.valueOf(cmtNum + 1));
				iv_praise.setClickable(false);
				break;
			case PRAISE_FAILED:
				iv_praise.setClickable(true);
				Toast.makeText(CmtsReplyDetailActivity.this, "点赞失败",
						Toast.LENGTH_SHORT).show();
			case COMMENT_SUCCESS:
				edt_reply.setText("");
				btn_reply.setClickable(true);
				refresh();
				break;
			case COMMENT_FAILED:
				Toast.makeText(CmtsReplyDetailActivity.this, "评论失败，请重试",
						Toast.LENGTH_SHORT).show();
				btn_reply.setClickable(true);
				break;
			case GET_REPLY_SUCCESS_INIT:
				if (mSwipeRefresh.isRefreshing()) {
					mSwipeRefresh.setRefreshing(false);
				}
				datalist.clear();
				datalist = (ArrayList<ReplyDetailBean>) msg.obj;
				if (datalist.size() > 0) {
					note = datalist.get(0).getNote();
					refreshHeader(note);
				}
				datalist.remove(0);
				adapter.bindData(datalist);
				mListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;
			case GET_REPLY_FAILED:
				if (mSwipeRefresh.isRefreshing()) {
					mSwipeRefresh.setRefreshing(false);
				}
				Toast.makeText(CmtsReplyDetailActivity.this, "加载评论列表失败",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		v = getLayoutInflater()
				.inflate(R.layout.activity_homeltv_details, null);
		setContentView(v);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		lruCache = ImageCache.GetLruCache(this);
		Intent intent = getIntent();
		postId = intent.getStringExtra("postId");
		initView();
	}

	@SuppressWarnings("deprecation")
	public void initView() {
		edt_reply = (EditText) this.findViewById(R.id.edt_reply);
		mSwipeRefresh = (RefreshLayout) this.findViewById(R.id.swipe_layout);
		mSwipeRefresh.setColorScheme(R.color.umeng_comm_lv_header_color1,
				R.color.umeng_comm_lv_header_color2,
				R.color.umeng_comm_lv_header_color3,
				R.color.umeng_comm_lv_header_color4);
		mSwipeRefresh.setOnRefreshListener(mSwipeRefreshListener);
		mSwipeRefresh.setOnLoadListener(mSwipeLoadListener);
		mListView = (ListView) findViewById(R.id.ltv_home_detail);
		btn_reply = (Button) findViewById(R.id.btn_reply);
		addHeader();
		datalist = new ArrayList<ReplyDetailBean>();
		adapter = new ReplyDetailAdapter(CmtsReplyDetailActivity.this);
		refresh();
	}

	@SuppressLint("InflateParams")
	public void addHeader() {
		LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View headerView = lif.inflate(R.layout.headerview_home_detail, null);
		avatar = (ImageView) headerView.findViewById(R.id.img_home_hot_head);
		name = (TextView) headerView.findViewById(R.id.tv_home_hot_name);
		sex = (ImageView) headerView.findViewById(R.id.img_home_hot_sex);
		time = (TextView) headerView.findViewById(R.id.tv_home_hot_time);
		cnt = (TextView) headerView.findViewById(R.id.tv_home_hot_content);
		rlt_tag = (RelativeLayout) headerView.findViewById(R.id.rlt_tag);
		tag = (TextView) headerView.findViewById(R.id.tv_home_hot_tag);
		tv_cmt = (TextView) headerView.findViewById(R.id.tv_home_hot_comment);
		tv_praise = (TextView) headerView.findViewById(R.id.tv_home_hot_good);
		iv_praise = (ImageView) headerView.findViewById(R.id.img_home_hot_good);
		iv_praise.setOnClickListener(this);
		Button btn_chat = (Button) headerView
				.findViewById(R.id.btn_home_hot_mark);
		btn_chat.setOnClickListener(this);
		cntImg = (ImageView) headerView.findViewById(R.id.img_home_hot_content);
		mListView.addHeaderView(headerView);
	}

	public void setPraise() {
		iv_praise.setClickable(false);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = HttpClientApi.PraiseApi(PreferenceUtils
						.getInstance(CmtsReplyDetailActivity.this)
						.getSettingUserId(), postId);
				if (result == true) {
					detailsHandler.sendEmptyMessage(PRAISE_SUCCESS);
				} else {
					detailsHandler.sendEmptyMessage(PRAISE_FAILED);
				}
			}
		}).start();
	}

	public void refreshHeader(HomeBean note) {
		if (note.getHasPraised().equals("true")) {
			iv_praise.setImageResource(R.drawable.pic_good_sel);
			iv_praise.setClickable(false);
		} else {
			iv_praise.setClickable(true);
			iv_praise.setImageResource(R.drawable.pic_good_nor);
		}
		avatar.setVisibility(View.VISIBLE);
		avatar.setClickable(true);
		avatar.setImageResource(R.drawable.default_avatar);
		if (!note.getHead().equals("")) {
			avatar.setTag(Constants.IP + note.getHead());
			new ImageCache(CmtsReplyDetailActivity.this, lruCache, avatar,
					Constants.IP, "APP", 120, 120);
		}
		avatar.setOnClickListener(this);
		name.setText(note.getName());
		if (note.getSex().equals("男")) {
			sex.setImageResource(R.drawable.pic_male);
		} else {
			sex.setImageResource(R.drawable.pic_female);
		}
		time.setText(note.getTime());
		cnt.setText(note.getContentStr());
		if (!note.getTag().equals("")) {
			tag.setText(note.getTag());
			rlt_tag.setVisibility(View.VISIBLE);
		} else {
			rlt_tag.setVisibility(View.GONE);
		}
		tv_cmt.setText(note.getComment());
		cmtNum = Integer.parseInt(note.getComment());
		tv_praise.setText(note.getGood());
		if (!note.getContentImg().equals("")) {
			cntImg.setTag(Constants.IP + note.getContentImg());
			new ImageCache(this, lruCache, cntImg, Constants.IP
					+ note.getContentImg(), "App", 800, 320);
		} else {
			cntImg.setVisibility(View.GONE);
		}
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
				msg.obj = HttpClientApi.GetNoteReplyApi(PreferenceUtils
						.getInstance(CmtsReplyDetailActivity.this)
						.getSettingUserId(), postId);
				if (msg.obj == null) {
					detailsHandler.sendEmptyMessage(GET_REPLY_FAILED);
				} else {
					msg.what = GET_REPLY_SUCCESS_INIT;
					detailsHandler.sendMessage(msg);
				}
			}
		}).start();
	}

	public void reply(View v) {
		btn_reply.setClickable(false);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0); // 强制隐藏键盘
		final String cmtStr = edt_reply.getText().toString().trim();
		if (cmtStr.equals("")) {
			Toast.makeText(CmtsReplyDetailActivity.this, "输入不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = HttpClientApi.ReplyApi(PreferenceUtils
						.getInstance(CmtsReplyDetailActivity.this)
						.getSettingUserId(), postId, cmtStr);
				if (result == true) {
					detailsHandler.sendEmptyMessage(COMMENT_SUCCESS);
				} else {
					detailsHandler.sendEmptyMessage(COMMENT_FAILED);
				}
			}
		}).start();
	}

	public void back(View v) {
		finish();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_home_hot_mark:
			if(note.getUserId().equals(PreferenceUtils.getInstance(CmtsReplyDetailActivity.this).getSettingUserId())){
				Toast.makeText(CmtsReplyDetailActivity.this, "不能Yo自己哦", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent(CmtsReplyDetailActivity.this,
					ChatActivity.class);
			if (note != null) {
				intent.putExtra("userId", note.getUserId());
				intent.putExtra("userName", note.getName());
				intent.putExtra("headerUrl", note.getHead());
				startActivity(intent);
			}else{
				Toast.makeText(CmtsReplyDetailActivity.this, "错误", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.img_home_hot_good:
			setPraise();
			break;
		case R.id.img_home_hot_head:
			Intent it = new Intent(CmtsReplyDetailActivity.this, UserInfoActivity.class);
			it.putExtra("userId", note.getUserId());
			startActivity(it);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
