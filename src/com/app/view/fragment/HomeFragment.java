package com.app.view.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.app.adapter.HomeHotAdapter;
import com.app.bean.ADInfo;
import com.app.bean.HomeBean;
import com.app.client.ClientApi;
import com.app.ui.AddNewNoteActivity;
import com.app.ui.GameDetailActivity;
import com.app.ui.HomeLtvDetailsActivity;
import com.app.ui.R;
import com.app.utils.Constants;
import com.app.view.baseview.ImageCycleView;
import com.app.view.baseview.ImageCycleView.ImageCycleViewListener;
import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullToRefreshLayout.OnRefreshListener;
import com.app.view.baseview.PullableListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ToggleButton;

public class HomeFragment extends Fragment implements OnRefreshListener,
		OnClickListener, OnCheckedChangeListener {

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
  
	private RelativeLayout rlt_home_add;
	private ToggleButton tb_add;

	private Button btn_new_card;
	private Button btn_new_note;
	private Button btn_new_tucao;
	/* dialog部分 */
	private AlertDialog dialog_newCard1 = null, dialog_newCard2 = null;
	private LinearLayout llt_bg_dialog = null;
	private RelativeLayout rlt_dialog_card = null;
	private ImageView iv_new_card_picture = null;
	private EditText edt_new_card_title = null;
	private EditText edt_new_card_cnt = null;
	private ImageView iv_new_card_avatar = null;
	private TextView tv_new_card_name = null;
	private Button btn_write_finish = null;

	private LinearLayout llt_bg_dialog_card2 = null;
	private ImageView iv_new_card_checked;
	private RadioGroup rg_poi, rg_rigion;
	private RadioButton rb_poi_here, rb_poi_select, rb_send2Marker, rb_private;
	private LinearLayout llt_dialog_card2;

	private String pathImage = null;

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
	private String lastTime = null;
	private Handler getDataHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.obj == null) {
				Toast.makeText(getActivity(), "网络异常，请检查设置！", Toast.LENGTH_SHORT)
						.show();
				if (msg.what == INIT) {
					tv_loading.setText("重新加载!");
					tv_loading.setClickable(true);
				}
				if (msg.what == REFRESH) {
					pullToRefreshManager
							.refreshFinish(PullToRefreshLayout.FAIL);
				}
				if (msg.what == LOAD) {
					pullToLoadManager.refreshFinish(PullToRefreshLayout.FAIL);
				}
			} else {
				if (msg.what == INIT) {
					rlt_loading.setVisibility(View.GONE);
					llt_data.setVisibility(View.VISIBLE);
					dataList.clear();
					dataList = (ArrayList<HomeBean>) msg.obj;
					if (dataList != null) {
						lastTime = dataList.get(4).getTime();
					}
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					mHomeHotAdapter.notifyDataSetChanged();
				}
				if (msg.what == REFRESH) {
					dataList.clear();
					dataList = (ArrayList<HomeBean>) msg.obj;
					if (dataList != null) {
						lastTime = dataList.get(4).getTime();
					}
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					pullToRefreshManager
							.refreshFinish(PullToRefreshLayout.SUCCEED);
					mHomeHotAdapter.notifyDataSetChanged();
				}
				if (msg.what == LOAD) {
					dataList.addAll((ArrayList<HomeBean>) msg.obj);
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					mListView.setSelection(offset);
					offset = dataList.size();
					lastTime = dataList.get(offset - 1).getTime();
					pullToLoadManager
							.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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
		v = inflater.inflate(R.layout.fragment_home, null);
		initView();
		initData();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						HomeLtvDetailsActivity.class);
				intent.putExtra("note", dataList.get(position - 1));
				intent.putExtra("postUser", dataList.get(position - 1)
						.getUserId());
				startActivity(intent);
			}
		});
		return v;
	}

	public void initView() {
		dataList = new ArrayList<HomeBean>();
		mListView = (PullableListView) v.findViewById(R.id.ltv_home_hot);
		addHeader();
		mHomeHotAdapter = new HomeHotAdapter(getActivity());
		((PullToRefreshLayout) v.findViewById(R.id.refresh_home_hot))
				.setOnRefreshListener(this);
		;
		;
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

		rlt_home_add = (RelativeLayout) v.findViewById(R.id.rlt_home_add);
		rlt_home_add.setOnClickListener(this);
		tb_add = (ToggleButton) v.findViewById(R.id.tb_add);
		tb_add.setOnCheckedChangeListener(this);

		btn_new_card = (Button) v.findViewById(R.id.btn_new_card);
		btn_new_note = (Button) v.findViewById(R.id.btn_new_note);
		btn_new_tucao = (Button) v.findViewById(R.id.btn_new_tucao);
		btn_new_card.setOnClickListener(this);
		btn_new_note.setOnClickListener(this);
		btn_new_tucao.setOnClickListener(this);
	}

	public void initData() {
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

		for (int i = 0; i < imageUrls.length; i++) {
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
			Toast.makeText(getActivity(), "content->" + info.getContent(),
					Toast.LENGTH_SHORT).show();
			Intent it = new Intent();
			it.setClass(getActivity(), GameDetailActivity.class);
			startActivity(it);
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);// 使用ImageLoader对图片进行加装！
		}
	};

	public void showNewCardDialog() {

		dialog_newCard1 = new AlertDialog.Builder(getActivity()).create();
		dialog_newCard1.show();
		dialog_newCard1.getWindow().setContentView(R.layout.dialog_new_card);
		dialog_newCard1.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog_newCard1.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		iv_new_card_picture = (ImageView) dialog_newCard1.getWindow()
				.findViewById(R.id.iv_new_card_picture);
		llt_bg_dialog = (LinearLayout) dialog_newCard1.getWindow()
				.findViewById(R.id.llt_bg_dialog);
		rlt_dialog_card = (RelativeLayout) dialog_newCard1.getWindow()
				.findViewById(R.id.rlt_dialog_card);
		edt_new_card_title = (EditText) dialog_newCard1.getWindow()
				.findViewById(R.id.edt_new_card_title);
		edt_new_card_cnt = (EditText) dialog_newCard1.getWindow().findViewById(
				R.id.edt_new_card_cnt);
		iv_new_card_avatar = (ImageView) dialog_newCard1.getWindow()
				.findViewById(R.id.iv_new_card_avatar);
		tv_new_card_name = (TextView) dialog_newCard1.getWindow().findViewById(
				R.id.tv_new_card_name);
		btn_write_finish = (Button) dialog_newCard1.getWindow().findViewById(
				R.id.btn_new_card_finish_write);
		llt_bg_dialog.setOnClickListener(this);
		btn_write_finish.setOnClickListener(this);
		iv_new_card_picture.setOnClickListener(this);
		rlt_dialog_card.setOnClickListener(this);

	}

	public void createNewCardCheckDialog() {

		dialog_newCard2 = new AlertDialog.Builder(getActivity()).create();
		dialog_newCard2.show();
		dialog_newCard2.getWindow().setContentView(R.layout.dialog_new_card2);

		llt_bg_dialog_card2 = (LinearLayout) dialog_newCard2.getWindow()
				.findViewById(R.id.llt_bg_dialog_card2);
		iv_new_card_checked = (ImageView) dialog_newCard2.getWindow()
				.findViewById(R.id.iv_new_card_checked);
		llt_dialog_card2 = (LinearLayout) dialog_newCard2.getWindow()
				.findViewById(R.id.llt_dialog_card2);
		llt_bg_dialog_card2.setOnClickListener(this);
		iv_new_card_checked.setOnClickListener(this);
		llt_dialog_card2.setOnClickListener(this);

		rg_poi = (RadioGroup) dialog_newCard2.getWindow().findViewById(
				R.id.rg_poi);
		/*
		 * rg_rigion = (RadioGroup) dialog_newCard2.getWindow().findViewById(
		 * R.id.rg_rigion);
		 */
		rb_poi_here = (RadioButton) dialog_newCard2.getWindow().findViewById(
				R.id.rb_poi_here);
		rb_poi_select = (RadioButton) dialog_newCard2.getWindow().findViewById(
				R.id.rb_poi_select);
		rb_private = (RadioButton) dialog_newCard2.getWindow().findViewById(
				R.id.rb_private);
		rb_send2Marker = (RadioButton) dialog_newCard2.getWindow()
				.findViewById(R.id.rb_send2Marker);
		/* rg_poi.setOnCheckedChangeListener(new RadioGroupChangeListener()); */
		/* rg_rigion.setOnCheckedChangeListener(new RadioGroupChangeListener()); */
	}

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
		if (mAdView != null) {
			mAdView.startImageCycle();
		}
		if (!TextUtils.isEmpty(pathImage)) {
			Bitmap bmp = BitmapFactory.decodeFile(pathImage);
			if (iv_new_card_picture != null) {
				iv_new_card_picture.setImageBitmap(bmp);
			}
		}
	};

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		// scheduledExecutorService.shutdown();
		super.onStop();
		isHidden = true;
		if (mAdView != null) {
			mAdView.pushImageCycle();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isHidden = true;
		if (mAdView != null) {
			mAdView.pushImageCycle();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isHidden = true;
		if (mAdView != null) {
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
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		pullToLoadManager = pullToRefreshLayout;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				Map<String, String> m = new HashMap<String, String>();
				JSONObject postData = null;
				if (lastTime != null) {
					m.put("postTime", lastTime);
					postData = new JSONObject(m);

				}
				Log.i("HomeHotFragment", lastTime);
				try {
					ClientApi.loadHomeData(REFRESH_URL+"?postTime="+"9jkjkj");
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				postData = null;
				msg.what = LOAD;
				getDataHandler.sendMessage(msg);
			}
		}).start();
	}

	private class RadioGroupChangeListener implements
			android.widget.RadioGroup.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (group.getId()) {
			case R.id.rg_poi:
				switch (checkedId) {
				case R.id.rb_poi_here:
					rb_poi_here.setChecked(true);
					rb_poi_select.setChecked(false);
					rb_private.setChecked(false);
					break;
				case R.id.rb_poi_select:
					rb_poi_here.setChecked(false);
					rb_poi_select.setChecked(true);
					rb_private.setChecked(false);
					break;
				case R.id.rb_private:
					rb_private.setChecked(true);
					rb_poi_here.setChecked(false);
					rb_poi_select.setChecked(false);
					break;

				default:
					break;
				}
				break;
			/*
			 * case R.id.rg_rigion: switch (checkedId) { case
			 * R.id.rb_send2Marker: break;
			 * 
			 * default: break; }
			 */

			default:
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.rlt_home_add:
			tb_add.setChecked(false);
			break;
		case R.id.btn_new_card:
			showNewCardDialog();
			tb_add.setChecked(false);
			break;
		case R.id.btn_new_note:
			tb_add.setChecked(false);
			intent.setClass(getActivity(), AddNewNoteActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_new_tucao:
			break;

		case R.id.iv_new_card_picture:
			Toast.makeText(getActivity(), "添加图片", Toast.LENGTH_SHORT).show();
			// 选择图片
			Intent it = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(it, Constants.IMG_OPEN);
			// 通过onResume()刷新数据
			break;
		case R.id.btn_new_card_finish_write:
			dialog_newCard1.dismiss();
			createNewCardCheckDialog();
			break;
		case R.id.llt_bg_dialog:
			if (dialog_newCard1 != null) {
				dialog_newCard1.dismiss();
			}
			break;
		case R.id.rlt_dialog_card:

			break;
		case R.id.llt_bg_dialog_card2:
			dialog_newCard2.dismiss();
			break;
		case R.id.iv_new_card_checked:
			dialog_newCard2.dismiss();
			break;

		case R.id.llt_dialog_card2:

			break;
		default:
			break;
		}
	}

	// 获取图片路径 响应startActivityForResult
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			// 打开图片
			if (resultCode == getActivity().RESULT_OK
					&& requestCode == Constants.IMG_OPEN) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					// 查询选择图片
					Cursor cursor = getActivity().getContentResolver().query(uri,
							new String[] { MediaStore.Images.Media.DATA }, null,
							null, null);
					// 返回 没找到选择图片
					if (null == cursor) {
						return;
					}
					// 光标移动至开头 获取图片路径
					cursor.moveToFirst();
					pathImage = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
				}
			} // end if 打开图片
		}

		public void showAddAnimation() {
			btn_new_card.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.activity_translate_in));
			btn_new_note.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.activity_translate_in));
			btn_new_tucao.startAnimation(AnimationUtils.loadAnimation(
					getActivity(), R.anim.activity_translate_in));
		}

	
	
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

		Animation am = AnimationUtils.loadAnimation(getActivity(),
				R.anim.anim_btn_add_rotate);
		switch (buttonView.getId()) {
		case R.id.tb_add:
			if (isChecked) {
				tb_add.startAnimation(am);
				tb_add.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.pic_add_cancel));
				showAddAnimation();
				rlt_home_add.setVisibility(View.VISIBLE);

			} else {
				tb_add.startAnimation(am);
				tb_add.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.pic_home_add));
				rlt_home_add.setVisibility(View.GONE);
				
			}
			break;

		default:
			break;
		}
	}

	/*
	 * @Override public void onLoad(PullableListView pullableListView) { // TODO
	 * Auto-generated method stub new Thread(new Runnable() {
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub Message
	 * msg = Message.obtain(); msg.obj = ClientApi.getHomeData(REFRESH_URL,
	 * null); msg.what = LOAD; getDataHandler.sendMessage(msg); } }).start(); }
	 */
}
