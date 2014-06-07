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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ijob.R;
import com.ijob.db.Profile_Item;
import com.ijob.db.RDBmanager;

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

public class MyProfile extends Activity {
	Map<String, Object> map = new HashMap<String, Object>();
	private Handler handler;
	private Profile_Item item;
	private RDBmanager mydb;
	private String jobId;
	private String uploadProfile_URL = "http://iters.sinaapp.com/messages/saveResume.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mydb = new RDBmanager(MyProfile.this);
		setContentView(R.layout.input_message);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);;
		this.setTitle("简历填写");

		jobId = this.getIntent().getExtras().getString("messageId").toString();

		// 获得保存在数据库中的数据，预先填写已经保存过的内容。
		Profile_Item userData = mydb.find(1);
		((EditText) findViewById(R.id.EditName)).setText(userData.getName());
		((EditText) findViewById(R.id.EditSex)).setText(userData.getSex());
		((EditText) findViewById(R.id.EditAge)).setText(userData.getAge());
		((EditText) findViewById(R.id.EditEdu)).setText(userData
				.getEduBackground());
		((EditText) findViewById(R.id.EditSchool))
				.setText(userData.getSchool());
		((EditText) findViewById(R.id.EditPur)).setText(userData.getPurpose());
		((EditText) findViewById(R.id.EditPhone)).setText(userData
				.getPhoneNumber());
		((EditText) findViewById(R.id.EditQQ)).setText(userData.getQQ());
		((EditText) findViewById(R.id.EditE_mail))
				.setText(userData.getE_mail());
		((EditText) findViewById(R.id.EditAddr)).setText(userData.getAddress());
		((EditText) findViewById(R.id.EditIntr)).setText(userData.getIntro());
		((EditText) findViewById(R.id.EditExp)).setText(userData.getExep());
		((EditText) findViewById(R.id.EditSkill)).setText(userData.getSkill());
		((EditText) findViewById(R.id.EditOther)).setText(userData.getOther());

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// Log.i("msg.what", Integer.toString(msg.what));
				switch (msg.what) {// 如果下载的xml文件已经解析完毕，则会接收到信号msg.what=0x1f
				case 0x3f:
					Toast.makeText(MyProfile.this, map.get("msg").toString(),Toast.LENGTH_SHORT).show();
				default:
					break;
				}
			}
		};
	}

	public void saveProfile() {
		int id = 1;
		String name, sex, age, eduBackground, school, purpose, phoneNumber, QQ, E_mail, address, intro, exep, skill, other;

		name = ((EditText) findViewById(R.id.EditName)).getText()
				.toString();
		sex = ((EditText) findViewById(R.id.EditSex)).getText()
				.toString();
		age = ((EditText) findViewById(R.id.EditAge)).getText()
				.toString();
		eduBackground = ((EditText) findViewById(R.id.EditEdu))
				.getText().toString();
		school = ((EditText) findViewById(R.id.EditSchool)).getText()
				.toString();
		purpose = ((EditText) findViewById(R.id.EditPur)).getText()
				.toString();
		phoneNumber = ((EditText) findViewById(R.id.EditPhone))
				.getText().toString();
		QQ = ((EditText) findViewById(R.id.EditQQ)).getText()
				.toString();
		E_mail = ((EditText) findViewById(R.id.EditE_mail)).getText()
				.toString();
		address = ((EditText) findViewById(R.id.EditAddr)).getText()
				.toString();
		intro = ((EditText) findViewById(R.id.EditIntr)).getText()
				.toString();
		exep = ((EditText) findViewById(R.id.EditExp)).getText()
				.toString();
		skill = ((EditText) findViewById(R.id.EditSkill)).getText()
				.toString();
		other = ((EditText) findViewById(R.id.EditOther)).getText()
				.toString();

		Profile_Item newItem = new Profile_Item(id, name, sex, age,
				eduBackground, school, purpose, phoneNumber, QQ,
				E_mail, address, intro, exep, skill, other);
		if (mydb.count() == 0)
			mydb.add(newItem);
		else
			mydb.update(newItem);
	}
	public void submitProfile() {
		Profile_Item newItem = mydb.find(1);
		if (jobId.equals("-1")) {
			Toast.makeText(this,
					"Failed To Post Your Profile!", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (mydb.count() == 0) {
				Toast.makeText(this, "No Profile Find!",
						Toast.LENGTH_SHORT).show();
			} else {
				UploadRunnable uploadRunnable = new UploadRunnable(
						jobId, newItem);
				Thread thread = new Thread(uploadRunnable);
				thread.start();
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Intent intent = new Intent(this, Personal.class);
			// startActivity(intent);
			this.finish(); 
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			break;
		case R.id.upload:
			saveProfile();
			submitProfile();
			
			break;
		case R.id.savefile:
			saveProfile();
			Toast.makeText(this,
					"save success", Toast.LENGTH_SHORT)
					.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public String HTTPGetInfo(String messageId, Profile_Item profile_Item) {
		String result = "";
		HttpPost httpPost = new HttpPost(uploadProfile_URL);// 将参数在地址中传递
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("id", messageId));
		params.add(new BasicNameValuePair("name", profile_Item.getName()));
		params.add(new BasicNameValuePair("sex", profile_Item.getSex()));
		params.add(new BasicNameValuePair("age", profile_Item.getAge()));
		params.add(new BasicNameValuePair("eduBackground", profile_Item
				.getEduBackground()));
		params.add(new BasicNameValuePair("school", profile_Item.getSchool()));
		params.add(new BasicNameValuePair("purpose", profile_Item.getPurpose()));
		params.add(new BasicNameValuePair("phoneNumber", profile_Item
				.getPhoneNumber()));
		params.add(new BasicNameValuePair("QQ", profile_Item.getQQ()));
		params.add(new BasicNameValuePair("E_mail", profile_Item.getE_mail()));
		params.add(new BasicNameValuePair("address", profile_Item.getAddress()));
		params.add(new BasicNameValuePair("intro", profile_Item.getIntro()));
		params.add(new BasicNameValuePair("exep", profile_Item.getExep()));
		params.add(new BasicNameValuePair("skill", profile_Item.getSkill()));
		params.add(new BasicNameValuePair("other", profile_Item.getOther()));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try {
			HttpResponse response = new DefaultHttpClient().execute(httpPost);
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
//		 Log.i("Myprofile result", result);
		return result;
	}

	// 后台线程解析下载的json格式文件
	class UploadRunnable implements Runnable {
		private String jobId;
		private Profile_Item profile_Item;

		public UploadRunnable(String id, Profile_Item _Item) {
			this.jobId = id;
			this.profile_Item = _Item;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String httpresponse = new String();
			httpresponse = HTTPGetInfo(jobId, profile_Item);
			try {
				JSONparser(httpresponse);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			// }
		}

		public void JSONparser(String result) throws JSONException {
			if (result.length() > 0) {
				JSONObject jsonObject = new JSONObject(result);
				map.put("msg", jsonObject.getString("msg"));// 信息的ID
				Message message = new Message();
				message.what = 0x3f;
				handler.sendMessage(message);
//				Log.i("message", map.get("msg").toString());
			}

		}
	}

}