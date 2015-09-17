package com.app.adapter;


import java.util.List;

import com.app.bean.FindCardBean;
import com.app.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CardPagerAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<FindCardBean> mItems;
	
	public CardPagerAdapter(Context context, List<FindCardBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public FindCardBean getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fragment_find_card, null);
			viewHolder.cdImg = (ImageView) convertView.findViewById(R.id.cmv_card_img);
			viewHolder.cdTitle = (TextView) convertView.findViewById(R.id.tv_card_title);
			viewHolder.cdCnt = (TextView) convertView.findViewById(R.id.tv_card_cnt);
			viewHolder.cdAvatar = (ImageView) convertView.findViewById(R.id.iv_card_avatar);
			viewHolder.cdName = (TextView) convertView.findViewById(R.id.tv_card_name);
			viewHolder.cdCmt = (TextView) convertView.findViewById(R.id.tv_card_comment);
			viewHolder.cdGood = (TextView) convertView.findViewById(R.id.tv_card_good);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		FindCardBean item = getItem(position);
		if(null != item){
			viewHolder.cdImg.setImageDrawable(item.getImg());
			
			viewHolder.cdTitle.setText(item.getTitle());
			viewHolder.cdCnt.setText(item.getCnt());
			viewHolder.cdAvatar.setImageDrawable(item.getHead());
			viewHolder.cdName.setText(item.getName());
			viewHolder.cdCmt.setText(item.getCmt());
			viewHolder.cdGood.setText(item.getGood());
			
		}
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView cdImg;
		TextView cdTitle;
		TextView cdCnt;
		ImageView cdAvatar;
		TextView cdName;
		TextView cdCmt;
		TextView cdGood;
	}

}


