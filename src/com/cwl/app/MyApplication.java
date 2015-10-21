package com.cwl.app;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.cwl.app.bean.User;
import com.cwl.app.db.DbOpenHelper;
import com.cwl.app.db.UserDao;
import com.cwl.app.ui.ChatActivity;
import com.cwl.app.ui.MainActivity;
import com.cwl.app.utils.PreferenceUtils;
import com.easemob.chat.ConnectionListener;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.OnNotificationClickListener;
import com.easemob.chat.EMMessage.ChatType;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {

	private static Context applicationContext;
	private static MyApplication instance;
	public final String PREF_USERNAME = "username";
	public final String PREF_USERNICKNAME = "usernickname";
	private String userName = null;
	private String userNickName = null;
	// login password
	private static final String PREF_PWD = "pwd";
	private String password = null;
	private Map<String, User> contactList;
		/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick="";

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		instance = this;
		initImageLoader(applicationContext);
		EMChat.getInstance().init(applicationContext);
		// debugmode设为true后，就能看到sdk打印的log了
		EMChat.getInstance().setDebugMode(false);

		// 获取到EMChatOptions对象
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		// 默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(true);
		// 设置收到消息是否有新消息通知，默认为true
		options.setNotificationEnable(PreferenceUtils.getInstance(
				applicationContext).getSettingMsgNotification());
		// 设置收到消息是否有声音提示，默认为true
		options.setNoticeBySound(PreferenceUtils
				.getInstance(applicationContext).getSettingMsgSound());
		// 设置收到消息是否震动 默认为true
		options.setNoticedByVibrate(PreferenceUtils.getInstance(
				applicationContext).getSettingMsgVibrate());
		// 设置语音消息播放是否设置为扬声器播放 默认为true
		options.setUseSpeaker(PreferenceUtils.getInstance(applicationContext)
				.getSettingMsgSpeaker());

		// 设置notification消息点击时，跳转的intent为自定义的intent
		options.setOnNotificationClickListener(new OnNotificationClickListener() {

			@Override
			public Intent onNotificationClick(EMMessage message) {
				Intent intent = new Intent(applicationContext,
						ChatActivity.class);
				ChatType chatType = message.getChatType();
				if (chatType == ChatType.Chat) { // 单聊信息
					intent.putExtra("userId", message.getFrom());
					intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
				} else { // 群聊信息
							// message.getTo()为群聊id
					intent.putExtra("groupId", message.getTo());
					intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
				}
				return intent;
			}
		});
		// 设置一个connectionlistener监听账户重复登陆
		EMChatManager.getInstance().addConnectionListener(
				new MyConnectionListener());
	}

	public Map<String, User> getContactList() {
		// if(getUserName() != null &&contactList == null)
		if (getUser() != null && contactList == null) {
			UserDao dao = new UserDao(applicationContext);
			// 获取本地好友user list到内存,方便以后获取好友list
			contactList = dao.getContactList();
		}
		return contactList;
	}

	/**
	 * 设置好友user list到内存中
	 * 
	 * @param contactList
	 */
	public void setContactList(Map<String, User> contactList) {
		this.contactList = contactList;
	}

	public void setStrangerList(Map<String, User> List) {

	}

	/**
	 * 获取当前huanxin登陆用户 加密字符串
	 * 
	 * @return
	 */
	public String getUser() {
		if (userName == null) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(applicationContext);
			userName = preferences.getString(PREF_USERNAME, null);
		}
		return userName;
	}

	/**
	 * 获取huanxin密码
	 * 
	 * @return
	 */
	public String getPassword() {
		if (password == null) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(applicationContext);
			password = preferences.getString(PREF_PWD, null);
		}
		return password;
	}

	/**
	 * 获取当前登陆用户昵称
	 * 
	 * @return
	 */
	public String getUsernNickName() {
		if (userNickName == null) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(applicationContext);
			userNickName = preferences.getString(PREF_USERNICKNAME, null);
		}
		return userNickName;
	}

	/**
	 * 设置用户昵称
	 * 
	 * @param user
	 */
	public void setUserNickName(String NickName) {
		if (NickName != null) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(applicationContext);
			SharedPreferences.Editor editor = preferences.edit();
			if (editor.putString(PREF_USERNICKNAME, NickName).commit()) {
				userNickName = NickName;
			}
		}
	}

	/**
	 * 设置huanxin用户名 加密字符串
	 * 
	 * @param user
	 */
	// public void setUserName(String username) {
	public void setUser(String username) {
		if (username != null) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(applicationContext);
			SharedPreferences.Editor editor = preferences.edit();
			if (editor.putString(PREF_USERNAME, username).commit()) {
				userName = username;
			}
		}
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 * 
	 * @param pwd
	 */
	public void setPassword(String pwd) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(applicationContext);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_PWD, pwd).commit()) {
			password = pwd;
		}
	}

	/**
	 * 退出登录,清空数据
	 */
	public void logout() {
		// 先调用sdk logout，在清理app中自己的数据
		EMChatManager.getInstance().logout();
		DbOpenHelper.getInstance(applicationContext).closeDB();
		setPassword(null);
		setContactList(null);

	}

	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
					.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm
							.getApplicationInfo(info.processName,
									PackageManager.GET_META_DATA));
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
			}
		}
		return processName;
	}

	public String getVersionName() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	public int getVersionCode()// 获取版本号(内部识别号)
	{
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
			return 0;
		}
	}

	public static void initImageLoader(Context context) {
		// 初始化ImageLoader
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	public static MyApplication getInstance() {
		return instance;
	}

	class MyConnectionListener implements ConnectionListener {
		@Override
		public void onReConnecting() {
		}

		@Override
		public void onReConnected() {
		}

		@Override
		public void onDisConnected(String errorString) {
			if (errorString != null && errorString.contains("conflict")) {
				Intent intent = new Intent(applicationContext,
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("conflict", true);
				startActivity(intent);
			}

		}

		@Override
		public void onConnecting(String progress) {

		}

		@Override
		public void onConnected() {
		}
	}
}
