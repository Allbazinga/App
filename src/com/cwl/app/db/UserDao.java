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
package com.cwl.app.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.cwl.app.bean.User;
import com.cwl.app.utils.CommonUtils;
import com.cwl.app.utils.Constants;
import com.easemob.util.HanziToPinyin;

public class UserDao {
	public static final String TABLE_NAME = "users";
	public static final String COLUMN_NAME_ID = "username";
	public static final String COLUMN_NAME_NICK = "nick";
	public static final String COLUMN_HEAD_PIC = "headpic";
	public static final String COLUMN_NAME_IS_STRANGER = "is_stranger";//0是陌生人 2是好友  (1应该是关注的人,3是关注自己的)

	private DbOpenHelper dbHelper;

	public UserDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	public void saveContactList(List<User> contactList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(TABLE_NAME, null, null);
			for (User user : contactList) {
				ContentValues values = new ContentValues();
				values.put(COLUMN_NAME_ID, user.getId());
				values.put(COLUMN_HEAD_PIC, user.getHeaderurl());
				values.put(COLUMN_NAME_IS_STRANGER, "2");//0是陌生人 2是好友
				if(user.getName() != null)
					values.put(COLUMN_NAME_NICK, user.getName());
				db.insert(TABLE_NAME, null, values);
			}
		}
	}
	public Map<String, User> getContactList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, User> users = new HashMap<String, User>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +" WHERE " + COLUMN_NAME_IS_STRANGER +" = '2' "/* + " desc" */, null);
			while (cursor.moveToNext()) {
				String username = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID));
				String nick = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NICK));
				User user = new User();
				user.setUsername(username);
				user.setNick(nick);
				String headerName = null;
				if (!TextUtils.isEmpty(user.getNick())) {
					headerName = user.getNick();
				} else {
					headerName = user.getUsername();
				}
				
				if (username.equals(Constants.NEW_FRIENDS_USERNAME) || username.equals(Constants.GROUP_USERNAME)) {
					user.setHeader("");
				} else if (Character.isDigit(headerName.charAt(0))) {
					user.setHeader("#");
				} else {
					user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1))
							.get(0).target.substring(0, 1).toUpperCase());
					char header = user.getHeader().toLowerCase().charAt(0);
					if (header < 'a' || header > 'z') {
						user.setHeader("#");
					}
				}
				users.put(username, user);
			}
			cursor.close();
		}
		return users;
	}
	
	public void deleteContact(String userId){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			//System.out.println("==============================delete"+username);
			db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[]{userId});
		}
	}
	

	public void saveContact(User user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		Log.i("UserDao", user.getId());
		User user_tmp = getUser(user.getId());
		//判断是否有昵称和头像，有则删除原来的（这里没有判断原来是否存在）
		if(user_tmp.getId() != null){
			return;
		}
		/*if(!CommonUtils.isNullOrEmpty(user.getNick())||!CommonUtils.isNullOrEmpty(user.getHeaderurl())){
	 			db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[]{user.getUsername()});
	 			values.put(COLUMN_NAME_ID, user.getUsername()); 
				values.put(COLUMN_HEAD_PIC, user.getHeaderurl());
				values.put(COLUMN_NAME_IS_STRANGER, "2");//0是陌生人 2是好友
				values.put(COLUMN_NAME_NICK, user.getNick());
				if(db.isOpen()){
					db.insert(TABLE_NAME, null, values);
					Log.i("UserDao", "insert user succeed 1");
					
				}
		}else{*/
			values.put(COLUMN_NAME_ID, user.getId()); 
			values.put(COLUMN_HEAD_PIC, user.getHeaderurl());
			values.put(COLUMN_NAME_IS_STRANGER, "2");//0是陌生人 2是好友
			if(user.getName() != null)
				values.put(COLUMN_NAME_NICK, user.getName());
			if(db.isOpen()){
				db.insert(TABLE_NAME, null, values);
				Log.i("UserDao", "insert user succeed 2");
			} 
		//}
	}
	
	public User getUser(String userId) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		User user = new User();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +" WHERE " + COLUMN_NAME_ID +" = '"+userId+"'"/* + " desc" */, null);
			if (cursor.moveToFirst() == false) {
	       	 }else{
	       		//String username = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID));
				String nick = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NICK)); 
				String headpic = cursor.getString(cursor.getColumnIndex(COLUMN_HEAD_PIC)); 
				String is_stranger = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_IS_STRANGER)); 
				user.setUsername(userId);
				user.setName(nick);
				user.setHeaderurl(headpic);
				user.setIs_stranger(is_stranger);
	       	 }
			cursor.close();
		}
		return user;
	}
}
