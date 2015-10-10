package com.app.bean;

import android.graphics.drawable.Drawable;

public class MineMarkBean {

	private Drawable avatar;
	private String name;
	private String scl;
	private String sex;
	private String marked;

	public MineMarkBean() {
	}

	public MineMarkBean(Drawable avatar, String name, String scl, String sex, String marked) {
		this.avatar = avatar;
		this.name = name;
		this.scl = scl;
		this.sex = sex;
		this.marked = marked;
	}

	public void setAvatar(Drawable avatar) {
		this.avatar = avatar;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex){
		this.sex = sex;
	}
	public void setScl(String scl){
		this.scl = scl;
	}
	public void setMarked(String marked){
		this.marked = marked;
	}
	public Drawable getAvatar() {
		return this.avatar;
	}

	public String getName() {
		return this.name;
	}
	public String getSex(){
		return this.sex;
	}
	public String getScl(){
		return this.scl;
	}
	public String getMarked(){
		return this.marked;
	}
}
