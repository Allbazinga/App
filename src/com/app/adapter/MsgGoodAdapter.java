package com.app.adapter;


import java.util.List;

import com.app.bean.MsgGoodBean;
import com.app.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MsgGoodAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MsgGoodBean> mItems;
	
	public MsgGoodAdapter(Context context, List<MsgGoodBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MsgGoodBean getItem(int position) {
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
		MsgGoodBean item = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_msg_good, null);
			viewHolder.head = (ImageView) convertView.findViewById(R.id.img_msg_good_head);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_msg_good_name);
			viewHolder.sex = (ImageView) convertView.findViewById(R.id.img_msg_good_sex);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.img_msg_good_picture);
			viewHolder.time = (TextView) convertView.findViewById(R.id.tv_msg_good_time);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_msg_good_content);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(item != null){
			viewHolder.head.setImageDrawable(item.getHead());
			viewHolder.name.setText(item.getName());
			viewHolder.sex.setImageDrawable(item.getSex());
			viewHolder.img.setImageDrawable(item.getImg());
			viewHolder.time.setText(item.getTime());
			viewHolder.content.setText(item.getCnt());
		}
		return convertView;
	}

	private static class ViewHolder{
		ImageView head;
		TextView name;
		ImageView sex;
		ImageView img;
		TextView time;
		TextView content;
	}
}


