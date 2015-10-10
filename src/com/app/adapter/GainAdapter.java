package com.app.adapter;


import java.util.List;

import com.app.bean.GainBean;
import com.app.ui.R;
import com.app.ui.R.color;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class GainAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<GainBean> mItems;
	
	public GainAdapter(Context context, List<GainBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public GainBean getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_gain, null);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.iv_gain);
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_gain_title);
			viewHolder.cnt = (TextView) convertView.findViewById(R.id.tv_gain_cnt);
			viewHolder.num = (TextView) convertView.findViewById(R.id.tv_gain_num);
			viewHolder.finish = (TextView) convertView.findViewById(R.id.tv_gain_finish);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		GainBean item = getItem(position);
		if(null != item){
			viewHolder.img.setImageDrawable(item.getImg());
			viewHolder.title.setText(item.getTitle());
			viewHolder.cnt.setText(item.getCnt());
			viewHolder.num.setText(item.getNum());
			if(item.getFinish().equals("1")){
				viewHolder.finish.setBackgroundResource(R.drawable.bg_tv_gain_finish_yes);
				viewHolder.finish.setTextColor(Color.BLACK);
				viewHolder.finish.setText("get √");
			}else{
				viewHolder.finish.setBackgroundResource(R.drawable.bg_tv_gain_finish_no);
				viewHolder.finish.setText("未完成");
				viewHolder.finish.setTextColor(Color.WHITE);
			}
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView img;
		TextView title;
		TextView cnt;
		TextView num;
		TextView finish;
	}
}


