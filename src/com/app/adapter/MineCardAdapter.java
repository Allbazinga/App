package com.app.adapter;


import java.util.List;

import com.app.bean.MineCardBean;
import com.app.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MineCardAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MineCardBean> mItems;
	
	public MineCardAdapter(Context context, List<MineCardBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MineCardBean getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_mine_card, null);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.iv_mine_card);
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_mine_card_title);
			viewHolder.cnt = (TextView) convertView.findViewById(R.id.tv_mine_card_cnt);
			viewHolder.cmt = (TextView) convertView.findViewById(R.id.tv_mine_card_comment);
			viewHolder.good = (TextView) convertView.findViewById(R.id.tv_mine_card_good);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MineCardBean item = getItem(position);
		if(null != item){
			viewHolder.img.setImageDrawable(item.getImg());
			viewHolder.title.setText(item.getTitle());
			viewHolder.cnt.setText(item.getCnt());
			viewHolder.cmt.setText(item.getCmt());
			viewHolder.good.setText(item.getGood());
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView img;
		TextView title;
		TextView cnt;
		TextView cmt;
		TextView good;
	}
}


