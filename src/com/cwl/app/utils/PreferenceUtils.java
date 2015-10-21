/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cwl.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

	/**
	 * 保存Preference的name
	 */
	public static final String PREFERENCE_NAME = "saveInfo";
	private static SharedPreferences mSharedPreferences;
	private static PreferenceUtils mPreferenceUtils;
	private static SharedPreferences.Editor editor;

	private String SHARED_KEY_SETTING_NOTIFICATION = "shared_key_setting_notification";
	private String SHARED_KEY_SETTING_SOUND = "shared_key_setting_sound";
	private String SHARED_KEY_SETTING_VIBRATE = "shared_key_setting_vibrate";
	private String SHARED_KEY_SETTING_SPEAKER = "shared_key_setting_speaker";

	/************ 设置用户信息 *************/
	private String SHARED_KEY_SETTING_USER_NAME = "shared_key_setting_user_name";// 昵称
	private String SHARED_KEY_SETTING_USER_ID = "shared_key_setting_user_id";// id
	private String SHARED_KEY_SETTING_USER_PWD = "shared_key_setting_user_pwd";// 密码
	private String SHARED_KEY_SETTING_USER_PIC = "shared_key_setting_user_pic";// 头像
	private String SHARED_KEY_SETTING_USER_SEX = "shared_key_setting_user_sex";// 性别
	private String SHARED_KEY_SETTING_USER_AGE = "shared_key_setting_user_age";// 年龄
	private String SHARED_KEY_SETTING_USER_AREA = "shared_key_setting_user_area";// 家乡
	private String SHARED_KEY_SETTING_USER_SIGN = "shared_key_setting_user_sign";// 签名
	private String SHARED_KEY_SETTING_USER_START_SCL = "shared_key_setting_user_start_scl";// 入学年份
	private String SHARED_KEY_SETTING_USER_MAJOR = "shared_key_setting_user_major";// 专业
	private String SHARED_KEY_SETTING_USER_SCL = "shared_key_setting_user_scl";// 学校

	private String SHARED_KEY_SETTING_USER_LOC = "shared_key_setting_user_loc";// 地址
	/**** ×*******设置加载用户性别 ****/
	private String SHARED_KEY_LOAD_SEX = "shared_key_load_sex";
	/**** ×*******设置按地区或者时间筛选 ****/
	private String SHARED_KEY_LOAD_TIME_LOC = "shared_key_load_time_loc";

	private PreferenceUtils(Context cxt) {
		// mSharedPreferences =
		// cxt.getSharedPreferences(DemoApplication.getInstance().getUser()+PREFERENCE_NAME,
		// Context.MODE_PRIVATE);
		mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 单例模式，获取instance实例
	 * 
	 * @param cxt
	 * @return
	 */
	public static PreferenceUtils getInstance(Context cxt) {
		if (mPreferenceUtils == null) {
			mPreferenceUtils = new PreferenceUtils(cxt);
		}
		editor = mSharedPreferences.edit();
		return mPreferenceUtils;
	}

	public void setSettingMsgNotification(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_NOTIFICATION, paramBoolean);
		editor.commit();
	}

	public boolean getSettingMsgNotification() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_NOTIFICATION,
				true);
	}

	public void setSettingMsgSound(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_SOUND, paramBoolean);
		editor.commit();
	}

	public boolean getSettingMsgSound() {

		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_SOUND, true);
	}

	public void setSettingMsgVibrate(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_VIBRATE, paramBoolean);
		editor.commit();
	}

	public boolean getSettingMsgVibrate() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_VIBRATE, true);
	}

	public void setSettingMsgSpeaker(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_SPEAKER, paramBoolean);
		editor.commit();
	}

	public boolean getSettingMsgSpeaker() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_SPEAKER, true);
	}

	/**
	 * 设置用户昵称
	 * 
	 * @param UserNickName
	 */
	public void setSettingUserName(String userName) {
		editor.putString(SHARED_KEY_SETTING_USER_NAME, userName);
		editor.commit();
	}

	/**
	 * 获取用户昵称
	 */
	public String getSettingUserName() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_NAME, "");
	}

	public void setSettingUserId(String userId) {
		editor.putString(SHARED_KEY_SETTING_USER_ID, userId);
		editor.commit();
	}

	public String getSettingUserId() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_ID, "");
	}

	public void setSettingUserPwd(String userPwd) {
		editor.putString(SHARED_KEY_SETTING_USER_PWD, userPwd);
		editor.commit();
	}

	public String getSettingsUserPwd() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_PWD, "");
	}

	public void setSettingUserSign(String sign) {
		editor.putString(SHARED_KEY_SETTING_USER_SIGN, sign);
		editor.commit();
	}

	public String getSettingUserSign() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_SIGN, "");
	}

	public void setSettingUserStartScl(String startScl) {
		editor.putString(SHARED_KEY_SETTING_USER_START_SCL, startScl);
		editor.commit();
	}

	public String getSettingUserStartScl() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_START_SCL,
				"");
	}

	public void setSettingUserScl(String scl) {
		editor.putString(SHARED_KEY_SETTING_USER_SCL, scl);
		editor.commit();
	}

	public String getSettingUserScl() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_SCL, "");
	}

	public void setSettingUserMajor(String major) {
		editor.putString(SHARED_KEY_SETTING_USER_MAJOR, major);
		editor.commit();
	}

	public String getSettingUserMajor() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_MAJOR, "");
	}

	/**
	 * 设置用户头像
	 * 
	 * @param UserPic
	 */
	public void setSettingUserPic(String UserPic) {
		editor.putString(SHARED_KEY_SETTING_USER_PIC, UserPic);
		editor.commit();
	}

	/**
	 * 获取用户头像
	 */
	public String getSettingUserPic() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_PIC, "");
	}

	/**
	 * 设置用户性别
	 * 
	 * @param UserSex
	 */
	public void setSettingUserSex(String UserSex) {
		editor.putString(SHARED_KEY_SETTING_USER_SEX, UserSex);
		editor.commit();
	}

	/**
	 * 获取用户性别
	 */
	public String getSettingUserSex() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_SEX, "女");
	}

	/**
	 * 设置用户年龄
	 * 
	 * @param UserAge
	 */
	public void setSettingUserAge(String UserAge) {
		editor.putString(SHARED_KEY_SETTING_USER_AGE, UserAge);
		editor.commit();
	}

	/**
	 * 获取用户年龄
	 */
	public String getSettingUserAge() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_AGE, "21");
	}

	/**
	 * 设置用户区域
	 * 
	 * @param UserArea
	 */
	public void setSettingUserArea(String UserArea) {
		editor.putString(SHARED_KEY_SETTING_USER_AREA, UserArea);
		editor.commit();
	}

	/**
	 * 获取用户区域
	 */
	public String getSettingUserArea() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_AREA, "");
	}

	/**
	 * 设置用户经纬度
	 * 
	 * @param UserZaina
	 */
	public void setSettingUserloc(String UserLoc) {
		editor.putString(SHARED_KEY_SETTING_USER_LOC, UserLoc);
		editor.commit();
	}

	/**
	 * 获取用户经纬度
	 */
	public String getSettingUserloc() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_LOC, "");
	}

	/**
	 * 设置筛选用户性别
	 * 
	 * @param UserZaina
	 */
	public void setloadsex(String loadsex) {
		editor.putString(SHARED_KEY_LOAD_SEX, loadsex);
		editor.commit();
	}

	/**
	 * 获取筛选用户性别
	 */
	public String getloadsex() {
		return mSharedPreferences.getString(SHARED_KEY_LOAD_SEX, "");
	}

	/**
	 * 设置按距离或者是时间筛选用户
	 * 
	 * @param UserZaina
	 */
	public void setloadtimeloc(String loadtimeloc) {
		editor.putString(SHARED_KEY_LOAD_TIME_LOC, loadtimeloc);
		editor.commit();
	}

	/**
	 * 获取按距离或者是时间筛选用户
	 */
	public String getloadtimeloc() {
		return mSharedPreferences.getString(SHARED_KEY_LOAD_TIME_LOC, "");
	}

}