package com.cwl.app.bean;

import java.io.Serializable;


public class MsgGoodBean implements Serializable{

	private static final long serialVersionUID = -1342518045017210258L;
	private String gdHead;
	private String gdName;
	private String userId;
	private String gdId;
	private String gdSex;
	private String gdTime;
	private HomeBean note;

	public MsgGoodBean() {
	}

	public MsgGoodBean(String gdHead, String userId, String gdId, String gdName, String gdSex,
			String gdTime, HomeBean note) {
		this.gdHead = gdHead;
		this.userId = userId;
		this.gdId = gdId;
		this.gdName = gdName;
		this.gdSex = gdSex;
		this.gdTime = gdTime;
		this.note = note;
	}

	public void setHead(String gdHead) {
		this.gdHead = gdHead;
	}

	public void setId(String gdId){
		this.gdId = gdId;
	}
	
	public void setName(String gdName) {
		this.gdName = gdName;
	}

	public void setSex(String gdSex) {
		this.gdSex = gdSex;
	}

	public void setTime(String gdTime) {
		this.gdTime = gdTime;
	}

	public void setNote(HomeBean note){
		this.note = note;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	public String getHead() {
		return this.gdHead;
	}
	
	public String getId(){
		return this.gdId;
	}

	public String getName() {
		return this.gdName;
	}

	public String getSex() {
		return this.gdSex;
	}

	public String getTime() {
		return this.gdTime;
	}

	public HomeBean getNote(){
		return this.note;
	}

}
