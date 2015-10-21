package com.cwl.app.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
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
			//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.
                                CONNECTION_TIMEOUT, 5000);
            
            //设置读取超时
            httpClient.getParams().setParameter(
                                CoreConnectionPNames.SO_TIMEOUT, 5000);
            
		}
		
		return httpClient;
	}
}
