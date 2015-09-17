package com.app.bean;

import android.graphics.drawable.Drawable;

public class ReplyDetailBean {

	private Drawable cmtHead;
	private String cmtName;
	private Drawable cmtSex;
	private String cmtTime;
	private String cmtFloor;
	private String cmtContent;
	public ReplyDetailBean(){}
	public ReplyDetailBean(Drawable cmtHead, String cmtName, Drawable cmtSex,
			String cmtTime, String cmtFloor, String cmtContent){
		this.cmtHead = cmtHead;
		this.cmtName = cmtName;
		this.cmtSex = cmtSex;
		this.cmtTime = cmtTime;
		this.cmtFloor = cmtFloor;
		this.cmtContent = cmtContent;
	}
	public void setHead(Drawable cmtHead){
		this.cmtHead = cmtHead;
	}
	public void setName(String cmtName){
		this.cmtName = cmtName;
	}
	public void setSex(Drawable cmtSex){
		this.cmtSex = cmtSex;
	}
	public void setTime(String cmtTime){
		this.cmtTime = cmtTime;
	}
	public void setFloor(String cmtFloor){
		this.cmtFloor = cmtFloor;
	}
	public void setContent(String cmtContent){
		this.cmtContent = cmtContent;
	}
	public Drawable getHead(){
		return this.cmtHead;
	}
	public String getName(){
		return this.cmtName;
	}
	public Drawable getSex(){
		return this.cmtSex;
	}
	public String getTime(){
		return this.cmtTime;
	}
	public String getFloor(){
		return this.cmtFloor;
	}
	public String getContent(){
		return this.cmtContent;
	}
}
