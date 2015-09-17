package com.app.ui;

import java.io.File;

import com.app.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddNewNoteActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private ImageView iv_new_note_back, iv_new_note_finish, iv_new_note_img,
			iv_new_note_emotion, iv_new_note_tag;
	private EditText edt_new_note_cnt;

	private ToggleButton tb_new_note_niming;

	private ImageView iv_cntImg;
	private ImageView iv_isShot; // 说明是否为实拍
	private boolean isShot = false;

	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private Button pick_camera, pick_album, pick_cancel;

	private String pathImage = null;

	private Intent intent = null;

	private String capture_path = null;

	private LinearLayout llt_picture;
	private ImageView iv_new_note_picture_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(
				R.layout.activity_add_new_note, null);
		setContentView(parentView);
		initView();
	}

	public void initView() {

		iv_new_note_back = (ImageView) findViewById(R.id.iv_new_note_back);
		iv_new_note_finish = (ImageView) findViewById(R.id.iv_new_note_finish);
		iv_new_note_img = (ImageView) findViewById(R.id.iv_new_note_img);
		iv_new_note_emotion = (ImageView) findViewById(R.id.iv_new_note_emotion);
		iv_new_note_tag = (ImageView) findViewById(R.id.iv_new_note_tag);
		iv_new_note_back.setOnClickListener(this);
		iv_new_note_finish.setOnClickListener(this);
		iv_new_note_img.setOnClickListener(this);
		iv_new_note_emotion.setOnClickListener(this);
		iv_new_note_tag.setOnClickListener(this);

		edt_new_note_cnt = (EditText) findViewById(R.id.edt_new_note_cnt);
		iv_cntImg = (ImageView) findViewById(R.id.iv_cntImg);
		iv_cntImg.setOnClickListener(this);
		iv_isShot = (ImageView) findViewById(R.id.iv_isShot);
		iv_isShot.setVisibility(View.GONE);

		tb_new_note_niming = (ToggleButton) findViewById(R.id.tb_new_note_niming);
		tb_new_note_niming.setOnCheckedChangeListener(this);

		llt_picture = (LinearLayout) findViewById(R.id.llt_picture);
		llt_picture.setOnClickListener(this);
		iv_new_note_picture_all = (ImageView) findViewById(R.id.iv_new_note_picture_all);

		/* 弹出选取照片菜单 */
		pop = new PopupWindow(AddNewNoteActivity.this);
		View view = getLayoutInflater().inflate(R.layout.popwindow_pick_image,
				null);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_new_note_back:
			finish();
			break;
		case R.id.iv_new_note_finish:
			break;
		case R.id.iv_new_note_img:
			ll_popup.startAnimation(AnimationUtils.loadAnimation(
					AddNewNoteActivity.this, R.anim.activity_translate_in));
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.iv_new_note_emotion:
			break;
		case R.id.iv_new_note_tag:
			break;
		case R.id.iv_cntImg:
			llt_picture.setVisibility(View.VISIBLE);
			iv_cntImg.setDrawingCacheEnabled(true);
			iv_new_note_picture_all.setImageBitmap(Bitmap
					.createBitmap(iv_cntImg.getDrawingCache()));
			iv_cntImg.setDrawingCacheEnabled(false);
			break;
		case R.id.llt_picture:
			llt_picture.setVisibility(View.GONE);
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
			isShot = true;
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
			isShot = false;
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
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					// 查询选择图片
					Cursor cursor = getContentResolver().query(uri,
							new String[] { MediaStore.Images.Media.DATA },
							null, null, null);
					// 返回 没找到选择图片
					if (null == cursor) {
						return;
					}
					// 光标移动至开头 获取图片路径
					cursor.moveToFirst();
					pathImage = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					isShot = false;
					cursor.close();
				}
			}
			break;
		case Constants.IMG_TAKE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				if (bundle != null) {
					Bitmap bmp = (Bitmap) bundle.get("data"); // get bitmap
					if (bmp != null) {
						iv_cntImg.setImageBitmap(bmp);
						isShot = true;
					} else {
						isShot = false;
					}
				}
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
			if (iv_cntImg != null) {
				iv_cntImg.setImageBitmap(bmp);
			}
		}
		if (isShot) {
			iv_isShot.setVisibility(View.VISIBLE);
		} else {
			iv_isShot.setVisibility(View.GONE);
		}
		isShot = false;
		pathImage = null;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.tb_new_note_niming:
			if (isChecked) {
				tb_new_note_niming.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.pic_niming_true));
				Toast.makeText(this, "匿名成功", Toast.LENGTH_SHORT).show();
			} else {
				tb_new_note_niming.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.pic_niming_false));
				Toast.makeText(this, "取消匿名", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}
}
