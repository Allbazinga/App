package com.cwl.app.bean;

import java.io.Serializable;

public class HomeBean implements Serializable {

	private static final long serialVersionUID = -1342518045017210258L;

	private String head;
	private String userId;
	private String noteId;
	private String name;
	private String sex;
	private String time;
	private String contentStr;
	private String contentImg;
	private String tag;
	private String comment;
	private String good;
	private String hasPraised;
	private String noteNum;//帖子个数
	
	public HomeBean() {
	}

	public HomeBean(String head, String userId, String noteId, String name,
			String sex, String time, String contentStr, String contentImg,
			String tag, String comment, String good, String hasPraised) {
		this.head = head;
		this.userId = userId;
		this.noteId = noteId;
		this.name = name;
		this.sex = sex;
		this.time = time;
		this.contentStr = contentStr;
		this.contentImg = contentImg;
		this.tag = tag;
		this.comment = comment;
		this.good = good;
		this.hasPraised = hasPraised;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setContentStr(String contentStr) {
		this.contentStr = contentStr;
	}

	public void setContentImg(String contentImg) {
		this.contentImg = contentImg;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getHead() {
		return this.head;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getNoteId() {
		return this.noteId;
	}

	public String getName() {
		return this.name;
	}

	public String getSex() {
		return this.sex;
	}

	public String getTime() {
		return this.time;
	}

	public String getContentStr() {
		return this.contentStr;
	}

	public String getContentImg() {
		return this.contentImg;
	}

	public String getTag() {
		return this.tag;
	}

	public String getComment() {
		return this.comment;
	}

	public String getGood() {
		return this.good;
	}

	public void setHasPraised(String hasPraised) {
		this.hasPraised = hasPraised;
	}

	public String getHasPraised() {
		return this.hasPraised;
	}
	
	public void setNoteNum(String noteNum){
		this.noteNum = noteNum;
	}
	
	public String getNoteNum(){
		return this.noteNum;
	}
}
