package com.cwl.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cwl.app.R;
import com.cwl.app.bean.MineMarkBean;
import com.cwl.app.ui.UserInfoActivity;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MineMarkAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MineMarkBean> mItems;
	private Context context;
	private LruCache<String, Bitmap> lruCache;
	
	public MineMarkAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.lruCache = ImageCache.GetLruCache(context);
	}

	public void bindData(ArrayList<MineMarkBean> mItems){
		this.mItems = mItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MineMarkBean getItem(int position) {
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
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_mine_mark, null);
			viewHolder.head = (ImageView) convertView
					.findViewById(R.id.iv_mine_mark_avatar);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.tv_mine_mark_name);
			viewHolder.scl = (TextView) convertView
					.findViewById(R.id.tv_mine_mark_scl);
			viewHolder.marked = (ImageView) convertView
					.findViewById(R.id.iv_mine_marked);
			viewHolder.sex = (ImageView) convertView
					.findViewById(R.id.iv_mine_mark_sex);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final MineMarkBean item = getItem(position);
		if (null != item) {
			if (item.getAvatar().equals("")) {
				viewHolder.head.setImageResource(R.drawable.default_avatar);
			} else {
				viewHolder.head.setTag(Constants.IP + item.getAvatar());
				new ImageCache(context, lruCache, viewHolder.head, Constants.IP
						+ item.getAvatar(), "APP", 120, 120);
			}
			viewHolder.name.setText(item.getName());
			if (item.getSex().equals("女")) {
				viewHolder.sex.setImageResource(R.drawable.pic_female);
			} else {
				viewHolder.sex.setImageResource(R.drawable.pic_male);
			}
			if (!item.getScl().equals("")) {
				viewHolder.scl.setText(item.getScl() + "  " + item.getGrade() + "级");
			} else {
				viewHolder.scl.setText(item.getGrade() + "级");
			}
			viewHolder.marked.setImageResource(R.drawable.pic_mark_yes);
			viewHolder.marked.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "取消关注功能暂未开放，请到详细资料页取消关注！"
							, Toast.LENGTH_SHORT).show();
				}
			});
			viewHolder.head.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, UserInfoActivity.class);
					intent.putExtra("userId", item.getId());
					context.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView head;
		TextView name;
		TextView scl;
		ImageView sex;
		ImageView marked;
	}
}
