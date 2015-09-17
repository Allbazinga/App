package com.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bean.MineMarkBean;
import com.app.bean.VisitorBean;
import com.app.ui.R;

public class VisitorAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<VisitorBean> mItems;

	public VisitorAdapter(Context context, List<VisitorBean> item) {
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public VisitorBean getItem(int position) {
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

		VisitorBean item = getItem(position);
		if (item.getName().toString().equals("VISITOR_TODAY_AUTHOR")) {
			convertView = mInflater.inflate(R.layout.headerview_visitor, null);
			TextView time = (TextView) convertView
					.findViewById(R.id.tv_visitor_time);
			time.setText(R.string.today);
			return convertView;
		} else if (item.getName().toString().equals("VISITOR_YESTERDAY_AUTHOR")) {
			convertView = mInflater.inflate(R.layout.headerview_visitor, null);
			TextView time = (TextView) convertView
					.findViewById(R.id.tv_visitor_time);
			time.setText(R.string.yesterday);
			return convertView;
		} else {
			convertView = mInflater.inflate(R.layout.item_visitor, null);
			ImageView avatar = (ImageView) convertView.findViewById(R.id.iv_visitor_avatar);
			TextView name = (TextView) convertView.findViewById(R.id.tv_visitor_name);
			ImageView sex = (ImageView) convertView.findViewById(R.id.iv_visitor_sex);
			avatar.setImageDrawable(item.getAvatar());
			name.setText(item.getName());
			sex.setImageDrawable(item.getSex());
		}
		return convertView;
	}
}
