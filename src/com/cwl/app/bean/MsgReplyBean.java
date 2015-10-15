package com.cwl.app.bean;

import android.graphics.drawable.Drawable;

public class MsgReplyBean {

	private Drawable rpyHead;
	private String rpyName;
	private Drawable rpySex;
	private String rpyTime;
	private String rpyCnt;
	private String rpyWhat;
	
	public MsgReplyBean(){}
	public MsgReplyBean(Drawable rpyHead, String rpyName,
			  Drawable rpySex, String rpyTime, String rpyCnt, String rpyWhat){
		this.rpyHead = rpyHead;
		this.rpyName = rpyName;
		this.rpySex = rpySex;
		this.rpyTime = rpyTime;
		this.rpyCnt = rpyCnt;
        this.rpyWhat = rpyWhat;		
	}
	
	public void setHead(Drawable rpyHead){this.rpyHead = rpyHead;}
	public void setName(String rpyName){this.rpyName = rpyName;}
	public void setSex(Drawable rpySex){this.rpySex = rpySex;}
	public void setTime(String rpyTime){this.rpyTime = rpyTime;}
	public void setCnt(String rpyCnt){this.rpyCnt = rpyCnt;}
	public void setWhat(String rpyWhat){this.rpyWhat = rpyWhat;}
	
	public Drawable getHead(){return this.rpyHead;}
	public String getName(){return this.rpyName;}
	public Drawable getSex(){return this.rpySex;}
	public String getTime(){return this.rpyTime;}
	public String getCnt(){return this.rpyCnt;}
	public String getWhat(){return this.rpyWhat;}
	
}
