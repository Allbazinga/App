package com.app.bean;

import android.graphics.drawable.Drawable;

public class MineCardBean {

	private Drawable img;
	private String title;
	private String cnt;
	private String good;
	private String cmt;

	public MineCardBean() {

	}

	public MineCardBean(Drawable img, String title, String cnt, String cmt,
			String good) {

		this.img = img;
		this.title = title;
		this.cnt = cnt;
		this.cmt = cmt;
		this.good = good;
	}

	public void setImg(Drawable img) {
		this.img = img;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public Drawable getImg() {
		return this.img;
	}

	public String getTitle() {
		return this.title;
	}

	public String getCnt() {
		return this.cnt;
	}

	public String getCmt(){
		return this.cmt;
	}
	public String getGood(){
		return this.good;
	}
}
