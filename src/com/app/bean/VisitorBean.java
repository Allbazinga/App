package com.app.bean;

import android.graphics.drawable.Drawable;

public class VisitorBean {

	private Drawable avatar;
	private String name;
	private Drawable sex;

	public VisitorBean() {
	}

	public VisitorBean(Drawable avatar, String name, Drawable sex) {
		this.avatar = avatar;
		this.name = name;
		this.sex = sex;
	}

	public void setAvatar(Drawable avatar) {
		this.avatar = avatar;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(Drawable sex) {
		this.sex = sex;
	}

	public Drawable getAvatar() {
		return this.avatar;
	}

	public String getName() {
		return this.name;
	}

	public Drawable getSex() {
		return this.sex;
	}

}
