package com.ijob.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ijob.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Request extends Activity {
	private String urlString = "http://iters.sinaapp.com/messages/saveFeedback.json";
	private EditText editText;
	private Button button;
	String result;
	private Handler handler;
	Map<String, Object> map = new HashMap<String, Object>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.request);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		editText = (EditText) findViewById(R.id.request_editText1);
		button = (Button) findViewById(R.id.request_button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				String string = editText.getText().toString();
				if (string.length() < 1) {
					Toast.makeText(v.getContext(), "You Input Nothing!",
							Toast.LENGTH_SHORT).show();
				} else {
					Upload upload = new Upload(string);
					Thread thread = new Thread(upload);
					thread.start();
				}
			}
		});
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// Log.i("msg.what", Integer.toString(msg.what));
				switch (msg.what) {// ������ص�xml�ļ��Ѿ�������ϣ������յ��ź�msg.what=0x1f
				case 0x4f:
					Toast.makeText(Request.this, map.get("msg").toString(),Toast.LENGTH_SHORT).show();
				default:
					break;
				}
			}
		};
	}
	public class Upload implements Runnable{
		String string;
		public Upload(String string) {
			// TODO �Զ����ɵĹ��캯�����
			this.string = string;
		}
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			result = "";
			HttpPost httpPost = new HttpPost(urlString);// �������ڵ�ַ�д���
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("message", string));
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			} catch (UnsupportedEncodingException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			try {
				HttpResponse response = new DefaultHttpClient().execute(httpPost);
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
//					Log.i("Request result", result);
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(result);
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			try {
				map.put("msg", jsonObject.getString("msg"));
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}// ��Ϣ��ID
			Message message = new Message();
			message.what = 0x4f;
			handler.sendMessage(message);
		}
		
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

}