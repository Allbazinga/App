package com.cwl.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.cwl.app.R;
import com.cwl.app.adapter.ReplyDetailAdapter;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.bean.ReplyDetailBean;
import com.cwl.app.utils.Constants;
import com.cwl.app.utils.ImageCache;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeLtvDetailsActivity extends Activity {

	private ListView mListView;
	private List<ReplyDetailBean> mDataList;
	private ReplyDetailAdapter mHomeDetailAdapter;
	private HomeBean noteData = null;
	private LruCache<String, Bitmap> lruCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homeltv_details);
		Intent intent = getIntent();
		noteData = (HomeBean) intent.getExtras().get("note");

		initView();
		initListView();
	}

	public void initView() {
	}

	public void initListView() {
		mListView = (ListView) findViewById(R.id.ltv_home_detail);
		mDataList = new ArrayList<ReplyDetailBean>();

		addHeader();

		ReplyDetailBean mBeam1 = new ReplyDetailBean("", "暗龙袭击", "", "4小时前", "1楼",
				"连三角架都带真是作死，不过看有护栏，应该是景区，估计也不是很难爬");
		ReplyDetailBean mBeam2 = new ReplyDetailBean("", "迷路的安娜", "", "昨天12:23", "2楼", "只为15字！");
		mDataList.add(mBeam2);
		mDataList.add(mBeam1);
		mDataList.add(mBeam2);
		mDataList.add(mBeam1);
		mHomeDetailAdapter = new ReplyDetailAdapter(this, mDataList);
		mListView.setAdapter(mHomeDetailAdapter);
	}

	public void addHeader() {
		lruCache = ImageCache.GetLruCache(this);
		LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View headerView = lif.inflate(R.layout.headerview_home_detail, null);
		ImageView avater = (ImageView) headerView
				.findViewById(R.id.img_home_hot_head);
		TextView name = (TextView) headerView
				.findViewById(R.id.tv_home_hot_name);
		ImageView sex = (ImageView) headerView
				.findViewById(R.id.img_home_hot_sex);
		TextView time = (TextView) headerView
				.findViewById(R.id.tv_home_hot_time);
		TextView cnt = (TextView) headerView
				.findViewById(R.id.tv_home_hot_content);
		TextView tag = (TextView) headerView
				.findViewById(R.id.tv_home_hot_tag);
		TextView good = (TextView) headerView
				.findViewById(R.id.tv_home_hot_good);
		ImageView cntImg = (ImageView) headerView
				.findViewById(R.id.img_home_hot_content);
		name.setText(noteData.getName());
		if (noteData.getSex().equals("男")) {
			sex.setImageResource(R.drawable.pic_male);
		} else {
			sex.setImageResource(R.drawable.pic_female);
		}
		time.setText(noteData.getTime());
		cnt.setText(noteData.getContentStr());
		tag.setText(noteData.getTag());
		good.setText(noteData.getGood());
		if (!noteData.getContentImg().equals("")) {
			cntImg.setTag(Constants.IMAGEHOME + noteData.getContentImg());
			new ImageCache(this, lruCache, cntImg, Constants.IMAGEHOME
					+ noteData.getContentImg(), "App", 800, 320);
		}else{
			cntImg.setVisibility(View.GONE);
		}
		
		mListView.addHeaderView(headerView);
	}
}
