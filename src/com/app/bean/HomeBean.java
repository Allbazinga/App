package com.app.bean;

import android.graphics.drawable.Drawable;

public class HomeBean {

	private Drawable head;
	private String name;
	private Drawable sex;
	private String time;
	private String contentStr;
	private Drawable contentImg;
	private String tag;
	private String comment;
	private String good;

	public HomeBean() {
	}

	public HomeBean(Drawable head, String name, Drawable sex, String time,
			String contentStr, Drawable contentImg, String tag, String comment,
			String good) {
		this.head = head;
		this.name = name;
		this.sex = sex;
		this.time = time;
		this.contentStr = contentStr;
		this.contentImg = contentImg;
		this.tag = tag;
		this.comment = comment;
		this.good = good;
	}

	public void setHead(Drawable head) {
		this.head = head;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(Drawable sex) {
		this.sex = sex;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setContentStr(String contentStr) {
		this.contentStr = contentStr;
	}

	public void setContentImg(Drawable contentImg) {
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

	public Drawable getHead() {
		return this.head;
	}

	public String getName() {
		return this.name;
	}

	public Drawable getSex() {
		return this.sex;
	}

	public String getTime() {
		return this.time;
	}

	public String getContentStr() {
		return this.contentStr;
	}

	public Drawable getContentImg() {
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
}
