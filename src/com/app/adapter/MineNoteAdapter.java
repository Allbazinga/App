package com.app.adapter;


import java.util.List;

import com.app.bean.MineNoteBean;
import com.app.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MineNoteAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MineNoteBean> mItems;
	
	public MineNoteAdapter(Context context, List<MineNoteBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MineNoteBean getItem(int position) {
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
		MineNoteBean item = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_mine_note, null);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.iv_mine_note);
			viewHolder.cnt = (TextView) convertView.findViewById(R.id.tv_mine_note_cnt);
			viewHolder.time = (TextView) convertView.findViewById(R.id.tv_mine_note_time);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(item  != null){
			viewHolder.img.setImageDrawable(item.getImg());
			viewHolder.cnt.setText(item.getCnt());
			viewHolder.time.setText(item.getTime());
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView img;
		TextView cnt;
		TextView time;
	}
}


