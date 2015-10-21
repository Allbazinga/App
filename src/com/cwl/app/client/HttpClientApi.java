package com.cwl.app.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.cwl.app.bean.HomeBean;
import com.cwl.app.bean.MineMarkBean;
import com.cwl.app.bean.MsgGoodBean;
import com.cwl.app.bean.MsgReplyBean;
import com.cwl.app.bean.ReplyDetailBean;
import com.cwl.app.bean.User;

public class HttpClientApi {

	public static final String TAG = "HttpClientApi";
	public static final String BASE_URL = "http://202.118.76.72/App/action/";

	public static synchronized JSONObject ParseJson(String url,
			List<NameValuePair> params) {
		HttpClient httpClient = MyHttpClient.getHttpClient();
		HttpPost method = new HttpPost(url);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					HTTP.UTF_8);
			method.setEntity(entity);
			try {
				HttpResponse httpResponse = httpClient.execute(method);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					try {
						JSONObject result = new JSONObject(
								EntityUtils.toString(httpResponse.getEntity()));
						return result;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static synchronized String RegisterApi(String userName,
			String userPass) {
		String url = BASE_URL + "RegisterAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userName", userName));
		params.add(new BasicNameValuePair("userPass", userPass));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return "";
		}
		return json.optString("userId");
	}

	public static synchronized User LoginApi(String userId, String userPass) {
		String url = BASE_URL + "UserLoginAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		User user = new User();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("userPass", userPass));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		user.setIsLogin(json.optString("mesg"));
		JSONObject data;
		try {
			data = json.getJSONObject("data");
			user.setId(data.optString("userId"));
			user.setName(data.optString("userName"));
			user.setSex(data.optString("userSex"));
			user.setHeaderurl(data.optString("userImage"));
			user.setSign(data.optString("userNote"));
			user.setHome(data.optString("userHome"));
			user.setScl(data.optString("userSchool"));
			user.setGrade(data.optString("userGrade"));
			user.setMajor(data.optString("userMajor"));
			return user;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static synchronized ArrayList<HomeBean> GetHomeListApi(
			String userId, String postTime) {
		String url = BASE_URL + "PostListAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		ArrayList<HomeBean> list = new ArrayList<HomeBean>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("postTime", postTime));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		// Log.i(TAG, json.toString());
		try {
			JSONArray datas = json.getJSONArray("resultArray");
			for (int i = 0; i < datas.length(); i++) {
				JSONObject jsonObject = datas.getJSONObject(i);
				HomeBean homeBean = new HomeBean();
				homeBean.setHead(jsonObject.optString("userImage"));
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
				homeBean.setHasPraised(jsonObject.optString("hasPraised"));
				list.add(homeBean);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static synchronized boolean SetUserInfoApi(String userId,
			String field, String value) {
		String url = BASE_URL + "SetUserInfoAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("field", field));
		params.add(new BasicNameValuePair("value", value));
		JSONObject json = ParseJson(url, params);
		if (json != null && json.optString("result").equals("true")) {
			return true;
		}
		return false;
	}

	public static synchronized boolean PraiseApi(String userId, String postId) {
		String url = BASE_URL + "SetPraiseAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("postId", postId));
		JSONObject json = ParseJson(url, params);
		if (json != null && json.optString("result").equals("true")) {
			return true;
		}
		return false;
	}

	public static synchronized boolean CancelPraiseApi(String userId,
			String postId) {
		String url = BASE_URL + "CancelPraiseAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("postId", postId));
		JSONObject json = ParseJson(url, params);
		if (json != null && json.optString("result").equals("true")) {
			return true;
		}
		return false;
	}

	public static synchronized ArrayList<ReplyDetailBean> GetNoteReplyApi(
			String userId, String postId) {
		String url = BASE_URL + "PostCommentListAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		ArrayList<ReplyDetailBean> list = new ArrayList<ReplyDetailBean>();
		params.add(new BasicNameValuePair("postId", postId));
		params.add(new BasicNameValuePair("userId", userId));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		try {
			JSONObject noteJson = json.getJSONObject("post");
			HomeBean note = new HomeBean();
			note.setTag(noteJson.optString("postTag"));
			note.setContentImg(noteJson.optString("postImage"));
			note.setGood(noteJson.optString("postPraise"));
			note.setHasPraised(noteJson.optString("hasPraised"));
			note.setHead(noteJson.optString("userImage"));
			note.setSex(noteJson.optString("userSex"));
			note.setName(noteJson.optString("userName"));
			note.setComment(noteJson.optString("postComment"));
			note.setContentStr(noteJson.optString("postContent"));
			note.setNoteId(noteJson.optString("postId"));
			note.setTime(noteJson.optString("postTime"));
			note.setUserId(noteJson.optString("postUser"));
			ReplyDetailBean beanNote = new ReplyDetailBean();
			beanNote.setNote(note);
			list.add(beanNote);
			JSONArray datas = json.getJSONArray("resultArray");
			for (int i = 0; i < datas.length(); i++) {
				JSONObject data = datas.getJSONObject(i);
				ReplyDetailBean bean = new ReplyDetailBean();
				bean.setCmtUserId(data.optString("commentUser"));
				bean.setCmtId(data.optString("commentId"));
				bean.setTime(data.optString("commentTime"));
				bean.setContent(data.optString("commentContent"));
				bean.setSex(data.optString("userSex"));
				bean.setHead(data.optString("userImage"));
				bean.setName(data.optString("userName"));
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized boolean ReplyApi(String userId, String postId,
			String commentContent) {
		String url = BASE_URL + "SetCommentAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("postId", postId));
		params.add(new BasicNameValuePair("commentContent", commentContent));
		JSONObject json = ParseJson(url, params);
		if (json != null && json.optString("result").equals("true")) {
			return true;
		}
		return false;
	}

	public static synchronized ArrayList<HomeBean> GetListByTagApi(
			String userId, String postTag, String postTime) {
		String url = BASE_URL + "SearchByTagAction.php";
		ArrayList<HomeBean> list = new ArrayList<HomeBean>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("postTag", postTag));
		params.add(new BasicNameValuePair("postTime", postTime));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		try {
			JSONArray datas = json.getJSONArray("resultArray");
			for (int i = 0; i < datas.length(); i++) {
				JSONObject jsonObject = datas.getJSONObject(i);
				HomeBean homeBean = new HomeBean();
				homeBean.setHead(jsonObject.optString("userImage"));
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
				homeBean.setHasPraised(jsonObject.optString("hasPraised"));
				list.add(homeBean);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized ArrayList<HomeBean> UserNotesListApi(
			String userId, String visiterUser, String postTime) {
		String url = BASE_URL + "UserPostListAction.php";
		String noteNum = "";
		ArrayList<HomeBean> list = new ArrayList<HomeBean>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("visitUser", visiterUser));
		params.add(new BasicNameValuePair("postTime", postTime));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		noteNum = json.optString("postCount");
		try {
			JSONArray datas = json.getJSONArray("resultArray");
			for (int i = 0; i < datas.length(); i++) {
				JSONObject jsonObject = datas.getJSONObject(i);
				HomeBean bean = new HomeBean();
				bean.setHead(jsonObject.optString("userImage"));
				bean.setName(jsonObject.optString("userName"));
				bean.setUserId(jsonObject.optString("postUser"));
				bean.setNoteId(jsonObject.optString("postId"));
				bean.setSex(jsonObject.optString("userSex"));
				bean.setTime(jsonObject.optString("postTime"));
				bean.setContentImg(jsonObject.optString("postImage"));
				bean.setContentStr(jsonObject.optString("postContent"));
				bean.setTag(jsonObject.optString("postTag"));
				bean.setComment(jsonObject.optString("postComment"));
				bean.setGood(jsonObject.optString("postPraise"));
				bean.setHasPraised(jsonObject.optString("hasPraised"));
				bean.setNoteNum(noteNum);
				list.add(bean);
			}
			Log.i("testout", Integer.toString(list.size()));
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 优化建议：取消resultArray
	public static synchronized User GetUserInfoApi(String userId,
			String visiterUser) {
		String url = BASE_URL + "UserInfoAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("visitUser", visiterUser));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		User user = new User();
		user.setHasConcerned(json.optString("hasConcerned"));
		try {
			JSONObject data = json.getJSONArray("resultArray").getJSONObject(0);
			user.setId(data.optString("userId"));
			user.setName(data.optString("userName"));
			user.setSex(data.optString("userSex"));
			user.setScl(data.optString("userSchool"));
			user.setGrade(data.optString("userGrade"));
			user.setHome(data.optString("userHome"));
			user.setUsername(data.optString("userId"));
			user.setHeaderurl(data.optString("userImage"));
			user.setSign(data.optString("userNote"));
			user.setMajor(data.optString("userMajor"));
			return user;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized boolean SetConcernApi(String userId,
			String concernTarget) {
		String url = BASE_URL + "SetConcernAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("concernTarget", concernTarget));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return false;
		}
		if (json != null && json.optString("result").equals("true")) {
			return true;
		}
		return false;
	}

	public static synchronized boolean CancelConcernApi(String userId,
			String concernTarget) {
		String url = BASE_URL + "CancelConcernAction.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("concernTarget", concernTarget));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return false;
		}
		if (json != null && json.optString("result").equals("true")) {
			return true;
		}
		return false;
	}

	public static synchronized ArrayList<MineMarkBean> GetConcerndListApi(
			String userId) {
		String url = BASE_URL + "ConcernListAction.php";
		ArrayList<MineMarkBean> list = new ArrayList<MineMarkBean>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		try {
			JSONArray datas = json.getJSONArray("resultArray");
			for (int i = 0; i < datas.length(); i++) {
				JSONObject data = datas.getJSONObject(i);
				MineMarkBean bean = new MineMarkBean();
				bean.setId(data.optString("concernTarget"));
				bean.setAvatar(data.optString("userImage"));
				bean.setName(data.optString("userName"));
				bean.setSex(data.optString("userSex"));
				bean.setGrade(data.optString("userGrade"));
				bean.setScl(data.optString("userSchool"));
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized ArrayList<MsgReplyBean> GetReplyListApi(
			String userId) {
		String url = BASE_URL + "UserCommentListAction.php";
		ArrayList<MsgReplyBean> list = new ArrayList<MsgReplyBean>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		Log.i(TAG, json.toString());
		try {
			JSONArray datas = json.getJSONArray("resultArray");
			for (int i = 0; i < datas.length(); i++) {
				JSONObject data = datas.getJSONObject(i);
				MsgReplyBean bean = new MsgReplyBean();
				bean.setUserId(data.optString("commentUser"));
				bean.setName(data.optString("userName"));
				bean.setSex(data.optString("userSex"));
				bean.setHead(data.optString("userImage"));
				bean.setCnt(data.optString("commentContent"));
				bean.setTime(data.optString("commentTime"));
				bean.setId(data.optString("commentId"));
				HomeBean note = new HomeBean();
				note.setContentImg(data.optString("postImage"));
				note.setNoteId(data.optString("commentPost"));
				note.setContentStr(data.optString("postContent"));
				bean.setNote(note);
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized ArrayList<MsgGoodBean> GetPraiseListApi(
			String userId) {
		String url = BASE_URL + "UserPraiseListAction.php";
		ArrayList<MsgGoodBean> list = new ArrayList<MsgGoodBean>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", userId));
		JSONObject json = ParseJson(url, params);
		if (json == null) {
			return null;
		}
		try {
			JSONArray datas = json.getJSONArray("resultArray");
			for (int i = 0; i < datas.length(); i++) {
				JSONObject data = datas.getJSONObject(i);
				MsgGoodBean bean = new MsgGoodBean();
				bean.setHead(data.optString("userImage"));
				bean.setUserId(data.optString("praiseUser"));
				bean.setId(data.optString("praiseId"));
				bean.setName(data.optString("userName"));
				bean.setSex(data.optString("userSex"));
				bean.setTime(data.optString("praiseTime"));
				HomeBean note = new HomeBean();
				note.setContentStr(data.optString("postContent"));
				note.setTime(data.optString("postTime"));
				note.setNoteId(data.optString("praisePost"));
				note.setContentImg(data.optString("postImage"));
				bean.setNote(note);
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized boolean SetReplyReadAction(String commentId) {
		String url = BASE_URL + "SetCommentReadAction.php";
		return false;
	}

	public static synchronized boolean SetPraiseReadAction(String praiseId) {
		String url = BASE_URL + "SetPraiseReadAction.php";
		return false;
	}
}
