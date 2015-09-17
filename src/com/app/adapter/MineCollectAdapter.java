package com.app.adapter;


import java.util.List;

import com.app.bean.MineCollectBean;
import com.app.bean.MineMarkBean;
import com.app.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MineCollectAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MineCollectBean> mItems;
	
	public MineCollectAdapter(Context context, List<MineCollectBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MineCollectBean getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_mine_collect, null);
			viewHolder.head = (ImageView) convertView.findViewById(R.id.iv_mine_collect_avatar);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_mine_collect_name);
			viewHolder.sex = (ImageView) convertView.findViewById(R.id.iv_mine_collect_sex);
			viewHolder.time = (TextView) convertView.findViewById(R.id.tv_mine_collect_time);
			viewHolder.cnt = (TextView) convertView.findViewById(R.id.tv_mine_collect_cnt);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.iv_mine_collect_cntImg);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MineCollectBean item = getItem(position);
		if(null != item){
			viewHolder.head.setImageDrawable(item.getAvatar());
			viewHolder.name.setText(item.getName());
			viewHolder.sex.setImageDrawable(item.getSex());
			viewHolder.time.setText(item.getTime());
			viewHolder.cnt.setText(item.getCnt());
			viewHolder.img.setImageDrawable(item.getImg());
		}
		return convertView;
	}
	
	private static class ViewHolder{
		ImageView head;
		TextView name;
		ImageView sex;
		TextView time;
		TextView cnt;
		ImageView img;
	}

}


