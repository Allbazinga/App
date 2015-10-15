package com.cwl.app.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cwl.app.MyApplication;
import com.cwl.app.R;
import com.cwl.app.adapter.ChatHistoryAdapter;
import com.cwl.app.bean.User;
import com.cwl.app.db.UserDao;
import com.cwl.app.ui.ChatActivity;
import com.cwl.app.ui.MsgGoodActivity;
import com.cwl.app.ui.MsgReplyActivity;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContact;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

public class MsgFragment extends Fragment implements OnClickListener {

	private ListView ltv_msg;
	private RelativeLayout layout_msg_good, layout_msg_reply;
	private Map<String, User> contactList;
	private User contactUser;
	private ChatHistoryAdapter adapter;
	private boolean isHidden = false;
	private boolean hidden;

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
		addHeader();
		contactList = (new UserDao(getActivity())).getContactList();
		Log.i("MsgFragment", "contactList is null " + String.valueOf(contactList == null));
		/*User user = new User();
		user.setUsername("souhoney");
		user.setNick("souhoney");
		contactList = new HashMap<String, User>();
		contactList.put(user.getUsername(), user);*/
		
		adapter = new ChatHistoryAdapter(getActivity(), 1,
				loadUsersWithRecentChat());
		// 设置adapter
		Log.i("MsgFragment", String.valueOf(loadUsersWithRecentChat() == null));
		Log.i("MsgFragment", String.valueOf(adapter == null));
		ltv_msg.setAdapter(adapter);
		ltv_msg.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				EMContact emContact = adapter.getItem(position-1);
				// if
				// (adapter.getItem(position).getUsername().equals(DemoApplication.getInstance().getUserName()))
				if (adapter.getItem(position-1).getUsername()
						.equals(MyApplication.getInstance().getUser()))
					Toast.makeText(getActivity(), "不能和自己聊天", 0).show();
				else {
					// 进入聊天页面
					Intent intent = new Intent(getActivity(),
							ChatActivity.class);
					if (emContact instanceof EMGroup) {
						// it is group chat
						intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
						intent.putExtra("groupId",
								((EMGroup) emContact).getGroupId());
					} else {
						// it is single chat
						intent.putExtra("userId", emContact.getUsername());
					}
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
		View footerView = lif.inflate(R.layout.footer_find, null);
		ltv_msg.addHeaderView(headerView, null, true);
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
		// 获取有聊天记录的users，不包括陌生人
		Log.i("testout", "test1");
		for (User user : contactList.values()) {
			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(user.getUsername());
			Log.i("testout", "test2");
			/*if (conversation.getMsgCount() > 0) {
				Log.i("testout", "test3");
				resultList.add(user);
			}*/
		    resultList.add(user);
		}
		for (EMGroup group : EMGroupManager.getInstance().getAllGroups()) {
			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(group.getGroupId());
			if (conversation.getMsgCount() > 0) {
				resultList.add(group);
			}
		}

		if(resultList.size() == 0){
			Log.i("testout", "vacum");
		}else{
			Log.i("testouy", "not vacum");
		}
		/*String name = resultList.get(0).getUsername();
		Log.i("testout", name);*/
		// 排序
		//sortUserByLastChatTime(resultList);
		return resultList;
	}

	/**
	 * 根据最后一条消息的时间排序
	 * 
	 * @param usernames
	 */
	private void sortUserByLastChatTime(List<EMContact> contactList) {
		Collections.sort(contactList, new Comparator<EMContact>() {
			@Override
			public int compare(final EMContact user1, final EMContact user2) {
				EMConversation conversation1 = EMChatManager.getInstance()
						.getConversation(user1.getUsername());
				EMConversation conversation2 = EMChatManager.getInstance()
						.getConversation(user2.getUsername());

				EMMessage user2LastMessage = conversation2.getLastMessage();
				EMMessage user1LastMessage = conversation1.getLastMessage();
				if (user2LastMessage.getMsgTime() == user1LastMessage
						.getMsgTime()) {
					return 0;
				} else if (user2LastMessage.getMsgTime() > user1LastMessage
						.getMsgTime()) {
					return 1;
				} else {
					return -1;
				}
			}

		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.layout_msg_good:
			intent.setClass(getActivity(), MsgGoodActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_msg_reply:
			intent.setClass(getActivity(), MsgReplyActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 刷新页面
	 */
	public void refresh() {
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
			refresh();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!hidden) {
			refresh();
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
