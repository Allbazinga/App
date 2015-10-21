package com.cwl.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cwl.app.R;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.bean.ReplyDetailBean;
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

public class ReplyDetailAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ReplyDetailBean> mItems;
	private LruCache<String, Bitmap> lruCache;
    private Context context;
	public ReplyDetailAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.lruCache = ImageCache.GetLruCache(context);
	}

	public void bindData(ArrayList<ReplyDetailBean> datas) {
		this.mItems = datas;
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
		final ReplyDetailBean item = getItem(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_home_detail, null);
			viewHolder.cmtHead = (ImageView) convertView
					.findViewById(R.id.img_home_detail_cmt_head);
			viewHolder.cmtName = (TextView) convertView
					.findViewById(R.id.tv_home_detail_cmt_name);
			viewHolder.cmtSex = (ImageView) convertView
					.findViewById(R.id.img_home_detail_cmt_sex);
			viewHolder.cmtTime = (TextView) convertView
					.findViewById(R.id.tv_home_detail_cmt_time);
			viewHolder.cmtContent = (TextView) convertView
					.findViewById(R.id.tv_home_detail_cmt_content);
			viewHolder.cmtFloor = (TextView) convertView
					.findViewById(R.id.tv_home_detail_cmt_floor);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (null != item) {
			viewHolder.cmtHead.setImageResource(R.drawable.default_avatar);
			if (!item.getHead().equals("")) {
				viewHolder.cmtHead.setTag(Constants.IP + item.getHead());
				new ImageCache(context, lruCache, viewHolder.cmtHead,
						Constants.IP + item.getHead(), "APP", 120, 120);
			}
			if(item.getSex().equals("女")){
				viewHolder.cmtSex.setImageResource(R.drawable.pic_female);
			}else{
				viewHolder.cmtSex.setImageResource(R.drawable.pic_male);
			}
			viewHolder.cmtName.setText(item.getName());
			viewHolder.cmtTime.setText(item.getTime());
			viewHolder.cmtContent.setText(item.getContent());
			viewHolder.cmtFloor.setText(String.valueOf(position+1) + "楼");
			viewHolder.cmtHead.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent((Activity)context, UserInfoActivity.class);
					intent.putExtra("userId", item.getCmtUserId());
					((Activity) context).startActivity(intent);
				}
			});
		}
        
		return convertView;
	}

	private static class ViewHolder {
		ImageView cmtHead;
		TextView cmtName;
		ImageView cmtSex;
		TextView cmtTime;
		TextView cmtContent;
		TextView cmtFloor;

	}
}
