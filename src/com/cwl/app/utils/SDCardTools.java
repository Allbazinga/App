package com.cwl.app.utils;

import android.os.Environment;

/**
 * �ж�sdcard���Ĺ���״̬��·����ʣ������
 * 
 * @author Administrator
 * 
 */
public class SDCardTools {

	public SDCardTools() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * �ж�sdcard���Ƿ����
	 * 
	 * @return
	 */
	public static boolean isMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);// MEDIA_MOUNTED ý�����
	}

	/**
	 * ���sdcard����·��
	 * 
	 * @return
	 */
	public static String getSDcardRootPath() {
		if (isMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}
}
