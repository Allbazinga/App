package com.app.bean;

import android.graphics.drawable.Drawable;

public class MineCollectBean {

	private Drawable avatar;
	private String name;
	private Drawable sex;
	private String time;
	private String cnt;
	private Drawable img;

	public MineCollectBean() {
	}

	public MineCollectBean(Drawable avatar, String name, Drawable sex,
			String time, String cnt, Drawable img) {

		this.avatar = avatar;
		this.name = name;
		this.sex = sex;
		this.time = time;
		this.cnt = cnt;
		this.img = img;
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

	public void setTime(String time) {
		this.time = time;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public void setImg(Drawable img) {
		this.img = img;
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

	public String getTime() {
		return this.time;
	}

	public String getCnt() {
		return this.cnt;
	}

	public Drawable getImg() {
		return this.img;
	}
}
