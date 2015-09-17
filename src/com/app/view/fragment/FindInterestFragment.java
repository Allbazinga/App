package com.app.view.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.CardPagerAdapter;
import com.app.adapter.MyFragmentPagerAdapter;
import com.app.bean.FindCardBean;
import com.app.ui.FindCardDetailsActivity;
import com.app.ui.R;
import com.app.utils.DensityUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class FindInterestFragment extends Fragment {

	private View v;
	private ViewPager vpCard;
	private ArrayList<Fragment> fragments;
	private FrameLayout vpCardContainer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_find_interest, null);
		initView();
		/*initFragment();*/
		return v;
	}
	
	public void initView(){
		initFragment();
		vpCard = (ViewPager) v.findViewById(R.id.vpCard);
		vpCard.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragments));
		vpCard.setOnPageChangeListener(new MyOnPageChangeListener());
		vpCard.setOffscreenPageLimit(3);
		vpCard.setPageMargin(DensityUtil.dip2px(getActivity(), 21));
		if(fragments.size() > 1){
			vpCard.setCurrentItem(1);
		}
		vpCardContainer = (FrameLayout) v.findViewById(R.id.vpCardContainer);
		vpCardContainer.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return vpCard.dispatchTouchEvent(event);
			}			
		    });
	}
	
	
	public void initFragment(){
		
		fragments = new ArrayList<Fragment>();
		for(int i = 0; i < 8; i++){
			FindCardFragment fragment = new FindCardFragment(i);
			fragments.add(fragment);
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
			vpCard.setCurrentItem(arg0);
			//changeTabView(currIndex, arg0);
		}

	}

}
