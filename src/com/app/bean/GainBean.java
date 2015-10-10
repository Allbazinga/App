package com.app.bean;

import android.graphics.drawable.Drawable;

public class GainBean {

	private Drawable img;
	private String title;
	private String cnt;
	private String num;
	private String finish;

	public GainBean() {

	}

	public GainBean(Drawable img, String title, String cnt, String num, String finish) {

		this.img = img;
		this.title = title;
		this.cnt = cnt;
		this.num = num;
		this.finish = finish;
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

	public void setNum(String num) {
		this.num = num;
	}

	public void setFinish(String finish) {
		this.finish = finish;
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

	public String getNum(){
		return this.num;
	}
	public String getFinish(){
		return this.finish;
	}
}
