package com.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.app.bean.FindCardBean;

import android.content.ClipData.Item;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;



public class CardPageAdapter extends PagerAdapter {

	private List<FindCardBean> mItems;
	private Context context;
	
	public CardPageAdapter(){
		
	}
	public CardPageAdapter(Context context, List<FindCardBean> items){
		this.context = context;
		this.mItems = items;
	}
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}
     
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	public FindCardBean getItem(int position){
		
		return mItems.get(position);
	}
	
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}
   
	

}
