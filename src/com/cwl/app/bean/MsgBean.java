package com.cwl.app.bean;

import android.graphics.drawable.Drawable;

public class MsgBean {

	private Drawable head;
	private String name;
	private Drawable sex;
	private String time;
	private String content;

	public MsgBean() {
	}

	public MsgBean(Drawable head, String name, Drawable sex, String time,
			String content) {
		this.head = head;
		this.name = name;
		this.sex = sex;
		this.time = time;
		this.content = content;
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

	public void setContent(String content) {
		this.content = content;
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

	public String getContent() {
		return this.content;
	}

}
