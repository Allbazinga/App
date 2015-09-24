package com.app.listener;


import com.app.view.baseview.PullToRefreshLayout;
import com.app.view.baseview.PullToRefreshLayout.OnRefreshListener;

import android.os.Handler;
import android.os.Message;


public class MyListener implements OnRefreshListener
{

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 下拉刷新操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件刷新完毕了哦！
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 2000);
	}


}
