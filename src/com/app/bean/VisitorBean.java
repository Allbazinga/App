package com.app.bean;

import android.graphics.drawable.Drawable;

public class VisitorBean {

	private String avatar;
	private String name;
	private String sex;

	public VisitorBean() {
	}

	public VisitorBean(String avatar, String name, String sex) {
		this.avatar = avatar;
		this.name = name;
		this.sex = sex;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public String getName() {
		return this.name;
	}

	public String getSex() {
		return this.sex;
	}

}
