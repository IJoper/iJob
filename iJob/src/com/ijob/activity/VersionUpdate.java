package com.ijob.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ijob.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class VersionUpdate extends Activity {
	private String currentVersionString;
	private Map<String, Object> map = new HashMap<String, Object>();
	private TextView nowversion;
	private TextView newversion;
	private Button downloadButton;
	private String urlString = "http://iters.sinaapp.com/messages/getVersion.json";
	private Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.versionupdate);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		nowversion = (TextView) findViewById(R.id.now_textView1);
		newversion = (TextView) findViewById(R.id.new_textView2);
		downloadButton = (Button) findViewById(R.id.update_button1);
		try {
			currentVersionString = getPackageManager().getPackageInfo(
					getPackageName(), 0).versionName;
			Log.i("currentVersionString", currentVersionString);
		} catch (NameNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		DownloadRunnable downloadRunnable = new DownloadRunnable();
		Thread thread = new Thread(downloadRunnable);
		thread.start();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.i("msg.what", Integer.toString(msg.what));
				switch (msg.what) {// 如果下载的xml文件已经解析完毕，则会接收到信号msg.what=0x1f
				case 0xff:
					nowversion.setText(currentVersionString);
					newversion.setText(map.get("version").toString());
				default:
					break;
				}
			}
		};

		downloadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (currentVersionString.equals(map.get("version").toString())) {
					Toast.makeText(v.getContext(), "No Updating need!",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent("android.intent.action.VIEW",
							Uri.parse(map.get("link").toString()));
					startActivity(intent);
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public String HTTPGetInfo() {
		String result = "";
		HttpGet httpGet = new HttpGet(urlString);// 将参数在地址中传递

		try {
			HttpResponse response = new DefaultHttpClient().execute(httpGet);
			// Log.i("response state = ", ""+
			// response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				StringBuffer buffer = new StringBuffer();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
				String data = "";
				while ((data = bufferedReader.readLine()) != null) {
					buffer.append(data);
				}
				result = buffer.toString();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("result", result);
		return result;
	}

	// 后台线程解析下载的json格式文件
	class DownloadRunnable implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String httpresponse = new String();
			httpresponse = HTTPGetInfo();
			try {
				JSONparser(httpresponse);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

		public void JSONparser(String result) throws JSONException {
			if (result.length() > 0) {
				JSONObject jsonObject = new JSONObject(result);
				map.put("version", jsonObject.getString("version"));// 信息的ID
				map.put("link", jsonObject.getString("link"));// 信息标题
				Message message = new Message();
				message.what = 0xff;
				mHandler.sendMessage(message);
			}
		}
	}

}
