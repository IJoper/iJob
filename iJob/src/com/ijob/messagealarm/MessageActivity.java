package com.ijob.messagealarm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ijob.activity.MyFocus;
import com.ijob.db.City_Item;
import com.ijob.db.DBHelper;
import com.ijob.db.Infor_item;
import com.ijob.db.Job_Item;

import android.R.bool;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MessageActivity extends Service {
	private MyBinder mBinder = new MyBinder();
	private int count;
	private String result;
	private DBHelper myDbHelper;
	private String URL = "http://1.iters.sinaapp.com/messages/getMessageById/";

	@Override
	public void onCreate() {
		super.onCreate();
		myDbHelper = new DBHelper(this);
		count = myDbHelper.getMaxMessageId() + 1;
		if (count < 111) {
			count = 111;
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		count = myDbHelper.getMaxMessageId() + 1;
		if (count < 111) {
			count = 111;
		}
		 Log.i("init count", ""+count);
		MyBinder myBinder = new MyBinder();
		myBinder.startDownload();

		// Log.i("message", "onCreate() executed");
		return super.onStartCommand(intent, flags, startId);
	}

	class MyBinder extends Binder {

		public void startDownload() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 执行具体的下载任务
					HttpGet httpGet = new HttpGet(URL + count + ".json");
					 Log.i("count", ""+count);
					result = "";
					try {
						HttpResponse response = new DefaultHttpClient()
								.execute(httpGet);
						// Log.i("response state = ", ""+
						// response.getStatusLine().getStatusCode());
						if (response.getStatusLine().getStatusCode() == 200) {
							StringBuffer buffer = new StringBuffer();
							BufferedReader bufferedReader = new BufferedReader(
									new InputStreamReader(response.getEntity()
											.getContent()));
							String data = "";
							while ((data = bufferedReader.readLine()) != null) {
								buffer.append(data);
							}
							result = buffer.toString();
							 Log.i("result = ", result.toString());
						}

					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						JSONparser(result);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	public void JSONparser(String result) throws JSONException {
		if (result.length() > 10) {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("job");
			Map<String, Object> map = new HashMap<String, Object>();

			List<City_Item> MyFocuscity = myDbHelper.getFocusCityList();
			List<Job_Item> MyFocusjob = myDbHelper.getFocusJobList();
			int i;
			boolean isMyMessage = false;
			String string = new String();
			for (i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				if (i == jsonArray.length() - 1) {
					string += jsonObject2.getString("type_id");
				} else {
					string += jsonObject2.getString("type_id") + "/";
				}
				if (MyFocuscity != null && MyFocuscity.size() > 0) {
					for (int j = 0; j < MyFocuscity.size(); j++) {
						if (MyFocuscity.get(j).getCityName()
								.equals(jsonObject2.getString("job_area"))) {
							isMyMessage = true;
						}
					}
				}else {
					isMyMessage = true;
				}
				if (MyFocusjob != null && MyFocusjob.size() > 0) {
					for (int j = 0; j < MyFocusjob.size(); j++) {
						if (MyFocusjob.get(j).getJobId() == Integer
								.parseInt(jsonObject2.getString("type_id"))) {
							isMyMessage = true;
						}
					}
				}else {
					isMyMessage = true;
				}
				if (MyFocuscity == null && MyFocusjob == null) {
					isMyMessage = true;
				}

			}
			Log.i("isMyMessage", "isMyMessage?");
			if (isMyMessage) {
				Log.i("isMyMessage", "isMyMessage!!!");
				myDbHelper.addMainListViewByItem(new Infor_item(Integer
						.parseInt(jsonObject.getString("id")), jsonObject
						.getString("message_title"), jsonObject
						.getString("company"), string, jsonObject
						.getString("location"), "5000-8000", "2014-04-02",
						"2014-04-06"));

				Bundle bundle = new Bundle();
				bundle.putString("message",
						jsonObject.getString("message_title"));
				Intent myIntent = new Intent(this, MyAlarm.class);
				myIntent.putExtras(bundle);
				startService(myIntent);
				isMyMessage = false;

			}
			if (jsonObject.getString("id") != null) {
				count++;
			}
		}
		// Toast.makeText(this, "check you internet connected !",
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Log.d("ondestroy", "onDestroy() executed");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		// Log.i("ddddd", "MessageActivity");

		return mBinder;
	}
}
