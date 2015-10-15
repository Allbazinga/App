package com.cwl.app.adapter;


import java.util.List;

import com.cwl.app.R;
import com.cwl.app.bean.MsgBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MsgAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MsgBean> mItems;
	
	public MsgAdapter(Context context, List<MsgBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MsgBean getItem(int position) {
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
		MsgBean item = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_msg, null);
			viewHolder.msgHead = (ImageView) convertView.findViewById(R.id.img_msg_head);
			viewHolder.msgName = (TextView) convertView.findViewById(R.id.tv_msg_name);
			viewHolder.msgSex = (ImageView) convertView.findViewById(R.id.img_msg_sex);
			viewHolder.msgTime = (TextView) convertView.findViewById(R.id.tv_msg_time);
			viewHolder.msgContent = (TextView) convertView.findViewById(R.id.tv_msg_short);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(null != item){
			viewHolder.msgHead.setImageDrawable(item.getHead());
			viewHolder.msgName.setText(item.getName());
			viewHolder.msgSex.setImageDrawable(item.getSex());
			viewHolder.msgTime.setText(item.getTime());
			viewHolder.msgContent.setText(item.getContent());
		}
		return convertView;
	}
	
	private static class ViewHolder{
		ImageView msgHead;
		TextView msgName;
		ImageView msgSex;
		TextView msgTime;
		TextView msgContent;
	}
}


