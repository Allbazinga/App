package com.app.bean;

import android.graphics.drawable.Drawable;

public class MineNoteBean {

	private Drawable img;
	private String cnt;
	private String time;
	private String label;
	public MineNoteBean(){}
	public MineNoteBean(Drawable img, String cnt, String time, String label){
		this.img = img;
		this.cnt = cnt;
		this.time = time;
		this.label = label;
	}
	
	public void setImg(Drawable img){
		this.img = img;
	}
	
	public void setCnt(String cnt){
		this.cnt = cnt;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	public Drawable getImg(){
		return this.img;
	}
	
	public String getCnt(){
		return this.cnt;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public String getLabel(){
		return this.label;
	}
}
