package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.MyFragmentPagerAdapter;
import com.app.ui.AddNewNoteActivity;
import com.app.ui.AppSettingsActivity;
import com.app.ui.R;
import com.app.ui.VisitorActivity;
import com.app.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineFragment extends Fragment implements OnClickListener {

	private View v;
	private boolean isHidden = false;
	private ViewPager vp_mine;
	private ImageView iv_settings;
	private ArrayList<Fragment> fragments;
	private MineNoteFragment mMineNoteFragment;
	private MineCardFragment mMineCardFragment;
	private MineCollectFragment mMineCollectFragment;
	private MineMarkFragment mMineMarkFragment;

	private int currIndex = 0;
	private List<TextView> numList = null;
	private List<TextView> tabList = null;

	private TextView tv_note_num, tv_card_num, tv_collect_num, tv_mark_num;
	private TextView tv_note, tv_card, tv_collect, tv_mark;
	private RelativeLayout rlt_note, rlt_card, rlt_collect, rlt_mark;

	private TextView tv_new_visitor;

	private ImageView iv_mine_avatar;

	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private Button pick_camera, pick_album, pick_cancel;
	private String pathImage = null;

	private Intent intent = null;

	private String capture_path = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("isMineHidden")) {
				getFragmentManager().beginTransaction().hide(this).commit();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_mine, null);
		initView();
		return v;
	}

	public void initView() {
		// TODO Auto-generated method stub
		parentView = v;
		initFragment();
		vp_mine = (ViewPager) v.findViewById(R.id.vp_mine);
		vp_mine.setAdapter(new MyFragmentPagerAdapter(
				getChildFragmentManager(), fragments));
		vp_mine.setOnPageChangeListener(new MyOnPageChangeListener());
		vp_mine.setOffscreenPageLimit(2);
		iv_settings = (ImageView) v.findViewById(R.id.iv_settings);
		iv_settings.setOnClickListener(this);

		iv_mine_avatar = (ImageView) v.findViewById(R.id.iv_mine_avatar);
		iv_mine_avatar.setOnClickListener(this);

		rlt_note = (RelativeLayout) v.findViewById(R.id.rlt_note);
		rlt_card = (RelativeLayout) v.findViewById(R.id.rlt_card);
		rlt_collect = (RelativeLayout) v.findViewById(R.id.rlt_collect);
		rlt_mark = (RelativeLayout) v.findViewById(R.id.rlt_mark);
		rlt_note.setOnClickListener(this);
		rlt_card.setOnClickListener(this);
		rlt_collect.setOnClickListener(this);
		rlt_mark.setOnClickListener(this);
		tv_note_num = (TextView) v.findViewById(R.id.tv_note_num);
		tv_card_num = (TextView) v.findViewById(R.id.tv_card_num);
		tv_collect_num = (TextView) v.findViewById(R.id.tv_collect_num);
		tv_mark_num = (TextView) v.findViewById(R.id.tv_mark_num);
		numList = new ArrayList<TextView>();
		numList.add(tv_note_num);
		numList.add(tv_card_num);
		numList.add(tv_collect_num);
		numList.add(tv_mark_num);
		tv_note = (TextView) v.findViewById(R.id.tv_note);
		tv_card = (TextView) v.findViewById(R.id.tv_card);
		tv_collect = (TextView) v.findViewById(R.id.tv_collect);
		tv_mark = (TextView) v.findViewById(R.id.tv_mark);
		tabList = new ArrayList<TextView>();
		tabList.add(tv_note);
		tabList.add(tv_card);
		tabList.add(tv_collect);
		tabList.add(tv_mark);

		tv_new_visitor = (TextView) v.findViewById(R.id.tv_new_visitor);
		tv_new_visitor.setText(3 + "名新访客");
		tv_new_visitor.setOnClickListener(this);

		/* 弹出选取照片菜单 */
		pop = new PopupWindow(getActivity());
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.popwindow_pick_image, null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		pick_camera = (Button) view.findViewById(R.id.item_popupwindows_camera);
		pick_album = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		pick_cancel = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		pick_camera.setOnClickListener(this);
		pick_album.setOnClickListener(this);
		pick_cancel.setOnClickListener(this);
		parent.setOnClickListener(this);

	}

	public void initFragment() {
		fragments = new ArrayList<Fragment>();
		mMineNoteFragment = new MineNoteFragment();
		mMineCardFragment = new MineCardFragment();
		mMineCollectFragment = new MineCollectFragment();
		mMineMarkFragment = new MineMarkFragment();
		fragments.add(mMineNoteFragment);
		fragments.add(mMineCardFragment);
		fragments.add(mMineCollectFragment);
		fragments.add(mMineMarkFragment);
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			vp_mine.setCurrentItem(arg0);
			changeTabView(currIndex, arg0);
		}

	}

	public void changeTabView(int from, int to) {
		// TODO Auto-generated method stub
		if (currIndex != to) {
			currIndex = to;
			numList.get(from).setTextColor(
					getResources().getColor(R.color.white));
			numList.get(to).setTextColor(
					getResources().getColor(R.color.ui_color));

			tabList.get(from).setTextColor(
					getResources().getColor(R.color.white));
			tabList.get(to).setTextColor(
					getResources().getColor(R.color.ui_color));

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putBoolean("isMineHidden", isHidden);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		isHidden = false;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isHidden = true;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isHidden = true;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isHidden = true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.iv_settings:
			intent = new Intent();
			intent.setClass(getActivity(), AppSettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_note:
			vp_mine.setCurrentItem(0);
			changeTabView(currIndex, 0);
			break;
		case R.id.rlt_card:
			vp_mine.setCurrentItem(1);
			changeTabView(currIndex, 1);
			break;
		case R.id.rlt_collect:
			vp_mine.setCurrentItem(2);
			changeTabView(currIndex, 2);
			break;
		case R.id.rlt_mark:
			vp_mine.setCurrentItem(3);
			changeTabView(currIndex, 3);
			break;
		case R.id.tv_new_visitor:
			intent = new Intent();
			intent.setClass(getActivity(), VisitorActivity.class);
			startActivity(intent);
			tv_new_visitor.setVisibility(View.GONE);
			break;
		case R.id.iv_mine_avatar:
			ll_popup.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.activity_translate_in));
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.parent:
			pop.dismiss();
			ll_popup.clearAnimation();
			break;
		case R.id.item_popupwindows_camera:
			intent = new Intent("android.media.action.IMAGE_CAPTURE");
			/*
			 * String out_file_path = Constants.SAVED_IMAGE_DIR_PATH; File dir =
			 * new File(out_file_path); if (!dir.exists()) { dir.mkdirs(); }
			 * capture_path = Constants.SAVED_IMAGE_DIR_PATH +
			 * System.currentTimeMillis() + ".jpg";
			 * intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new
			 * File(Environment.getExternalStorageDirectory(), capture_path)));
			 */
			startActivityForResult(intent, Constants.IMG_TAKE);// 采用ForResult打开
			pop.dismiss();
			ll_popup.clearAnimation();
			break;
		case R.id.item_popupwindows_cancel:
			break;
		case R.id.item_popupwindows_Photo:
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, Constants.IMG_OPEN);
			pop.dismiss();
			ll_popup.clearAnimation();
			break;

		default:
			break;
		}
	}

	// 获取图片路径 响应startActivityForResult
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 打开图片
		switch (requestCode) {
		case Constants.IMG_OPEN:
			if (resultCode == getActivity().RESULT_OK) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					// 查询选择图片
					Cursor cursor = getActivity().getContentResolver().query(
							uri, new String[] { MediaStore.Images.Media.DATA },
							null, null, null);
					// 返回 没找到选择图片
					if (null == cursor) {
						return;
					}
					// 光标移动至开头 获取图片路径
					cursor.moveToFirst();
					pathImage = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					cursor.close();
				}
			}
			break;
		case Constants.IMG_TAKE:
			if ((resultCode == getActivity().RESULT_OK)
					&& (data.getExtras() != null)
					&& (data.getExtras().get("data") != null)) {

				iv_mine_avatar.setImageBitmap((Bitmap) data.getExtras().get(
						"data"));
				/*
				 * Bundle bundle = data.getExtras(); if (bundle != null) {
				 * Bitmap bmp = (Bitmap) bundle.get("data"); // get bitmap if
				 * (bmp != null) { iv_mine_avatar.setImageBitmap(bmp); } }
				 */
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!TextUtils.isEmpty(pathImage)) {
			Bitmap bmp = BitmapFactory.decodeFile(pathImage);

			iv_mine_avatar.setImageBitmap(bmp);

		}
		pathImage = null;
	}

}
