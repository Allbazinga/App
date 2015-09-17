package com.app.adapter;


import java.util.List;

import com.app.bean.MineMarkBean;
import com.app.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MineMarkAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MineMarkBean> mItems;
	
	public MineMarkAdapter(Context context, List<MineMarkBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MineMarkBean getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_mine_mark, null);
			viewHolder.head = (ImageView) convertView.findViewById(R.id.iv_mine_mark_avatar);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_mine_mark_name);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MineMarkBean item = getItem(position);
		if(null != item){
			viewHolder.head.setImageDrawable(item.getAvatar());
			
			viewHolder.name.setText(item.getName());
		}
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView head;
		TextView name;
	}

}


