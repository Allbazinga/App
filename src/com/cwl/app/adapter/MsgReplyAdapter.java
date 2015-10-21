package com.cwl.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cwl.app.R;
import com.cwl.app.bean.MsgReplyBean;
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
import android.widget.TextView;

public class MsgReplyAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MsgReplyBean> mItems;
	private Context context;
	private LruCache<String, Bitmap> lruCache;

	public MsgReplyAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		lruCache = ImageCache.GetLruCache(context);
	}

	public void bindData(ArrayList<MsgReplyBean> item) {
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder viewHolder = null;
		final MsgReplyBean item = getItem(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_msg_reply, null);
			viewHolder.head = (ImageView) convertView
					.findViewById(R.id.img_msg_reply_head);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.tv_msg_reply_name);
			viewHolder.sex = (ImageView) convertView
					.findViewById(R.id.img_msg_reply_sex);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.tv_msg_reply_time);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.tv_msg_reply_content);
			viewHolder.what = (TextView) convertView
					.findViewById(R.id.tv_msg_reply_what);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

		}
		if (null != item) {
			viewHolder.head.setImageResource(R.drawable.default_avatar);
			if (item.getHead().equals("")) {
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
			viewHolder.time.setText(item.getTime());
			viewHolder.content.setText(item.getCnt());
			viewHolder.what.setText("评论了你的帖子：" + item.getNote().getContentStr());
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
		TextView time;
		TextView content;
		TextView what;
	}
}
