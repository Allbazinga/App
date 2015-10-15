package com.cwl.app.bean;

import android.graphics.drawable.Drawable;

public class FindLabelBean {

	private int img;
	private String name;
	private String intruduce;
	private int isMarked;

	public FindLabelBean() {

	}

	public FindLabelBean(int img, String name, String intruduce,
			int isMarked) {
		this.img = img;
		this.name = name;
		this.intruduce = intruduce;
		this.isMarked = isMarked;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntruduce(String intruduce) {
		this.intruduce = intruduce;
	}

	public void setIsMarked(int isMarked) {
		this.isMarked = isMarked;
	}

	public int getImg() {
		return this.img;
	}

	public String getName() {
		return this.name;
	}

	public String getIntruduce() {
		return this.intruduce;
	}

	public int getIsMarked() {
		return this.isMarked;
	}
}
