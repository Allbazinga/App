package com.app.ui;

import java.util.ArrayList;

import com.app.adapter.MineNoteAdapter;
import com.app.bean.MineNoteBean;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class MyNoteActivity extends Activity {

	private ListView ltv_other_info;
	private MineNoteAdapter adapter = null;
	private ArrayList<MineNoteBean> datalist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		initView();
	}

	public void initView() {
		((TextView) this.findViewById(R.id.tv_actionbar)).setText(R.string.note);
		ltv_other_info = (ListView) findViewById(R.id.ltv_my);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View headerview = inflater.inflate(R.layout.headerview_my_note, null);
		ltv_other_info.addHeaderView(headerview, null, true);

		datalist = new ArrayList<MineNoteBean>();
		MineNoteBean bean1 = new MineNoteBean(null,
				"我们被教导要记住思想，而不是人，因为人可能会失败，被杀死或是被遗忘，但是思想不会，不会不会不会不会不会不会",
				"2015年9月28日", "文青聚集地");
		datalist.add(bean1);
		MineNoteBean bean2 = new MineNoteBean(getResources().getDrawable(
				R.drawable.pic_test1), "我发誓，我真的活过", "2015年10月9日", "文青聚集地");
		for (int i = 0; i < 4; i++) {
			datalist.add(bean2);
		}
		adapter = new MineNoteAdapter(this, datalist);
		ltv_other_info.setAdapter(adapter);
	}
}
