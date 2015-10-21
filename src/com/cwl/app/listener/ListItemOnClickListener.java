package com.cwl.app.listener;

import com.cwl.app.ui.UserInfoActivity;
import com.cwl.app.R;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ListItemOnClickListener implements OnClickListener{

	private int position;
	private Context context;
	public ListItemOnClickListener(){
		
	}
	public ListItemOnClickListener(int position, Context context){
		this.position = position;
		this.context = context;
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.img_home_hot_head:
			intent.setClass(context, UserInfoActivity.class);
			context.startActivity(intent);
			break;
		case R.id.btn_home_hot_mark:
			//v.setVisibility(View.INVISIBLE);
			
			break;
		default:
			break;
		}
	}
	
	
}
