package com.cwl.app.adapter;

import java.util.ArrayList;

import com.cwl.app.R;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.ui.ChatActivity;
import com.cwl.app.ui.UserInfoActivity;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;
import com.cwl.app.utils.PreferenceUtils;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<HomeBean> mItems;
	private Context context;
	private LruCache<String, Bitmap> lruCache;

	public HomeAdapter(Context context) {
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

	@SuppressLint("InflateParams")
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
			viewHolder.rlt_tag = (RelativeLayout) convertView
					.findViewById(R.id.rlt_tag);
			viewHolder.comment = (TextView) convertView
					.findViewById(R.id.tv_home_hot_comment);
			viewHolder.good = (TextView) convertView
					.findViewById(R.id.tv_home_hot_good);
			viewHolder.img_good = (ImageView) convertView
					.findViewById(R.id.img_home_hot_good);
			viewHolder.mark = (Button) convertView
					.findViewById(R.id.btn_home_hot_mark);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final HomeBean item = getItem(position);
		if (null != item) {
			/* viewHolder.head.setImageDrawable(item.getHead()); */
			viewHolder.name.setText(item.getName());
			if (item.getSex().equals("女")) {
				viewHolder.sex.setImageResource(R.drawable.pic_female);
			} else {
				viewHolder.sex.setImageResource(R.drawable.pic_male);
			}
			viewHolder.head.setImageResource(R.drawable.default_avatar);
			if (!item.getHead().equals("")) {
				viewHolder.head.setTag(Constants.IP + item.getHead());
				new ImageCache(context, lruCache, viewHolder.head, Constants.IP
						+ item.getHead(), "APP", 120, 120);
			}
			/* viewHolder.sex.setImageDrawable(item.getSex()); */
			viewHolder.time.setText(item.getTime());
			viewHolder.contentStr.setText(item.getContentStr());
			if (!item.getContentImg().equals("")) {
				viewHolder.contentImg.setVisibility(View.VISIBLE);

				viewHolder.contentImg.setTag(Constants.IP
						+ item.getContentImg());
				viewHolder.contentImg
						.setImageResource(R.drawable.defaultcovers);
				new ImageCache(context, lruCache, viewHolder.contentImg,
						Constants.IP + item.getContentImg(), "App", 800, 320);

			} else {
				viewHolder.contentImg.setVisibility(View.GONE);
			}

			if (!item.getTag().equals("")) {
				viewHolder.tag.setText(item.getTag());
			} else {
				viewHolder.tag.setText("火星");
			}
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
			viewHolder.img_good.setImageResource(R.drawable.pic_good_nor);
			if (item.getHasPraised().equals("true")) {
				viewHolder.img_good.setImageResource(R.drawable.pic_good_sel);
			}
			viewHolder.head.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, UserInfoActivity.class);
					intent.putExtra("userId", item.getUserId());
					context.startActivity(intent);
				}
			});
			viewHolder.mark.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (item.getUserId().equals(
							PreferenceUtils.getInstance(context)
									.getSettingUserId())) {
						Toast.makeText(context, "不能Yo自己哦~", Toast.LENGTH_SHORT)
								.show();
					} else {
						Intent intent = new Intent(context, ChatActivity.class);
						intent.putExtra("userId", item.getUserId());
						intent.putExtra("userName", item.getName());
						intent.putExtra("headerUrl", item.getHead());
						context.startActivity(intent);
					}
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
		TextView contentStr;
		ImageView contentImg;
		TextView tag;
		RelativeLayout rlt_tag;
		TextView comment;
		TextView good;
		ImageView img_good;
		Button mark;
	}
}
