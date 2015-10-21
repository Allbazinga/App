package com.cwl.app.bean;

public class ReplyDetailBean {

	private String cmtHead;
	private String cmtUserId;
	private String cmtName;
	private String cmtId;
	private String cmtSex;
	private String cmtTime;
	private String cmtFloor;
	private String cmtContent;
	private HomeBean note;

	public ReplyDetailBean() {

	}

	public ReplyDetailBean(String cmtHead, String cmtUserId, String cmtName,
			String cmtId, String cmtSex, String cmtTime, String cmtFloor,
			String cmtContent) {
		this.cmtHead = cmtHead;
		this.cmtUserId = cmtUserId;
		this.cmtName = cmtName;
		this.cmtId = cmtId;
		this.cmtSex = cmtSex;
		this.cmtTime = cmtTime;
		this.cmtFloor = cmtFloor;
		this.cmtContent = cmtContent;
	}

	public void setHead(String cmtHead) {
		this.cmtHead = cmtHead;
	}

	public void setName(String cmtName) {
		this.cmtName = cmtName;
	}

	public void setSex(String cmtSex) {
		this.cmtSex = cmtSex;
	}

	public void setTime(String cmtTime) {
		this.cmtTime = cmtTime;
	}

	public void setFloor(String cmtFloor) {
		this.cmtFloor = cmtFloor;
	}

	public void setContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}

	public String getHead() {
		return this.cmtHead;
	}

	public String getName() {
		return this.cmtName;
	}

	public String getSex() {
		return this.cmtSex;
	}

	public String getTime() {
		return this.cmtTime;
	}

	public String getFloor() {
		return this.cmtFloor;
	}

	public String getContent() {
		return this.cmtContent;
	}

	public void setCmtUserId(String cmtUserId) {
		this.cmtUserId = cmtUserId;
	}

	public String getCmtUserId() {
		return this.cmtUserId;
	}

	public void setCmtId(String cmtId) {
		this.cmtId = cmtId;
	}

	public String getCmtId() {
		return this.cmtId;
	}

	public void setNote(HomeBean note) {
		this.note = note;
	}

	public HomeBean getNote() {
		return this.note;
	}
}
