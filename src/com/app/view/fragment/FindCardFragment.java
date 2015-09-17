package com.app.view.fragment;

import com.app.bean.FindCardBean;
import com.app.ui.FindCardDetailsActivity;
import com.app.ui.R;
import com.app.ui.R.id;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FindCardFragment extends Fragment implements OnClickListener{

	private View v;
	private FindCardBean mCardBean = null;
    private ImageView iv_card_img;
    private TextView tv_card_title;
    private TextView tv_card_cnt;
    private ImageView iv_card_avatar;
    private TextView tv_card_name;
    private TextView tv_card_cmt;
    private TextView tv_card_good;
    
    private View v_card_out;
    private ImageView iv_card_cmt;
    private ImageView iv_card_good;
    
    
    private int index = 0;
    
    private Intent intent = new Intent();
	public FindCardFragment() {
       
	}

	public FindCardFragment(int index){
		this.index = index;
	};
	
	public FindCardFragment(FindCardBean mCardBean) {
		this.mCardBean = mCardBean;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_find_card, null);
		initView();
		initData();
		return v;
	}
    
	public void initView(){
		
		iv_card_img = (ImageView) v.findViewById(R.id.cmv_card_img);
		tv_card_title = (TextView) v.findViewById(R.id.tv_card_title);
		tv_card_cnt = (TextView) v.findViewById(R.id.tv_card_cnt);
		iv_card_avatar = (ImageView) v.findViewById(R.id.iv_card_avatar);
		tv_card_name = (TextView) v.findViewById(R.id.tv_card_name);
		tv_card_cmt = (TextView) v.findViewById(R.id.tv_card_comment);
		tv_card_good = (TextView) v.findViewById(R.id.tv_card_good);
		iv_card_cmt = (ImageView) v.findViewById(R.id.img_card_coment);
		iv_card_good = (ImageView)v.findViewById(R.id.img_card_good);
		
		v_card_out = (View) v.findViewById(R.id.v_card_out);
		v_card_out.setOnClickListener(this);
		
		
	}
	
	public void initData(){
		
		if(mCardBean != null){
			iv_card_img.setImageDrawable(mCardBean.getImg());
			tv_card_title.setText(mCardBean.getTitle());
			tv_card_cnt.setText(mCardBean.getCnt());
			iv_card_avatar.setImageDrawable(mCardBean.getHead());
			tv_card_name.setText(mCardBean.getName());
			tv_card_cmt.setText(mCardBean.getCmt());
			tv_card_good.setText(mCardBean.getGood());
		}
		
	}

	public void jump2Detail(){
		intent.setClass(getActivity(), FindCardDetailsActivity.class);
	    Toast.makeText(getActivity(), String.valueOf(index), Toast.LENGTH_SHORT).show();
		startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {

		case R.id.img_card_good:
			break;
		case R.id.v_card_out:
			jump2Detail();
			break;
		default:
			break;
		}
	}
}
