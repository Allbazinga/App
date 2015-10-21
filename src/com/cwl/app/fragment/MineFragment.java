package com.cwl.app.fragment;

import com.cwl.app.R;
import com.cwl.app.ui.AppSettingsActivity;
import com.cwl.app.ui.MyMarkActivity;
import com.cwl.app.ui.MyNoteActivity;
import com.cwl.app.ui.SetMyInfoActivity;
import com.cwl.app.ui.UserInfoActivity;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;
import com.cwl.app.utils.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MineFragment extends Fragment implements OnClickListener {

	private View v;
	private boolean isHidden = false;
	private LruCache<String, Bitmap> lruCache;
	private PreferenceUtils mPreferenceUtils;
	private RelativeLayout rlt_mine_myinfo, rlt_mine_setinfo, rlt_mine_auther, rlt_mine_note,
			rlt_mine_collect, rlt_mine_mark, rlt_mine_gain, rlt_mine_settings,
			rlt_mine_recmd2tothers, rlt_mine_feedback;
	private ImageView iv_mine_avatar;
	private TextView tv_mine_name;
	private TextView tv_mine_scl;
	private String userName = "";
	private String userScl = "";
	private String userGrade = "";
	private String userPic = "";
	private Intent intent = null;

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

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_mine, null);
		mPreferenceUtils = PreferenceUtils.getInstance(getActivity());
		lruCache = ImageCache.GetLruCache(getActivity());
		initView(v);
		return v;
	}

	public void initView(View v) {
		rlt_mine_myinfo = (RelativeLayout) v.findViewById(R.id.rlt_mine_myinfo);
		rlt_mine_setinfo = (RelativeLayout) v
				.findViewById(R.id.rlt_mine_setinfo);
		rlt_mine_auther = (RelativeLayout) v.findViewById(R.id.rlt_mine_auther);
		rlt_mine_note = (RelativeLayout) v.findViewById(R.id.rlt_mine_note);
		rlt_mine_collect = (RelativeLayout) v
				.findViewById(R.id.rlt_mine_collect);
		rlt_mine_mark = (RelativeLayout) v.findViewById(R.id.rlt_mine_mark);
		rlt_mine_gain = (RelativeLayout) v.findViewById(R.id.rlt_mine_gain);
		rlt_mine_settings = (RelativeLayout) v
				.findViewById(R.id.rlt_mine_settings);
		rlt_mine_recmd2tothers = (RelativeLayout) v
				.findViewById(R.id.rlt_mine_recmd2tothers);
		rlt_mine_feedback = (RelativeLayout) v
				.findViewById(R.id.rlt_mine_feedback);
		rlt_mine_myinfo.setOnClickListener(this);
		rlt_mine_setinfo.setOnClickListener(this);
		rlt_mine_auther.setOnClickListener(this);
		rlt_mine_note.setOnClickListener(this);
		rlt_mine_collect.setOnClickListener(this);
		rlt_mine_mark.setOnClickListener(this);
		rlt_mine_gain.setOnClickListener(this);
		rlt_mine_settings.setOnClickListener(this);
		rlt_mine_recmd2tothers.setOnClickListener(this);
		rlt_mine_feedback.setOnClickListener(this);
		iv_mine_avatar = (ImageView) v.findViewById(R.id.iv_mine_avatar);
		tv_mine_name = (TextView) v.findViewById(R.id.tv_mine_name);
		tv_mine_scl = (TextView) v.findViewById(R.id.tv_mine_scl);
		refresh();
		intent = new Intent();
	}

	public void refresh(){
		userName = mPreferenceUtils.getSettingUserName();
		userScl = mPreferenceUtils.getSettingUserScl();
		userGrade = mPreferenceUtils.getSettingUserStartScl();
		userPic = mPreferenceUtils.getSettingUserPic();
		tv_mine_name.setText(userName);
		if (userScl.equals("") && userGrade.equals("")) {
			tv_mine_scl.setText("您还没完善个人信息~");
		} else if(!userScl.equals("")){
			tv_mine_scl.setText(userScl + "  " + userGrade + "级");
		} else {
			tv_mine_scl.setText(userGrade + "级");
		}
		iv_mine_avatar.setImageResource(R.drawable.default_avatar);
		if (!userPic.equals("")) {
			iv_mine_avatar.setTag(Constants.IP + userPic);
			new ImageCache(getActivity(), lruCache, iv_mine_avatar,
					Constants.IP + userPic, "App", 320, 320);
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
	public void onResume() {
		// TODO Auto-generated method stub
		refresh();
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rlt_mine_myinfo:
			intent.setClass(getActivity(), UserInfoActivity.class);
			intent.putExtra("userId", PreferenceUtils.getInstance(getActivity()).getSettingUserId());
			startActivity(intent);
			break;
		case R.id.rlt_mine_setinfo:
			intent.setClass(getActivity(), SetMyInfoActivity.class);
			startActivityForResult(intent, 10);
			break;
		case R.id.rlt_mine_auther:
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
			/*intent.setClass(getActivity(), AutherActivity.class);
			startActivity(intent);*/
			break;
		case R.id.rlt_mine_note:
			intent.setClass(getActivity(), MyNoteActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_mine_collect:
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
			/*intent.setClass(getActivity(), MyCollectActivity.class);
			startActivity(intent);*/
			break;
		case R.id.rlt_mine_gain:
			/*intent.setClass(getActivity(), MyGainActivity.class);
			startActivity(intent);*/
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rlt_mine_mark:
			intent.setClass(getActivity(), MyMarkActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_mine_settings:
			intent.setClass(getActivity(), AppSettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_mine_feedback:
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rlt_mine_recmd2tothers:
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
