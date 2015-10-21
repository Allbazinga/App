package com.cwl.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cwl.app.R;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.bean.MsgGoodBean;
import com.cwl.app.ui.UserInfoActivity;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MsgGoodAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MsgGoodBean> mItems;
	private Context context;
	private LruCache<String, Bitmap> lruCache;

	public MsgGoodAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.lruCache = ImageCache.GetLruCache(context);
	}

	public void bindData(ArrayList<MsgGoodBean> items) {
		this.mItems = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public MsgGoodBean getItem(int position) {
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
		final MsgGoodBean item = getItem(position);
		HomeBean note = item.getNote();
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_msg_good, null);
			viewHolder.head = (ImageView) convertView
					.findViewById(R.id.img_msg_good_head);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.tv_msg_good_name);
			viewHolder.sex = (ImageView) convertView
					.findViewById(R.id.img_msg_good_sex);
			viewHolder.img = (ImageView) convertView
					.findViewById(R.id.img_msg_good_picture);
			viewHolder.rlt_img = (RelativeLayout) convertView
					.findViewById(R.id.layout_msg_good_picture);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.tv_msg_good_time);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.tv_msg_good_content);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (item != null) {
			viewHolder.head.setImageResource(R.drawable.default_avatar);
			if (!item.getHead().equals("")) {
				viewHolder.head.setTag(Constants.IP + item.getHead());
				new ImageCache(context, lruCache, viewHolder.head, Constants.IP
						+ item.getHead(), "APP", 120, 120);
			}
			viewHolder.name.setText(item.getName());
			if (item.getSex().equals("女")) {
				viewHolder.sex.setImageResource(R.drawable.pic_female);
			} else {
				viewHolder.sex.setImageResource(R.drawable.pic_male);
			}
			if (note.getContentImg().equals("")) {
				viewHolder.img.setVisibility(View.GONE);
				viewHolder.rlt_img.setVisibility(View.GONE);
			} else {
				viewHolder.img.setVisibility(View.VISIBLE);
				viewHolder.rlt_img.setVisibility(View.VISIBLE);
				viewHolder.img.setImageResource(R.drawable.defaultcovers);
				viewHolder.img.setTag(Constants.IP + note.getContentImg());
				new ImageCache(context, lruCache, viewHolder.img, Constants.IP
						+ note.getContentImg(), "APP", 120, 120);
			}
			viewHolder.time.setText(item.getTime());
			viewHolder.content.setText("你的帖子：" + note.getContentStr());
			viewHolder.head.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent((Activity) context, UserInfoActivity.class);
					intent.putExtra("userId", item.getUserId());
					((Activity) context).startActivity(intent);
				}
			});
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView head;
		TextView name;
		ImageView sex;
		ImageView img;
		TextView time;
		TextView content;
		RelativeLayout rlt_img;
	}
}
