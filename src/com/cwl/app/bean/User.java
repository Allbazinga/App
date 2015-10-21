package com.cwl.app.bean;


import com.easemob.chat.EMContact;

public class User extends EMContact{
	private int unreadMsgCount;// 未读消息数量
	private String header;// 首字母
	private String headerurl;// 头像
	private String lasttime;// 最后登录时间
	private String is_stranger;// 是否是陌生人

	public String getIs_stranger() {
		return is_stranger;
	}

	public void setIs_stranger(String is_stranger) {
		this.is_stranger = is_stranger;
	}

	public String getHeaderurl() {
		return headerurl;
	}

	public void setHeaderurl(String headerurl) {
		this.headerurl = headerurl;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime){
		this.lasttime = lasttime;
	}
	/* ×××××××××××××××××××××××××××××××××××××××××× */

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}

	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}

	@Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof User)) {
			return false;
		}
		return getUsername().equals(((User) o).getUsername());
	}

	@Override
	public String toString() {
		return nick == null ? username : nick;
	}

	private String age;// 年龄
	private String sex;// 性别
	private String id;// id
	private String name;// 昵称
	private String sign;// 签名
	private String home;// 家乡
	private String major;// 专业
	private String grade;// 年级
	private String scl;// 学校
	private String hasConcerned;//是否被关注
	private String isLogin;

	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return this.sign;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getHome() {
		return this.home;
	}

	public void setScl(String scl) {
		this.scl = scl;
	}

	public String getScl() {
		return this.scl;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMajor() {
		return this.major;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public String getIsLogin() {
		return this.isLogin;
	}
	
	public void setHasConcerned(String hasConcerned){
		this.hasConcerned = hasConcerned;
	}
	public String getHasConcerned(){
		return this.hasConcerned;
	}

	public void clean() {
		this.unreadMsgCount = 0;// 未读消息数量
		this.header = "";// 首字母
		this.headerurl = "";// 头像
		this.age = "";// 年龄
		this.sex = "";// 性别
		this.id = "";
		this.name = "";
		this.scl = "";
		this.sign = "";
		this.grade = "";
		this.major = "";
		this.home = "";
		this.lasttime = "";// 最后登录时间
	}

}
