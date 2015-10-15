package com.cwl.app.client;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.cwl.app.bean.FindCardBean;
import com.cwl.app.bean.HomeBean;
import com.cwl.app.bean.MsgReplyBean;
import com.cwl.app.bean.ReplyDetailBean;
import com.cwl.app.bean.VisitorBean;

public class ClientApi {

	private static final String CHARSET = "utf-8"; // 设置编码
	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";

	public ClientApi() {

	}

	public static synchronized JSONObject ParseJson(final String path,
			final JSONObject data, final String encode) {
		// TODO Auto-generated method stub
		HttpClient httpClient = MyHttpClient.getHttpClient();
		HttpPost httpPost = new HttpPost(path);
		try {
			if (data != null) {
				Log.i("ClientApi", data.toString());
				StringEntity entity = new StringEntity(data.toString(),
						HTTP.UTF_8);
				/*
				 * entity.setContentEncoding("UTF-8");
				 * entity.setContentType("application/json");
				 */
				httpPost.setEntity(entity);
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity(),
						encode);
				Log.i("ClientApi", result);
				JSONObject jsonObject = new JSONObject(result);
				// JSONArray jsonArray = new JSONArray(result);
				return jsonObject;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			/*
			 * if (httpClient != null)
			 * httpClient.getConnectionManager().shutdown();
			 */
		}
		return null;

	}

	public static synchronized ArrayList<HomeBean> loadHomeData(String url) {

		HttpClient httpClient = MyHttpClient.getHttpClient();
		HttpGet method = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(method);
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				Log.i("ClientApi", result);
                JSONObject data = new JSONObject(result);
                ArrayList<HomeBean> list = new ArrayList<HomeBean>();
                JSONArray datas = data.getJSONArray("resultArray");
				for (int i = 0; i < datas.length(); i++) {
					JSONObject jsonObject = datas.getJSONObject(i);
					HomeBean homeBean = new HomeBean();
					homeBean.setHead(jsonObject.optString("userAvatar"));
					homeBean.setName(jsonObject.optString("userName"));
					homeBean.setUserId(jsonObject.optString("postUser"));
					homeBean.setNoteId(jsonObject.optString("postId"));
					homeBean.setSex(jsonObject.optString("userSex"));
					homeBean.setTime(jsonObject.optString("postTime"));
					homeBean.setContentImg(jsonObject.optString("postImage"));
					homeBean.setContentStr(jsonObject.optString("postContent"));
					homeBean.setTag(jsonObject.optString("postTag"));
					homeBean.setComment(jsonObject.optString("postComment"));
					homeBean.setGood(jsonObject.optString("postPraise"));
					list.add(homeBean);
				}
				return list;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			/*
			 * if (httpClient != null)
			 * httpClient.getConnectionManager().shutdown();
			 */
		}
		return null;
	}

	public static ArrayList<HomeBean> getHomeData(String Url,
			JSONObject postType) {

		JSONObject json = ParseJson(Url, postType, "utf-8");
		ArrayList<HomeBean> list = new ArrayList<HomeBean>();
		if (json == null) {
			return null;
		} else {
			try {
				JSONArray datas = json.getJSONArray("resultArray");
				for (int i = 0; i < datas.length(); i++) {
					JSONObject jsonObject = datas.getJSONObject(i);
					HomeBean homeBean = new HomeBean();
					homeBean.setHead(jsonObject.optString("userAvatar"));
					homeBean.setName(jsonObject.optString("userName"));
					homeBean.setUserId(jsonObject.optString("postUser"));
					homeBean.setNoteId(jsonObject.optString("postId"));
					homeBean.setSex(jsonObject.optString("userSex"));
					homeBean.setTime(jsonObject.optString("postTime"));
					homeBean.setContentImg(jsonObject.optString("postImage"));
					homeBean.setContentStr(jsonObject.optString("postContent"));
					homeBean.setTag(jsonObject.optString("postTag"));
					homeBean.setComment(jsonObject.optString("postComment"));
					homeBean.setGood(jsonObject.optString("postPraise"));
					list.add(homeBean);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("testout", Integer.toString(list.size()));
			return list;
		}
	}

	public static ArrayList<ReplyDetailBean> getReplyDetaiData(String Url,
			JSONObject postType) {

		JSONObject json = ParseJson(Url, postType, "utf-8");
		ArrayList<ReplyDetailBean> list = new ArrayList<ReplyDetailBean>();
		if (json == null) {
			return null;
		} else {
			try {
				JSONArray datas = json.getJSONArray("resultArray");
				for (int i = 0; i < datas.length(); i++) {
					JSONObject jsonObject = datas.getJSONObject(i);
					ReplyDetailBean replyDetailBean = new ReplyDetailBean();
					replyDetailBean.setHead(jsonObject.optString(""));
					replyDetailBean.setSex(jsonObject.optString(""));
					replyDetailBean.setTime(jsonObject.optString(""));
					replyDetailBean.setContent(jsonObject.optString(""));
					replyDetailBean.setName(jsonObject.optString(""));
					list.add(replyDetailBean);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
	}

	public static ArrayList<FindCardBean> getCardData(String Url,
			JSONObject postType) {
		JSONObject json = ParseJson(Url, postType, "utf-8");
		ArrayList<FindCardBean> list = new ArrayList<FindCardBean>();
		if (json == null) {
			return null;
		} else {
			try {
				JSONArray datas = json.getJSONArray("resultArray");
				for (int i = 0; i < datas.length(); i++) {
					JSONObject jsonObject = datas.getJSONObject(i);
					FindCardBean cardBean = new FindCardBean();
					cardBean.setHead(jsonObject.optString(""));
					cardBean.setName(jsonObject.optString(""));
					cardBean.setTitle(jsonObject.optString(""));
					cardBean.setCnt(jsonObject.optString(""));
					cardBean.setImg(jsonObject.optString(""));
					cardBean.setCmt(jsonObject.optString(""));
					cardBean.setGood(jsonObject.optString(""));
					list.add(cardBean);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
	}

	public static boolean postData(String Url, Map<String, String> data) {
		JSONObject jsonPost = new JSONObject(data);
		JSONObject json = ParseJson(Url, jsonPost, "utf-8");
		if (json == null) {
			return false;
		} else if (json.optInt("") == 1) {
			return true;
		}
		return false;
	}

	public static ArrayList<VisitorBean> getVisitorData(String Url) {

		JSONObject json = ParseJson(Url, null, "utf-8");
		ArrayList<VisitorBean> list = new ArrayList<VisitorBean>();
		if (json == null) {
			return null;
		} else {
			try {
				JSONArray datas = json.getJSONArray("resultArray");
				for (int i = 0; i < datas.length(); i++) {
					JSONObject jsonObject = datas.getJSONObject(i);
					VisitorBean visitorBean = new VisitorBean();
					visitorBean.setAvatar(jsonObject.optString(""));
					visitorBean.setName(jsonObject.optString(""));
					visitorBean.setSex(jsonObject.optString(""));
					list.add(visitorBean);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
	}

	public static ArrayList<MsgReplyBean> getMsgReplyData(String Url) {
		ArrayList<MsgReplyBean> list = new ArrayList<MsgReplyBean>();
		JSONObject json = ParseJson(Url, null, "utf-8");
		if (json == null) {
			return null;
		} else {
			try {
				JSONArray datas = json.getJSONArray("");
				for (int i = 0; i < datas.length(); i++) {
					JSONObject jsonObject = datas.getJSONObject(i);
					MsgReplyBean replyBean = new MsgReplyBean();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
	}

	public static String uploadSubmit(String url, Map<String, String> param,
			File file) throws Exception {
		System.out.println("11111");
		HttpPost post = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();
		MultipartEntity entity = new MultipartEntity();
		if (param != null && !param.isEmpty()) {
			for (Map.Entry<String, String> entry : param.entrySet()) {
				if (entry.getValue() != null
						&& entry.getValue().trim().length() > 0) {
					entity.addPart(
							entry.getKey(),
							new StringBody(
									entry.getValue(),
									Charset.forName(org.apache.http.protocol.HTTP.UTF_8)));
				}
			}
		}

		if (file != null && file.exists()) {
			FileBody body = new FileBody(file);
			entity.addPart("upfile", body);
			Log.i("testout", file.toString());
		}
		post.setEntity(entity);
		HttpResponse response = httpClient.execute(post);
		int stateCode = response.getStatusLine().getStatusCode();
		StringBuffer sb = new StringBuffer();
		String upLoadResult = null;
		System.out.println("222");
		if (stateCode == HttpStatus.SC_OK) {
			System.out.println("333");
			/*
			 * HttpEntity result = response.getEntity(); if (result != null) {
			 * InputStream is = result.getContent(); BufferedReader br = new
			 * BufferedReader(new InputStreamReader(is)); String tempLine; while
			 * ((tempLine = br.readLine()) != null) { sb.append(tempLine); } }
			 */
			String result = EntityUtils.toString(response.getEntity(), "utf-8");
			JSONObject responseJson = new JSONObject(result);
			if (responseJson != null) {
				upLoadResult = responseJson.optString("result");
			}
		}
		post.abort();
		return upLoadResult;
	}
}
