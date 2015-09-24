package com.app.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class MyHttpClient {

	/*
	 * @author allbazinga;
	 * HttpClient 的优化
	 * 
	 * */
	public static HttpClient httpClient = null;
	
	public static synchronized HttpClient getHttpClient(){
		
		if(httpClient == null){
			HttpParams params = new BasicHttpParams();
			httpClient = new DefaultHttpClient(null, null);
		}
		
		return httpClient;
	}
}
