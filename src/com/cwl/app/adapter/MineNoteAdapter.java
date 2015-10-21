package com.cwl.app.adapter;

import java.util.List;

import com.cwl.app.R;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.bean.MineNoteBean;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MineNoteAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<HomeBean> mItems;
	private Context context;
	private LruCache<String, Bitmap> lruCache;

	public MineNoteAdapter(Context context) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.lruCache = ImageCache.GetLruCache(context);
	}

	public void bindData(List<HomeBean> mItems) {
		this.mItems = mItems;
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
		HomeBean item = getItem(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_mine_note, null);
			viewHolder.img = (ImageView) convertView
					.findViewById(R.id.iv_mine_note);
			viewHolder.cnt = (TextView) convertView
					.findViewById(R.id.tv_mine_note_cnt);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.tv_mine_note_time);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.tv_note_label);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (item != null) {
			if (item.getContentImg().equals("")) {
				viewHolder.img.setVisibility(View.GONE);
			} else {
				viewHolder.img.setVisibility(View.VISIBLE);
				viewHolder.img.setImageResource(R.drawable.defaultcovers);
				viewHolder.img.setTag(Constants.IP + item.getContentImg());
				new ImageCache(context, lruCache, viewHolder.img, Constants.IP
						+ item.getContentImg(), "APP", 120, 120);
				
			}
			viewHolder.cnt.setText(item.getContentStr());
			viewHolder.time.setText(item.getTime());
			if(item.getTag().equals("")){
				viewHolder.label.setText("来自：火星");
			}else{
				viewHolder.label.setText("来自：" + item.getTag());
			}
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView img;
		TextView cnt;
		TextView time;
		TextView label;
	}
}
