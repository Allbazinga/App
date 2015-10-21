package com.cwl.app.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cwl.app.MyApplication;
import com.cwl.app.R;
import com.cwl.app.adapter.ChatHistoryAdapter;
import com.cwl.app.bean.MsgGoodBean;
import com.cwl.app.bean.MsgReplyBean;
import com.cwl.app.bean.User;
import com.cwl.app.client.HttpClientApi;
import com.cwl.app.db.UserDao;
import com.cwl.app.ui.ChatActivity;
import com.cwl.app.ui.MsgPraiseActivity;
import com.cwl.app.ui.MsgReplyActivity;
import com.cwl.app.utils.PreferenceUtils;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContact;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MsgFragment extends Fragment implements OnClickListener {

	private ListView ltv_msg;
	private RelativeLayout layout_msg_good, layout_msg_reply;
	private Map<String, User> contactList;
	private ChatHistoryAdapter adapter;
	private ArrayList<MsgGoodBean> praiselist = null;
	private ArrayList<MsgReplyBean> replylist = null;
	private TextView tv_msg_good_hint, tv_msg_reply_hint;
	private boolean isHidden = false;
	private boolean hidden;
	private static final int REFRESH_PRAISE_FAILED = 0;
	private static final int REFRESH_PRAISE_SUCCESS = 1;
	private static final int REFRESH_REPLY_FAILED = 2;
	private static final int REFRESH_REPLY_SUCCESS = 3;

	@SuppressLint("HandlerLeak")
	private Handler msgHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == REFRESH_PRAISE_FAILED) {
			} else if (msg.what == REFRESH_PRAISE_SUCCESS) {
				praiselist = (ArrayList<MsgGoodBean>) msg.obj;
				if(praiselist.size() > 0){
					tv_msg_good_hint.setText("又有" + String.valueOf(praiselist.size()) + "个人为你的帖子点赞了哦！");
				}
				refreshReply();
			} else if (msg.what == REFRESH_REPLY_FAILED) {

			} else if (msg.what == REFRESH_REPLY_SUCCESS) {
				replylist = (ArrayList<MsgReplyBean>) msg.obj;
				if(replylist.size()>0){
					tv_msg_reply_hint.setText(replylist.get(0).getName() + " 评论了你的帖子");
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("isMsgHidden")) {
				getFragmentManager().beginTransaction().hide(this).commit();
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ltv_msg = (ListView) getView().findViewById(R.id.ltv_msg);
		praiselist = new ArrayList<MsgGoodBean>();
		replylist = new ArrayList<MsgReplyBean>();
		addHeader();
		refreshPraise();
		contactList = (new UserDao(getActivity())).getContactList();
		Log.i("MsgFragment",
				"contactList is null " + String.valueOf(contactList == null));
		adapter = new ChatHistoryAdapter(getActivity(), 1,
				loadUsersWithRecentChat());
		Log.i("MsgFragment", String.valueOf(loadUsersWithRecentChat() == null));
		Log.i("MsgFragment", String.valueOf(adapter == null));
		ltv_msg.setAdapter(adapter);
		ltv_msg.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				EMContact emContact = adapter.getItem(position - 1);
				if (adapter.getItem(position - 1).getUsername()
						.equals(MyApplication.getInstance().getUser()))
					Toast.makeText(getActivity(), "不能和自己聊天", 0).show();
				else {
					User user = (new UserDao(getActivity())).getUser(emContact
							.getUsername());
					Intent intent = new Intent(getActivity(),
							ChatActivity.class);
					intent.putExtra("userId", emContact.getUsername());
					intent.putExtra("userName", user.getName());
					intent.putExtra("headerUrl", user.getHeader());
					startActivity(intent);
				}
			}

		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_msg, container, false);
	}

	@SuppressLint("InflateParams")
	public void addHeader() {
		LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		View headerView = lif.inflate(R.layout.headerview_msg, null);
		tv_msg_good_hint = (TextView) headerView.findViewById(R.id.tv_msg_good_hint);
		tv_msg_reply_hint = (TextView) headerView.findViewById(R.id.tv_msg_reply_hint);
		View footerView = lif.inflate(R.layout.footer_find, null);
		ltv_msg.addHeaderView(headerView, null, false);
		ltv_msg.addFooterView(footerView, null, false);
		layout_msg_good = (RelativeLayout) headerView
				.findViewById(R.id.layout_msg_good);
		layout_msg_reply = (RelativeLayout) headerView
				.findViewById(R.id.layout_msg_reply);
		layout_msg_good.setOnClickListener(this);
		layout_msg_reply.setOnClickListener(this);
	}

	private List<EMContact> loadUsersWithRecentChat() {
		List<EMContact> resultList = new ArrayList<EMContact>();
		for (User user : contactList.values()) {
			resultList.add(user);
		}
		for (EMGroup group : EMGroupManager.getInstance().getAllGroups()) {
			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(group.getGroupId());
			if (conversation.getMsgCount() > 0) {
				resultList.add(group);
			}
		}
		if (resultList.size() == 0) {
			Log.i("testout", "vacum");
		} else {
			Log.i("testouy", "not vacum");
		}
		return resultList;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.layout_msg_good:
			intent.setClass(getActivity(), MsgPraiseActivity.class);
			intent.putExtra("praiselist", praiselist);
			startActivity(intent);
			break;
		case R.id.layout_msg_reply:
			intent.setClass(getActivity(), MsgReplyActivity.class);
			intent.putExtra("replylist", replylist);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	public void refreshPraise() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = HttpClientApi.GetPraiseListApi(PreferenceUtils.getInstance(
						getActivity()).getSettingUserId());
				if(msg.obj != null){
					msg.what = REFRESH_PRAISE_SUCCESS;
					msgHandler.sendMessage(msg);
				}else{
					msgHandler.sendEmptyMessage(REFRESH_PRAISE_FAILED);
				}
			}
		}).start();
	}

	public void refreshReply() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = HttpClientApi.GetReplyListApi(PreferenceUtils.getInstance(
						getActivity()).getSettingUserId());
				if(msg.obj != null){
					msg.what = REFRESH_REPLY_SUCCESS;
					msgHandler.sendMessage(msg);
				}else{
					msgHandler.sendEmptyMessage(REFRESH_REPLY_FAILED);
				}
			}
		}).start();
	}

	public void refreshMsg() {
		contactList.clear();
		contactList = (new UserDao(getActivity())).getContactList();
		adapter = new ChatHistoryAdapter(getActivity(),
				R.layout.row_chat_history, loadUsersWithRecentChat());
		ltv_msg.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putBoolean("isMsgHidden", isHidden);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		isHidden = false;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if (!hidden) {
			refreshMsg();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!hidden) {
			refreshMsg();
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isHidden = true;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isHidden = true;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isHidden = true;
	}

}
