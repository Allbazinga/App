package com.cwl.app.adapter;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.listener.ListItemOnClickListener;
import com.cwl.app.utils.ImageCache;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeHotAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<HomeBean> mItems;
	private Context context;
	private LruCache<String, Bitmap> lruCache;

	public HomeHotAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.lruCache = ImageCache.GetLruCache(context);
	}

	public void bindData(ArrayList<HomeBean> datas) {
		this.mItems = datas;
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
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_home, null);
			viewHolder.head = (ImageView) convertView
					.findViewById(R.id.img_home_hot_head);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.tv_home_hot_name);
			viewHolder.sex = (ImageView) convertView
					.findViewById(R.id.img_home_hot_sex);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.tv_home_hot_time);
			viewHolder.contentStr = (TextView) convertView
					.findViewById(R.id.tv_home_hot_content);
			viewHolder.contentImg = (ImageView) convertView
					.findViewById(R.id.img_home_hot_content);
			viewHolder.tag = (TextView) convertView
					.findViewById(R.id.tv_home_hot_tag);
			viewHolder.comment = (TextView) convertView
					.findViewById(R.id.tv_home_hot_comment);
			viewHolder.good = (TextView) convertView
					.findViewById(R.id.tv_home_hot_good);
			viewHolder.mark = (Button) convertView
					.findViewById(R.id.btn_home_hot_mark);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		HomeBean item = getItem(position);
		if (null != item) {
			/* viewHolder.head.setImageDrawable(item.getHead()); */
			viewHolder.name.setText(item.getName());
			if (item.getSex().equals("男")) {
				viewHolder.sex.setImageResource(R.drawable.pic_male);
			} else {
				viewHolder.sex.setImageResource(R.drawable.pic_female);
			}
			/* viewHolder.sex.setImageDrawable(item.getSex()); */
			viewHolder.time.setText(item.getTime());
			viewHolder.contentStr.setText(item.getContentStr());
			if (!item.getContentImg().equals("")) {
				viewHolder.contentImg.setVisibility(View.VISIBLE);

				viewHolder.contentImg.setTag(com.cwl.app.utils.Constants.IMAGEHOME
						+ item.getContentImg());
				viewHolder.contentImg
						.setImageResource(R.drawable.defaultcovers);
				new ImageCache(context, lruCache, viewHolder.contentImg,
						com.cwl.app.utils.Constants.IMAGEHOME
								+ item.getContentImg(), "App", 800, 320);

			} else {
				viewHolder.contentImg.setVisibility(View.GONE);
			}
			viewHolder.tag.setText(item.getTag());
			if (item.getComment().length() == 1) {
				viewHolder.comment.setText(" " + item.getComment());
			} else {
				viewHolder.comment.setText(item.getComment());
			}
			if (item.getGood().length() == 1) {
				viewHolder.good.setText(" " + item.getGood());
			} else {
				viewHolder.good.setText(item.getGood());
			}
			viewHolder.head.setOnClickListener(new ListItemOnClickListener(
					position, context));
			viewHolder.mark.setOnClickListener(new ListItemOnClickListener(
					position, context));
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
