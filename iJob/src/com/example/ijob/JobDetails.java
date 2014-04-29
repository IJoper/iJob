package com.example.ijob;

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

import com.ijob.db.DBHelper;
import com.ijob.db.Infor_item;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JobDetails extends Activity{
	private TextView companyTextView;
	private TextView locationTextView;
	private TextView messagetitleTextView;
	private TextView jobdetailsTextView;
	private ImageView backImageView;
	private Button submitButton;
	private ImageView collectImageView;
	Map<String, Object> map = new HashMap<String, Object>();
	private String jobID;
	private final String URL = "http://1.iters.sinaapp.com/messages/getMessageById/";
	private Handler mHandler;
	private boolean downloadDone = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //声明隐藏标题
		setContentView(R.layout.jobdetails);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//自定义布局赋值
		
		companyTextView = (TextView)findViewById(R.id.jobdetails_company);
		locationTextView = (TextView)findViewById(R.id.jobdetails_location);
		messagetitleTextView = (TextView)findViewById(R.id.jobdetails_message_title);
		jobdetailsTextView = (TextView)findViewById(R.id.jobdetails_details);
		backImageView = (ImageView)findViewById(R.id.jobdetails_back);
		submitButton = (Button)findViewById(R.id.jobdetails_submit);
		collectImageView = (ImageView)findViewById(R.id.jobdetails_collect);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		jobID = bundle.getString("job_id");
		DownloadRunnable runnable = new DownloadRunnable();
		new Thread(runnable).start();
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Log.i("msg.what", Integer.toString(msg.what));
				switch (msg.what) {//如果下载的xml文件已经解析完毕，则会接收到信号msg.what=0x1f
				case 0x1f:
					companyTextView.setText(map.get("company").toString());
					locationTextView.setText(map.get("location").toString());
					messagetitleTextView.setText(map.get("message_title").toString());
					String string = new String();
					Log.i("jobcount", map.get("job_count").toString());
					for (int i = 0; i < Integer.parseInt(map.get("job_count").toString()); i++) {
						string += map.get("job_id"+i).toString();
						string += map.get("job_type"+i).toString();
						string += map.get("job_area"+i).toString();
						string += map.get("job_responsibilities"+i).toString();
						string += map.get("job_requirements"+i).toString();
					}
					jobdetailsTextView.setText(string);
					downloadDone = true;
				default:
					break;
				}
			}
		};
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(v.getContext(), "function wait...", Toast.LENGTH_SHORT).show();
			}
		});
		collectImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				DBHelper mDbHelper = new DBHelper(v.getContext());
				String string = new String();
				for (int i = 0; i < Integer.parseInt(map.get("job_count").toString()); i++) {
					if (i == Integer.parseInt(map.get("job_count").toString()) - 1) {
						string += map.get("job_type"+i).toString();
					}else {
						string += map.get("job_type"+i).toString()+"/";
					}
				}
				if (downloadDone && (!mDbHelper.findCollectionListViewItemById(Integer.parseInt(map.get("id").toString())))) {
					mDbHelper.addCollectionListViewByItem(new Infor_item(
							Integer.parseInt(map.get("id").toString()), 
							map.get("message_title").toString(), 
							map.get("company").toString(), 
							string,
							map.get("location").toString(),
							"4000-6000",
							"2014-04-06", "2014-06-09"));
					Toast.makeText(v.getContext(), "You have collected!",Toast.LENGTH_SHORT).show();
				}
//				Toast.makeText(v.getContext(), "You have collected!",Toast.LENGTH_SHORT).show();
			}
		});
	}
	public String HTTPGetInfo() {
		String uri = URL + jobID + ".json";
		String result = "";
		HttpGet httpGet = new HttpGet(uri);// 将参数在地址中传递
		Log.i("URL = ", uri);
		try {
			HttpResponse response = new DefaultHttpClient().execute(httpGet);
			//Log.i("response state = ", ""+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				StringBuffer buffer = new StringBuffer();
				BufferedReader bufferedReader = new BufferedReader(new 
						InputStreamReader(response.getEntity().getContent()));
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
//		Log.i("result", result);
		return result;
	}

	// 后台线程解析下载的json格式文件
	class DownloadRunnable implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String httpresponse = new String();
			for (int i = 0; i < 10; i++) {
				httpresponse = HTTPGetInfo();
				try {
					JSONparser(httpresponse);
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}

		
		public void JSONparser(String result) throws JSONException {
			if (result.length() > 100) {
				JSONObject jsonObject = new JSONObject(result);
				JSONArray jsonArray = jsonObject.getJSONArray("job"); 
				map.put("id", jsonObject.getString("id"));//信息的ID
				map.put("message_title", jsonObject.getString("message_title"));//信息标题
				map.put("company", jsonObject.getString("company"));//公司
				map.put("location", jsonObject.getString("location"));//公司地点
				int i;
				for (i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);
					map.put("job_id"+i, jsonObject2.getString("jid"));//岗位ID
					map.put("job_type"+i, jsonObject2.getString("type_id"));//实习，兼职，全职
					map.put("job_area"+i, jsonObject2.getString("job_area"));//工作地点
					map.put("job_responsibilities"+i, jsonObject2.getString("job_responsibilities"));//工作岗位描述
					map.put("job_requirements"+i, jsonObject2.getString("job_requirements"));//工作技能需求
				}
				map.put("job_count", i);
				Log.i("jobcount", ""+i);
				Message message = new Message();
				message.what = 0x1f;
				mHandler.sendMessage(message);
			}
		}
	}

}
