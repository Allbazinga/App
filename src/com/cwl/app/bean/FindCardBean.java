package com.cwl.app.bean;

import android.graphics.drawable.Drawable;

public class FindCardBean {

	private String cdImg;
	private String cdTitle;
	private String cdCnt;
	private String cdHead;
	private String cdName;
	private String cdCmt;
	private String cdGood;

	public FindCardBean() {

	}

	public FindCardBean(String cdImg, String cdTitle, String cdCnt,
			String cdHead, String cdName, String cdCmt, String cdGood) {
		this.cdImg = cdImg;
		this.cdCnt = cdCnt;
		this.cdTitle = cdTitle;
		this.cdHead = cdHead;
		this.cdName = cdName;
		this.cdCmt = cdCmt;
		this.cdGood = cdGood;
	}

	public void setImg(String cdImg) {
		this.cdImg = cdImg;
	}

	public void setTitle(String cdTitle) {
		this.cdTitle = cdTitle;
	}

	public void setCnt(String cdCnt) {
		this.cdCnt = cdCnt;

	}

	public void setHead(String cdHead) {
		this.cdHead = cdHead;
	}

	public void setName(String cdName) {
		this.cdName = cdName;
	}

	public void setCmt(String cdCmt) {
		this.cdCmt = cdCmt;
	}

	public void setGood(String cdGood) {
		this.cdGood = cdGood;
	}

	public String getImg() {
		return this.cdImg;
	}

	public String getTitle() {
		return this.cdTitle;
	}

	public String getCnt() {
		return this.cdCnt;
	}

	public String getHead() {
		return this.cdHead;
	}

	public String getName() {
		return this.cdName;
	}

	public String getCmt() {
		return this.cdCmt;
	}

	public String getGood() {
		return this.cdGood;
	}
}
