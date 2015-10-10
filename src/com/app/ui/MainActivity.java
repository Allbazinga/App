package com.app.ui;

import java.util.ArrayList;

import com.app.view.fragment.FindFragment;
import com.app.view.fragment.HomeFragment;
import com.app.view.fragment.MineFragment;
import com.app.view.fragment.MsgFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private RelativeLayout layout_home, layout_find, layout_msg, layout_mine;
	private LinearLayout layout_main_content;
	private ImageView img_home, img_find, img_msg, img_mine;
	private TextView tv_home, tv_find, tv_msg, tv_mine;
	private FindFragment mFindFragment;
	private HomeFragment mHomeFragment;
	private MsgFragment mMsgFragment;
	private MineFragment mMineFragment;
	private Fragment mContent;
	private ArrayList<TextView> tvList;
	private ArrayList<ImageView> imgList;
	private int currIndex = 0;
	private long exitTimeCount = 0;
	private int[] picNors = { R.drawable.pic_home_nor, R.drawable.pic_find_nor,
			R.drawable.pic_msg_nor, R.drawable.pic_mine_nor, };
	private int[] picSels = { R.drawable.pic_home_sel, R.drawable.pic_find_sel,
			R.drawable.pic_msg_sel, R.drawable.pic_mine_sel, };

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initFragment();
	}

	public void initView() {

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		((LinearLayout) this.findViewById(R.id.layout_main_content))
				.setLayoutParams(lp);
		layout_home = (RelativeLayout) findViewById(R.id.layout_home);
		layout_find = (RelativeLayout) findViewById(R.id.layout_find);
		layout_msg = (RelativeLayout) findViewById(R.id.layout_msg);
		layout_mine = (RelativeLayout) findViewById(R.id.layout_mine);
		layout_home.setOnClickListener(this);
		layout_find.setOnClickListener(this);
		layout_msg.setOnClickListener(this);
		layout_mine.setOnClickListener(this);
		img_home = (ImageView) findViewById(R.id.img_home);
		img_find = (ImageView) findViewById(R.id.img_find);
		img_msg = (ImageView) findViewById(R.id.img_msg);
		img_mine = (ImageView) findViewById(R.id.img_mine);
		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_find = (TextView) findViewById(R.id.tv_find);
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		tv_mine = (TextView) findViewById(R.id.tv_mine);
		tvList = new ArrayList<TextView>();
		imgList = new ArrayList<ImageView>();
		tvList.add(tv_home);
		tvList.add(tv_find);
		tvList.add(tv_msg);
		tvList.add(tv_mine);
		imgList.add(img_home);
		imgList.add(img_find);
		imgList.add(img_msg);
		imgList.add(img_mine);

	}

	public void initFragment() {

		mHomeFragment = new HomeFragment();
		mFindFragment = new FindFragment();
		mMsgFragment = new MsgFragment();
		mMineFragment = new MineFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_main_content, mHomeFragment).commit();
		mContent = mHomeFragment;
	}

	public void switchContent(Fragment from, Fragment to) {
		if (mContent != to) {
			mContent = to;
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) {
				transaction.hide(from).add(R.id.layout_main_content, to)
						.commit();
			} else {
				transaction.hide(from).show(to).commit();
			}
		}
	}

	public void changeTabView(int from, int to) {

		if (currIndex != to) {
			currIndex = to;
			tvList.get(from).setTextColor(
					getResources().getColor(R.color.tv_main_bottom_nor));
			tvList.get(to).setTextColor(
					getResources().getColor(R.color.tv_main_bottom_sel));
			imgList.get(from).setBackgroundResource(picNors[from]);
			imgList.get(to).setBackgroundResource(picSels[to]);

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.layout_home:
			switchContent(mContent, mHomeFragment);
			changeTabView(currIndex, 0);
			break;
		case R.id.layout_find:
			switchContent(mContent, mFindFragment);
			changeTabView(currIndex, 1);
			break;
		case R.id.layout_msg:
			switchContent(mContent, mMsgFragment);
			changeTabView(currIndex, 2);
			break;
		case R.id.layout_mine:
			switchContent(mContent, mMineFragment);
			changeTabView(currIndex, 3);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (System.currentTimeMillis() - exitTimeCount >= 2000) {
				Toast.makeText(MainActivity.this, "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTimeCount = System.currentTimeMillis();
				return true;
			} else {
				MainActivity.this.finish();
				System.exit(0);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
