package com.cwl.app.bean;

public class MineMarkBean {

	private String id;
	private String avatar;
	private String name;
	private String scl;
	private String grade;
	private String sex;
	private String marked;

	public MineMarkBean() {
	}

	public MineMarkBean(String avatar, String id, String name, String scl, String grade,
			String sex, String marked) {
		this.id = id;
		this.avatar = avatar;
		this.name = name;
		this.scl = scl;
		this.sex = sex;
		this.grade = grade;
		this.marked = marked;
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

	public void setScl(String scl) {
		this.scl = scl;
	}

	public void setMarked(String marked) {
		this.marked = marked;
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

	public String getScl() {
		return this.scl;
	}

	public String getMarked() {
		return this.marked;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGrade() {
		return this.grade;
	}
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
}
