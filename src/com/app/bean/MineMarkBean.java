package com.app.bean;

import android.graphics.drawable.Drawable;

public class MineMarkBean {

	private Drawable avatar;
	private String name;

	public MineMarkBean() {
	}

	public MineMarkBean(Drawable avatar, String name) {
		this.avatar = avatar;
		this.name = name;
	}

	public void setAvatar(Drawable avatar) {
		this.avatar = avatar;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Drawable getAvatar() {
		return this.avatar;
	}

	public String getName() {
		return this.name;
	}
}
