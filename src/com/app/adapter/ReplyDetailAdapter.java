package com.app.adapter;


import java.util.List;

import com.app.bean.ReplyDetailBean;
import com.app.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ReplyDetailAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ReplyDetailBean> mItems
	;
	public ReplyDetailAdapter(Context context, List<ReplyDetailBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public ReplyDetailBean getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder = null;
		ReplyDetailBean item = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_home_detail, null);
			viewHolder.cmtHead = (ImageView) convertView.findViewById(R.id.img_home_detail_cmt_head);
			viewHolder.cmtName = (TextView) convertView.findViewById(R.id.tv_home_detail_cmt_name);
			viewHolder.cmtSex = (ImageView) convertView.findViewById(R.id.img_home_detail_cmt_sex);
			viewHolder.cmtTime = (TextView) convertView.findViewById(R.id.tv_home_detail_cmt_time);
			viewHolder.cmtContent = (TextView) convertView.findViewById(R.id.tv_home_detail_cmt_content);
			viewHolder.cmtFloor = (TextView) convertView.findViewById(R.id.tv_home_detail_cmt_floor);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(null != item){
			viewHolder.cmtHead.setImageDrawable(item.getHead());
			viewHolder.cmtName.setText(item.getName());
			viewHolder.cmtSex.setImageDrawable(item.getSex());
			viewHolder.cmtTime.setText(item.getTime());
			viewHolder.cmtContent.setText(item.getContent());
			viewHolder.cmtFloor.setText(item.getFloor());	
		}
		
		return convertView;
	}
	
	private static class ViewHolder{
		ImageView cmtHead;
		TextView cmtName;
		ImageView cmtSex;
		TextView cmtTime;
		TextView cmtContent;
		TextView cmtFloor;
		
	}
}


