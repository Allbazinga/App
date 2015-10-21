package com.cwl.app.bean;

import java.io.Serializable;


public class MsgReplyBean implements Serializable{

	private static final long serialVersionUID = -1342518045017210258L;
	private String rpyHead;
	private String rpyName;
	private String rpySex;
	private String rpyTime;
	private String rpyCnt;
	private String rpyId;
	private String rpyUserId;
	private HomeBean note;
	
	public MsgReplyBean(){}
	public MsgReplyBean(String rpyHead, String rpyName,
			String rpySex, String rpyTime, String rpyCnt, String rpyWhat){
		this.rpyHead = rpyHead;
		this.rpyName = rpyName;
		this.rpySex = rpySex;
		this.rpyTime = rpyTime;
		this.rpyCnt = rpyCnt;
	}
	
	public void setHead(String rpyHead){this.rpyHead = rpyHead;}
	public void setName(String rpyName){this.rpyName = rpyName;}
	public void setSex(String rpySex){this.rpySex = rpySex;}
	public void setTime(String rpyTime){this.rpyTime = rpyTime;}
	public void setCnt(String rpyCnt){this.rpyCnt = rpyCnt;}
	public void setUserId(String rpyUserId){this.rpyUserId = rpyUserId;}
	public void setId(String rpyId){this.rpyId = rpyId;}
	public void setNote(HomeBean note){this.note = note;}
	
	
	public String getHead(){return this.rpyHead;}
	public String getName(){return this.rpyName;}
	public String getSex(){return this.rpySex;}
	public String getTime(){return this.rpyTime;}
	public String getCnt(){return this.rpyCnt;}
	public String getUserId(){return this.rpyUserId;}
	public String getId(){return this.rpyId;}
	public HomeBean getNote(){return this.note;}
	
}
