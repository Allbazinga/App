package com.app.adapter;


import java.util.List;

import com.app.bean.HomeBean;
import com.app.listener.ListItemOnClickListener;
import com.app.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeNearAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<HomeBean> mItems;
	private Context context;
	
	public HomeNearAdapter(Context context, List<HomeBean> item){
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public HomeBean getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_home_near, null);
			viewHolder.head = (ImageView) convertView.findViewById(R.id.img_home_near_head);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_home_near_name);
			viewHolder.sex = (ImageView) convertView.findViewById(R.id.img_home_near_sex);
			viewHolder.time = (TextView) convertView.findViewById(R.id.tv_home_near_time);
			viewHolder.contentStr = (TextView) convertView.findViewById(R.id.tv_home_near_content);
			viewHolder.contentImg = (ImageView) convertView.findViewById(R.id.img_home_near_content);
			viewHolder.tag = (TextView) convertView.findViewById(R.id.tv_home_near_tag);
			viewHolder.comment = (TextView) convertView.findViewById(R.id.tv_home_near_comment);
			viewHolder.good = (TextView) convertView.findViewById(R.id.tv_home_near_good);
			viewHolder.mark = (Button) convertView.findViewById(R.id.btn_home_near_mark);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		HomeBean item = getItem(position);
		if(null != item){
			/*viewHolder.head.setImageDrawable(item.getHead());*/
			viewHolder.name.setText(item.getName());
			/*viewHolder.sex.setImageDrawable(item.getSex());*/
			viewHolder.time.setText(item.getTime());
			viewHolder.contentStr.setText(item.getContentStr());
			/*if(item.getContentImg() != null){
				viewHolder.contentImg.setVisibility(View.VISIBLE);
				viewHolder.contentImg.setImageDrawable(item.getContentImg());
			}else{
				viewHolder.contentImg.setVisibility(View.GONE);
			}*/
			viewHolder.tag.setText(item.getTag());
			if(item.getComment().length() == 1){
				viewHolder.comment.setText(" " + item.getComment());
			}else{
				viewHolder.comment.setText(item.getComment());
			}
			if(item.getGood().length() == 1){
				viewHolder.good.setText(" " + item.getGood());
			}else{
				viewHolder.good.setText(item.getGood());
			}
			viewHolder.head.setOnClickListener(new ListItemOnClickListener(position, this.context));
			viewHolder.mark.setOnClickListener(new ListItemOnClickListener(position, context));
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView head;
		TextView name;
		ImageView sex;
		TextView time;
		TextView contentStr;
		ImageView contentImg;
		TextView tag;
		TextView comment;
		TextView good;
		Button mark;
		
	}

}


