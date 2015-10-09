package com.app.view.fragment;

import com.app.ui.AppSettingsActivity;
import com.app.ui.AutherActivity;
import com.app.ui.MyCollectActivity;
import com.app.ui.MyGainActivity;
import com.app.ui.MyMarkActivity;
import com.app.ui.MyNoteActivity;
import com.app.ui.R;
import com.app.ui.SetMyInfoActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MineFragment extends Fragment implements OnClickListener {

	private View v;
	private boolean isHidden = false;
	private RelativeLayout rlt_mine_selfinfo, rlt_mine_auther, rlt_mine_note,
			rlt_mine_collect, rlt_mine_mark, rlt_mine_gain, rlt_mine_settings,
			rlt_mine_recmd2tothers, rlt_mine_feedback;
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
		initView(v);
		return v;
	}

	public void initView(View v) {
		rlt_mine_selfinfo = (RelativeLayout) v
				.findViewById(R.id.rlt_mine_selfinfo);
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
		rlt_mine_selfinfo.setOnClickListener(this);
		rlt_mine_auther.setOnClickListener(this);
		rlt_mine_note.setOnClickListener(this);
		rlt_mine_collect.setOnClickListener(this);
		rlt_mine_mark.setOnClickListener(this);
		rlt_mine_gain.setOnClickListener(this);
		rlt_mine_settings.setOnClickListener(this);
		rlt_mine_recmd2tothers.setOnClickListener(this);
		rlt_mine_feedback.setOnClickListener(this);
		intent = new Intent();
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
		case R.id.rlt_mine_selfinfo:
			intent.setClass(getActivity(), SetMyInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_mine_auther:
			intent.setClass(getActivity(), AutherActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_mine_note:
			intent.setClass(getActivity(), MyNoteActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_mine_collect:
			intent.setClass(getActivity(), MyCollectActivity.class);
			startActivity(intent);
			break;
		case R.id.rlt_mine_gain:
			intent.setClass(getActivity(), MyGainActivity.class);
			startActivity(intent);
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
			startActivity(intent);
			break;
		case R.id.rlt_mine_recmd2tothers:
			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
