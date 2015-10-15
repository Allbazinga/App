package com.cwl.app.adapter;


import java.util.List;

import com.cwl.app.R;
import com.cwl.app.bean.MsgReplyBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MsgReplyAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MsgReplyBean> mItems;
	
	public MsgReplyAdapter(Context context, List<MsgReplyBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MsgReplyBean getItem(int position) {
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
		MsgReplyBean item = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_msg_reply, null);
			viewHolder.head = (ImageView) convertView.findViewById(R.id.img_msg_reply_head);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_msg_reply_name);
			viewHolder.sex = (ImageView) convertView.findViewById(R.id.img_msg_reply_sex);
			viewHolder.time = (TextView) convertView.findViewById(R.id.tv_msg_reply_time);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_msg_reply_content);
			viewHolder.what = (TextView) convertView.findViewById(R.id.tv_msg_reply_what);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		if(null != item){
			viewHolder.head.setImageDrawable(item.getHead());
			viewHolder.name.setText(item.getName());
			viewHolder.sex.setImageDrawable(item.getSex());
			viewHolder.time.setText(item.getTime());
			viewHolder.content.setText(item.getCnt());
			viewHolder.what.setText(item.getWhat());	
		}
		
		return convertView;
	}

	private static class ViewHolder{
		ImageView head;
		TextView name;
		ImageView sex;
		TextView time;
		TextView content;
		TextView what;
	}
}


