package com.app.adapter;

import java.util.List;

import com.app.bean.FindLabelBean;
import com.app.bean.MineMarkBean;
import com.app.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FindLabelAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<FindLabelBean> mItems;

	public FindLabelAdapter(Context context, List<FindLabelBean> item) {
		this.mInflater = LayoutInflater.from(context);
		this.mItems = item;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public FindLabelBean getItem(int position) {
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
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_find_label, null);
			viewHolder.img = (ImageView) convertView
					.findViewById(R.id.iv_label);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.tv_label);
			viewHolder.intruduce = (TextView) convertView
					.findViewById(R.id.tv_label_intruduce);
			viewHolder.marked = (ImageView) convertView
					.findViewById(R.id.iv_label_marked);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		FindLabelBean item = getItem(position);
		if (null != item) {
			viewHolder.img.setImageResource(item.getImg());
			viewHolder.label.setText(item.getName());
			viewHolder.intruduce.setText(item.getIntruduce());
			if (item.getIsMarked()==1) {
				viewHolder.marked.setImageResource(R.drawable.pic_mark_yes);
			} else {
				viewHolder.marked.setImageResource(R.drawable.pic_mark_no);
			}
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView img;
		TextView label;
		TextView intruduce;
		ImageView marked;
	}

}
