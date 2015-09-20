package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.MyFragmentPagerAdapter;
import com.app.ui.AddNewNoteActivity;
import com.app.ui.R;
import com.app.utils.Constants;
import com.app.view.baseview.NoScrollViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class HomeFragment extends Fragment implements OnClickListener,
		OnCheckedChangeListener {

	private View v;
	private RelativeLayout layout_home_shift_hot, layout_home_shift_near;
	private LinearLayout layout_home_shift;

	private TextView tv_home_shift_hot, tv_home_shift_near;
	private ToggleButton tb_add;

	private Button btn_new_card;
	private Button btn_new_note;
	private Button btn_new_tucao;

	private int currIndex = 0;
	private HomeHotFragment mHomeHotFragment;
	private HomeNearFragment mHomeNearFragment;
	private boolean isHidden = false;
	private List<TextView> tvList;
	private ArrayList<Fragment> fragments;
	private NoScrollViewPager vp_home;
	private int[] shiftBgs = { R.drawable.pic_home_shift_left,
			R.drawable.pic_home_shift_right, };
	private RelativeLayout rlt_home_add;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("isHomeHidden")) {
				getFragmentManager().beginTransaction().hide(this).commit();
			}
		}
	}

	@SuppressLint("InlinedApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_home, null);
		initView();
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putBoolean("isHomeHidden", isHidden);
	}

	public void initView() {

		initFragment();
		vp_home = (NoScrollViewPager) v.findViewById(R.id.vp_home_content);
		vp_home.setAdapter(new MyFragmentPagerAdapter(
				getChildFragmentManager(), fragments));
		vp_home.setOnPageChangeListener(new MyOnPageChangeListener());
		vp_home.setOffscreenPageLimit(1);
		layout_home_shift_hot = (RelativeLayout) v
				.findViewById(R.id.layout_home_shift_hot);
		layout_home_shift_near = (RelativeLayout) v
				.findViewById(R.id.layout_home_shift_near);
		tb_add = (ToggleButton) v.findViewById(R.id.tb_add);
		tb_add.setOnCheckedChangeListener(this);

		btn_new_card = (Button) v.findViewById(R.id.btn_new_card);
		btn_new_note = (Button) v.findViewById(R.id.btn_new_note);
		btn_new_tucao = (Button) v.findViewById(R.id.btn_new_tucao);
		btn_new_card.setOnClickListener(this);
		btn_new_note.setOnClickListener(this);
		btn_new_tucao.setOnClickListener(this);

		layout_home_shift_hot.setOnClickListener(this);
		layout_home_shift_near.setOnClickListener(this);
		layout_home_shift = (LinearLayout) v
				.findViewById(R.id.layout_home_shift);
		rlt_home_add = (RelativeLayout) v.findViewById(R.id.rlt_home_add);
		rlt_home_add.setOnClickListener(this);
		tv_home_shift_hot = (TextView) v.findViewById(R.id.tv_home_shift_hot);
		tv_home_shift_near = (TextView) v.findViewById(R.id.tv_home_shift_near);
		tvList = new ArrayList<TextView>();
		tvList.add(tv_home_shift_hot);
		tvList.add(tv_home_shift_near);

	}

	@SuppressLint("Recycle")
	public void initFragment() {

		fragments = new ArrayList<Fragment>();
		mHomeHotFragment = new HomeHotFragment();
		mHomeNearFragment = new HomeNearFragment();
		fragments.add(mHomeHotFragment);
		fragments.add(mHomeNearFragment);
	}

	/*
	 * public void switchContent(Fragment from, Fragment to) { if (mContent !=
	 * to) { mContent = to; FragmentTransaction transaction =
	 * getChildFragmentManager() .beginTransaction(); if (!to.isAdded()) {
	 * transaction.hide(from).add(R.id.layout_home_content, to) .commit(); }
	 * else { transaction.hide(from).show(to).commit(); } } }
	 */

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
	/*	rg_rigion = (RadioGroup) dialog_newCard2.getWindow().findViewById(
				R.id.rg_rigion);*/
		rb_poi_here = (RadioButton) dialog_newCard2.getWindow().findViewById(
				R.id.rb_poi_here);
		rb_poi_select = (RadioButton) dialog_newCard2.getWindow().findViewById(
				R.id.rb_poi_select);
		rb_private = (RadioButton) dialog_newCard2.getWindow().findViewById(
				R.id.rb_private);
		rb_send2Marker = (RadioButton) dialog_newCard2.getWindow()
				.findViewById(R.id.rb_send2Marker);
		rg_poi.setOnCheckedChangeListener(new RadioGroupChangeListener());
		/*rg_rigion.setOnCheckedChangeListener(new RadioGroupChangeListener());*/
	}

	@SuppressWarnings("deprecation")
	public void changeTabView(int from, int to) {

		if (currIndex != to) {
			currIndex = to;
			tvList.get(from).setTextColor(
					getResources().getColor(R.color.white));
			tvList.get(to).setTextColor(
					getResources().getColor(R.color.ui_color));
			layout_home_shift.setBackgroundDrawable(getResources().getDrawable(
					shiftBgs[to]));

		}
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
			vp_home.setCurrentItem(arg0);
			changeTabView(currIndex, arg0);
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
			/*case R.id.rg_rigion:
				switch (checkedId) {
				case R.id.rb_send2Marker:
					break;

				default:
					break;
				}*/

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
		case R.id.layout_home_shift_hot:
			vp_home.setCurrentItem(0);
			changeTabView(currIndex, 0);
			break;
		case R.id.layout_home_shift_near:
			vp_home.setCurrentItem(1);
			changeTabView(currIndex, 1);
			break;
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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!TextUtils.isEmpty(pathImage)) {
			Bitmap bmp = BitmapFactory.decodeFile(pathImage);
			if (iv_new_card_picture != null) {
				iv_new_card_picture.setImageBitmap(bmp);
			}
		}
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

}
