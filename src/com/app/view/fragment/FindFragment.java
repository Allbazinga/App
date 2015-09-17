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
	private NoScrollViewPager vp_find;
	private ArrayList<Fragment> fragments;
	private RelativeLayout layout_find_save, layout_find_interest, layout_find_meet;
	private ImageView iv_find_save, iv_find_interest, iv_find_meet;
	private FindSaveFragment mFindSaveFragment;
	private FindInterestFragment mFindInterestFragment;
	private FindMeetFragment mFindMeetFragment;
    
	
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
		initFragment();
		return v;
	}
	
	public void initView(){
		initFragment();
		vp_find = (NoScrollViewPager) v.findViewById(R.id.vp_find);
		vp_find.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragments));
		vp_find.setOnPageChangeListener(new MyOnPageChangeListener());
		vp_find.setOffscreenPageLimit(2);
		layout_find_save = (RelativeLayout) v.findViewById(R.id.layout_find_save);
		layout_find_interest = (RelativeLayout) v.findViewById(R.id.layout_find_interest);
		layout_find_meet = (RelativeLayout) v.findViewById(R.id.layout_find_meet);
		layout_find_save.setOnClickListener(this);
		layout_find_interest.setOnClickListener(this);
		layout_find_meet.setOnClickListener(this);
		iv_find_save = (ImageView) v.findViewById(R.id.iv_find_save);
		iv_find_interest = (ImageView) v.findViewById(R.id.iv_find_interest);
		iv_find_meet = (ImageView) v.findViewById(R.id.iv_find_meet);
	}
	
	public void initFragment(){
		fragments = new ArrayList<Fragment>();
		mFindSaveFragment = new FindSaveFragment();
		mFindInterestFragment = new FindInterestFragment();
		mFindMeetFragment = new FindMeetFragment();
		fragments.add(mFindSaveFragment);
		fragments.add(mFindInterestFragment);
		fragments.add(mFindMeetFragment);
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
			vp_find.setCurrentItem(arg0);
			//changeTabView(currIndex, arg0);
		}

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
		switch (v.getId()) {
		case R.id.layout_find_save:
			vp_find.setCurrentItem(0);
			break;
		case R.id.layout_find_interest:
			vp_find.setCurrentItem(1);
			break;
		case R.id.layout_find_meet:
			vp_find.setCurrentItem(2);
			break;

		default:
			break;
		}
	}
	
}
