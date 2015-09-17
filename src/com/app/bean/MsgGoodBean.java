package com.app.bean;

import android.graphics.drawable.Drawable;

public class MsgGoodBean {

	private Drawable gdHead;
	private String gdName;
	private Drawable gdSex;
	private String gdTime;
	private String gdCnt;
	private Drawable gdImg;
	
	public MsgGoodBean(){}
	public MsgGoodBean(Drawable gdHead, String gdName,
			  Drawable gdSex, String gdTime, String gdCnt, Drawable gdImg){
		this.gdHead = gdHead;
		this.gdName = gdName;
		this.gdSex = gdSex;
		this.gdTime = gdTime;
		this.gdCnt = gdCnt;
		this.gdImg = gdImg;
		
	}
	
	public void setHead(Drawable gdHead){this.gdHead = gdHead;}
	public void setName(String gdName){this.gdName = gdName;}
	public void setSex(Drawable gdSex){this.gdSex = gdSex;}
	public void setTime(String gdTime){this.gdTime = gdTime;}
	public void setCnt(String gdCnt){this.gdCnt = gdCnt;}
	public void setImg(Drawable gdImg){this.gdImg = gdImg;}
	
	public Drawable getHead(){return this.gdHead;}
	public String getName(){return this.gdName;}
	public Drawable getSex(){return this.gdSex;}
	public String getTime(){return this.gdTime;}
	public String getCnt(){return this.gdCnt;}
	public Drawable getImg(){return this.gdImg;}
	
}
