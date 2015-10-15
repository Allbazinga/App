package com.cwl.app.ui;

/**
 * @author allbazinga
 * tips:提取照片发送新帖子
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.cwl.app.R;
import com.cwl.app.client.ClientApi;
import com.cwl.app.utils.BitmapTools;
import com.cwl.app.utils.Constants;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
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

	private static final String TAG = "AddNewNoteActivity";
	private ImageView iv_new_note_back, iv_new_note_finish, iv_new_note_img,
			iv_new_note_tag;
	private EditText edt_new_note_cnt;
	private ToggleButton tb_new_note_niming;
	private ImageView iv_cntImg;
	private ImageView iv_isShot; // 说明是否为实拍
	private boolean isShot = false;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private Button pick_camera, pick_album, pick_cancel;
	private LinearLayout llt_picture;
	private ImageView iv_new_note_picture_all;
	private String imagePath = "";
	private static final String POST_NOTE_ERROR = "帖子发送失败";
	private static final String POST_NOTE_SUCCEEED = "发送成功！";
	private static final int MSG_POST_NOTE_ERROR = 1;
	private static final int MSG_POST_NOTE_SUCCEED = 2;
	private Handler postNoteHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MSG_POST_NOTE_ERROR:
				Toast.makeText(AddNewNoteActivity.this, POST_NOTE_ERROR,
						Toast.LENGTH_SHORT).show();
				;
				break;
			case MSG_POST_NOTE_SUCCEED:
				Toast.makeText(AddNewNoteActivity.this, POST_NOTE_SUCCEEED,
						Toast.LENGTH_SHORT).show();
				;
				AddNewNoteActivity.this.finish();
				break;

			default:
				break;
			}
		}

	};

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
		iv_new_note_back = (ImageView) findViewById(R.id.iv_back);
		iv_new_note_finish = (ImageView) findViewById(R.id.iv_new_note_finish);
		iv_new_note_img = (ImageView) findViewById(R.id.iv_new_note_img);
		iv_new_note_tag = (ImageView) findViewById(R.id.iv_new_note_tag);
		iv_new_note_back.setOnClickListener(this);
		iv_new_note_finish.setOnClickListener(this);
		iv_new_note_img.setOnClickListener(this);
		iv_new_note_tag.setOnClickListener(this);
		edt_new_note_cnt = (EditText) findViewById(R.id.edt_new_note_cnt);
		iv_cntImg = (ImageView) findViewById(R.id.iv_cntImg);
		/* iv_cntImg.setOnClickListener(this); */
		iv_isShot = (ImageView) findViewById(R.id.iv_isShot);
		iv_isShot.setVisibility(View.GONE);
		tb_new_note_niming = (ToggleButton) findViewById(R.id.tb_new_note_niming);
		tb_new_note_niming.setOnCheckedChangeListener(this);
		llt_picture = (LinearLayout) findViewById(R.id.llt_picture);
		llt_picture.setOnClickListener(this);
		iv_new_note_picture_all = (ImageView) findViewById(R.id.iv_new_note_picture_all);
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

		iv_cntImg.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				iv_cntImg.setImageBitmap(null);
				iv_isShot.setVisibility(View.GONE);
				if (!imagePath.equals("")) {
					File file = new File(imagePath);
					if (file != null) {
						file.delete();
					}
					imagePath = "";
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_new_note_finish:
			postNewNote();
			break;
		case R.id.iv_new_note_img:
			ll_popup.startAnimation(AnimationUtils.loadAnimation(
					AddNewNoteActivity.this, R.anim.activity_translate_in));
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
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
			Intent captureIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(captureIntent, Constants.IMG_TAKE);
			pop.dismiss();
			ll_popup.clearAnimation();
			break;
		case R.id.item_popupwindows_cancel:
			pop.dismiss();
			ll_popup.clearAnimation();
			break;
		case R.id.item_popupwindows_Photo:
			Intent pickIntent = new Intent(Intent.ACTION_PICK);
			pickIntent.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(pickIntent, Constants.IMG_OPEN);
			pop.dismiss();
			ll_popup.clearAnimation();
			break;

		default:
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case Constants.IMG_OPEN:

			if (resultCode == RESULT_OK) {
				Bitmap bitmap = BitmapTools
						.getSmallBitmap(getAbsoluteImagePath(data.getData()));
				iv_cntImg.setImageBitmap(bitmap);
				imagePath = BitmapTools.compressImgeToDisk(bitmap);
				if (bitmap != null) {
					isShot = false;
				}
			}

			break;
		case Constants.IMG_TAKE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				Bitmap bm = (Bitmap) bundle.get("data");
				imagePath = BitmapTools.compressImgeToDisk(bm);
				System.out.println("--imagePath-->" + imagePath);
				if (bm != null) {
					iv_cntImg.setImageBitmap(bm);
					isShot = true;
				} else {
					isShot = false;
				}
			}
			break;

		default:
			break;
		}
	}

	protected String getAbsoluteImagePath(Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Log.i(TAG, uri.toString());
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public void postNewNote() {
		String noteCnt = edt_new_note_cnt.getText().toString().trim();
		String noteTag = "我是你爸爸";
		String userId = "";
		String userName = "勤奋的千寻";
		if (tb_new_note_niming.isChecked()) {
			userName = "猜不到我";
		}
		Log.i(TAG, imagePath);
		if (noteCnt.equals("")) {
			Toast.makeText(this, Constants.ERROR_EDT_NULL, Toast.LENGTH_SHORT)
					.show();
		} else {
			final Map<String, String> noteStrMap = new HashMap<String, String>();
			noteStrMap.put("postContent", noteCnt);
			noteStrMap.put("postTag", noteTag);
			noteStrMap.put("userName", userId);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					File file = null;
					InputStream in = null;
					String result = null;
					try {
						if (!imagePath.equals("")) {
							file = new File(imagePath);
							in = new FileInputStream(file);
						}
						result = ClientApi.uploadSubmit(
								Constants.POST_NOTE_URL, noteStrMap,
								file);
						Message msg = new Message();
						if (result != null && result.equals("true")) {
							Log.i(TAG, result);
							msg.what = MSG_POST_NOTE_SUCCEED;
							postNoteHandler.sendMessage(msg);
						} else {
							msg.what = MSG_POST_NOTE_ERROR;
							postNoteHandler.sendMessage(msg);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						/*
						 * if(file != null && result.equals("true")){
						 * file.delete(); imagePath = ""; }
						 */
					}
				}
			}).start();
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		if (isShot) {
			iv_isShot.setVisibility(View.VISIBLE);
		} else {
			iv_isShot.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (!imagePath.equals("")) {
			File file = new File(imagePath);
			if (file != null) {
				file.delete();
			}
			imagePath = "";
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (!imagePath.equals("")) {
			File file = new File(imagePath);
			if (file != null) {
				file.delete();
			}
			imagePath = "";
		}
	}

}
