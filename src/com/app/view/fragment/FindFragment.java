package com.app.view.fragment;

import java.util.ArrayList;
import com.app.adapter.MyFragmentPagerAdapter;
import com.app.ui.R;
import com.app.view.baseview.NoScrollViewPager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FindFragment extends Fragment implements OnClickListener {

	private View v;
	private boolean isHidden = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			if(savedInstanceState.getBoolean("isFindHidden")){
				getFragmentManager().beginTransaction().hide(this).commit();
			}
		}
	}
	
	@SuppressLint("InlinedApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_find, null);
		initView();
		return v;
	}
	
	public void initView(){
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putBoolean("isFindHidden", isHidden);
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
	}
	
}
