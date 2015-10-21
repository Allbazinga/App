package com.cwl.app.fragment;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.adapter.HomeAdapter;
import com.cwl.app.bean.ADInfo;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.refresher.RefreshLayout;
import com.cwl.app.refresher.RefreshLayout.OnLoadListener;
import com.cwl.app.ui.AddNewNoteActivity;
import com.cwl.app.ui.CmtsReplyDetailActivity;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.PreferenceUtils;
import com.cwl.app.widget.ImageCycleView;
import com.cwl.app.widget.ImageCycleView.ImageCycleViewListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ToggleButton;

public class HomeFragment extends Fragment implements OnClickListener,
		OnCheckedChangeListener {

	private View v;
	private ListView mListView;
	private ArrayList<HomeBean> dataList;
	private HomeAdapter mHomeHotAdapter;
	private boolean isHidden = false;
	private boolean hidden = false;
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
	private ProgressDialog pd = null;
	private String pathImage = null;

	private ImageCycleView mAdView = null;
	private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
	private String[] imageUrls = {
			"http://upload.dlut.edu.cn/2015/0930/1443605019671.jpg",
			"http://upload.dlut.edu.cn/2015/1017/1445074037838.jpg",
			"http://upload.dlut.edu.cn/2015/1017/1445074040416.jpg",
			"http://upload.dlut.edu.cn/2015/1018/1445138271280.jpg",
	};
	private static final int INIT = 0;
	private static final int REFRESH = 1;
	private static final int LOAD = 2;
	private int offset = 0;
	private String lastTime = null;
	private RefreshLayout mSwipeRefresh = null;
	private Handler getDataHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.obj == null) {
				/*if (pd != null)
					pd.dismiss();*/
				mSwipeRefresh.setRefreshing(false);
				Toast.makeText(getActivity(), "网络异常，请检查设置！", 0).show();
				if (msg.what == INIT) {
					tv_loading.setText("重新加载!");
					tv_loading.setClickable(true);
				}
				if (msg.what == REFRESH) {
					mSwipeRefresh.setRefreshing(false);
				}
				if (msg.what == LOAD) {
					mSwipeRefresh.setLoading(false);
				}
			} else {
				if (msg.what == INIT) {
					//pd.dismiss();
					
					rlt_loading.setVisibility(View.GONE);
					llt_data.setVisibility(View.VISIBLE);
					dataList.clear();
					dataList = (ArrayList<HomeBean>) msg.obj;
					offset = dataList.size();
					if (dataList.size() != 0) {
						lastTime = dataList.get(dataList.size() - 1).getTime();
					}
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					mHomeHotAdapter.notifyDataSetChanged();
					mSwipeRefresh.setRefreshing(false);
				}
				if (msg.what == REFRESH) {
					// pd.dismiss();
					Toast.makeText(getActivity(), "刷新成功！", 0).show();
					dataList.clear();
					dataList = (ArrayList<HomeBean>) msg.obj;
					offset = dataList.size();
					if (dataList.size() != 0) {
						lastTime = dataList.get(dataList.size() - 1).getTime();
					}
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					mSwipeRefresh.setRefreshing(false);
					mHomeHotAdapter.notifyDataSetChanged();
				}
				if (msg.what == LOAD) {
					dataList.addAll((ArrayList<HomeBean>) msg.obj);
					if (dataList.size() != 0) {
						lastTime = dataList.get(dataList.size() - 1).getTime();
					}
					mHomeHotAdapter.bindData(dataList);
					mListView.setAdapter(mHomeHotAdapter);
					mListView.setSelection(offset);
					offset = dataList.size();
					mSwipeRefresh.setLoading(false);
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
						CmtsReplyDetailActivity.class);
				intent.putExtra("postId", dataList.get(position - 1).getNoteId());
				startActivity(intent);
			}
		});
		return v;
	}

	@SuppressWarnings("deprecation")
	public void initView() {
		dataList = new ArrayList<HomeBean>();
		mListView = (ListView) v.findViewById(R.id.ltv_home);
		addHeader();
		mHomeHotAdapter = new HomeAdapter(getActivity());
		mSwipeRefresh = (RefreshLayout) v.findViewById(R.id.swipe_layout);
		// 设置下拉刷新时的颜色值,颜色值需要定义在xml中
		mSwipeRefresh.setColorScheme(R.color.umeng_comm_lv_header_color1,
				R.color.umeng_comm_lv_header_color2,
				R.color.umeng_comm_lv_header_color3,
				R.color.umeng_comm_lv_header_color4);
		mSwipeRefresh.setOnRefreshListener(mSwipeRefreshListener);
		mSwipeRefresh.setOnLoadListener(mSwipeLoadListener);
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
		/*pd = new ProgressDialog(getActivity());
		pd.setMessage("正在加载中...");
		pd.show();*/
		mSwipeRefresh.setRefreshing(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.obj = HttpClientApi.GetHomeListApi(PreferenceUtils
						.getInstance(getActivity()).getSettingUserId(), "");
				msg.what = INIT;
				getDataHandler.sendMessage(msg);
			}
		}).start();
	}

	public void addHeader() {
		LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		View headerView = lif.inflate(R.layout.headerview_home_hot, null);
		View footerView = lif.inflate(R.layout.footer_find, null);
		for (int i = 0; i < imageUrls.length; i++) {
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
			info.setContent("top-->" + i);
			infos.add(info);
		}
		mAdView = (ImageCycleView) headerView.findViewById(R.id.ad_view);
		mAdView.setImageResources(infos, mAdCycleViewListener);

		mListView.addHeaderView(headerView, null, true);
		mListView.addFooterView(footerView, null, false);
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			/*Toast.makeText(getActivity(), "" + info.getContent(),
					Toast.LENGTH_SHORT).show();
			Intent it = new Intent();
			it.setClass(getActivity(), GameDetailActivity.class);
			startActivity(it);*/
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);// 使用ImageLoader对图片进行加装！
		}
	};

	private OnRefreshListener mSwipeRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = Message.obtain();
					msg.obj = HttpClientApi.GetHomeListApi(PreferenceUtils
							.getInstance(getActivity()).getSettingUserId(), "");
					msg.what = REFRESH;
					getDataHandler.sendMessage(msg);
				}
			}).start();
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
					Message msg = Message.obtain();
					/*
					 * String t = ""; if (lastTime != null) { String[] times =
					 * lastTime.split("\\ "); t = times[0] + "%20" + times[1]; }
					 * 
					 * Log.i("HomeHotFragment", t);
					 */
					msg.obj = HttpClientApi.GetHomeListApi(PreferenceUtils
							.getInstance(getActivity()).getSettingUserId(),
							lastTime);
					msg.what = LOAD;
					getDataHandler.sendMessage(msg);
				}
			}).start();
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
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		/*if (!hidden)
			initData();*/
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
			//showNewCardDialog();
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
			tb_add.setChecked(false);
			break;
		case R.id.btn_new_note:
			tb_add.setChecked(false);
			intent.setClass(getActivity(), AddNewNoteActivity.class);
			startActivityForResult(intent, 10);
			break;
		case R.id.btn_new_tucao:
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
			tb_add.setChecked(false);
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
						R.drawable.pic_add_black));
				rlt_home_add.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}

}
