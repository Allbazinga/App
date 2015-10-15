package com.cwl.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import com.cwl.app.MyApplication;
import com.cwl.app.R;
import com.cwl.app.bean.User;
import com.cwl.app.db.UserDao;
import com.cwl.app.fragment.FindFragment;
import com.cwl.app.fragment.HomeFragment;
import com.cwl.app.fragment.MineFragment;
import com.cwl.app.fragment.MsgFragment;
import com.easemob.chat.ConnectionListener;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.util.NetUtils;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
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

	private NewMessageBroadcastReceiver msgReceiver;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initFragment();
		msgReceiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		intentFilter.setPriority(3);
		registerReceiver(msgReceiver, intentFilter);

		// 注册一个ack回执消息的BroadcastReceiver
		IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getAckMessageBroadcastAction());
		ackMessageIntentFilter.setPriority(3);
		registerReceiver(ackMessageReceiver, ackMessageIntentFilter);

		// 注册一个离线消息的BroadcastReceiver
		IntentFilter offlineMessageIntentFilter = new IntentFilter(
				EMChatManager.getInstance().getOfflineMessageBroadcastAction());
		registerReceiver(offlineMessageReceiver, offlineMessageIntentFilter);

		// setContactListener监听联系人的变化等
		// EMContactManager.getInstance().setContactListener(new
		// MyContactListener());
		// 注册一个监听连接状态的listener
		// EMChatManager.getInstance().addConnectionListener(new
		// MyConnectionListener());
		// 注册群聊相关的listener
		// EMGroupManager.getInstance().addGroupChangeListener(new
		// MyGroupChangeListener());
		// 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
		EMChat.getInstance().setAppInited();
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

	/**
	 * 新消息广播接收者
	 */
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 消息id
			String msgId = intent.getStringExtra("msgid");
			final String toChatUsername = intent.getStringExtra("from");
			final UserDao userdao = new UserDao(context);
			final User local_user = userdao.getUser(toChatUsername);
			// 判断本地是否存在

			get_add_info(toChatUsername);
			if (currIndex == 2) {
				// 当前页面如果为聊天历史页面，刷新此页面
				if (mMsgFragment != null) {
					mMsgFragment.refresh();
				}
			}
			// 注销广播，否则在ChatActivity中会收到这个广播
			
			abortBroadcast();
		}
	}

	/**
	 * 消息回执BroadcastReceiver
	 */
	private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String msgid = intent.getStringExtra("msgid");
			String from = intent.getStringExtra("from");
			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(from);
			if (conversation != null) {
				// 把message设为已读
				EMMessage msg = conversation.getMessage(msgid);
				if (msg != null) {
					msg.isAcked = true;
				}
			}
			abortBroadcast();
		}
	};

	/**
	 * 离线消息BroadcastReceiver sdk 登录后，服务器会推送离线消息到client，这个receiver，是通知UI
	 * 有哪些人发来了离线消息 UI 可以做相应的操作，比如下载用户信息
	 */
	private BroadcastReceiver offlineMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String[] users = intent.getStringArrayExtra("fromuser");
			String[] groups = intent.getStringArrayExtra("fromgroup");
			if (users != null) {
				for (String user : users) {
					// Log.i("收到user离线消息：" + user);

					get_add_info(user);

				}
			}
			if (groups != null) {
				for (String group : groups) {
					// Log.d("收到group离线消息：" + group);
				}
			} 
			abortBroadcast();
		}
	};

	public void get_add_info(String username) {
		UserDao userDao = new UserDao(this);
		Log.i("MainActivity", "no such a contactor");
		User user = new User();
		user.setUsername(username);
		userDao.saveContact(user);
		//userDao.saveContactList(users);
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 注销广播接收者
		try {
			unregisterReceiver(msgReceiver);
		} catch (Exception e) {
		}
		try {
			unregisterReceiver(ackMessageReceiver);
		} catch (Exception e) {
		}
		try {
			unregisterReceiver(offlineMessageReceiver);
		} catch (Exception e) {
		}
	}
}
