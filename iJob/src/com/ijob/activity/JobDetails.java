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
import com.ijob.db.DBHelper;
import com.ijob.db.Infor_item;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JobDetails extends Activity {
	private TextView companyTextView;
	private TextView locationTextView;
	private TextView messagetitleTextView;
	private TextView jobdetailsTextView;
	private ProgressDialog progressDialog;
	private int saveFinish;

	Map<String, Object> map = new HashMap<String, Object>();
	private String jobID;
	private final String URL = "http://1.iters.sinaapp.com/messages/getMessageById/";
	private Handler mHandler;
	private boolean downloadDone = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.jobdetails);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		companyTextView = (TextView) findViewById(R.id.jobdetails_company);
		locationTextView = (TextView) findViewById(R.id.jobdetails_location);
		messagetitleTextView = (TextView) findViewById(R.id.jobdetails_message_title);
		jobdetailsTextView = (TextView) findViewById(R.id.jobdetails_details);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		jobID = bundle.getString("job_id");

		DownloadRunnable runnable = new DownloadRunnable();
		new Thread(runnable).start();
		progressDialog = new ProgressDialog(JobDetails.this);
		progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
		progressDialog.setMessage("正在努力为你加载中...");
		progressDialog.setProgress(100);
		progressDialog.setIndeterminate(false);
		progressDialog.show();
		saveFinish = 0;

		new Thread() {
			public void run() {
				try {
					while (saveFinish <= 100) {
						progressDialog.setProgress(saveFinish);
						Thread.sleep(1);
					}
				} catch (Exception e) {
					// TODO: handle exception
					progressDialog.cancel();
				}
				if (saveFinish >= 100) {
					progressDialog.cancel();
				}
			}
		}.start();

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.i("msg.what", Integer.toString(msg.what));
				switch (msg.what) {// 如果下载的xml文件已经解析完毕，则会接收到信号msg.what=0x1f
				case 0x1f:
					companyTextView.setText(map.get("company").toString());
					locationTextView.setText(map.get("location").toString());
					messagetitleTextView.setText(map.get("message_title")
							.toString());
					String string = new String();
					Log.i("jobcount", map.get("job_count").toString());
					for (int i = 0; i < Integer.parseInt(map.get("job_count")
							.toString()); i++) {
						string += map.get("job_id" + i).toString();
						string += map.get("job_type" + i).toString();
						string += map.get("job_area" + i).toString();
						string += map.get("job_responsibilities" + i)
								.toString();
						string += map.get("job_requirements" + i).toString();
					}
					jobdetailsTextView.setText(string);
					Log.i("jobdetails", string);
					downloadDone = true;
				default:
					break;
				}
			}
		};

	}

	public void collectJob() {
		DBHelper mDbHelper = new DBHelper(JobDetails.this);
		String string = new String();
		for (int i = 0; i < Integer.parseInt(map.get("job_count").toString()); i++) {
			if (i == Integer.parseInt(map.get("job_count").toString()) - 1) {
				string += map.get("job_type" + i).toString();
			} else {
				string += map.get("job_type" + i).toString() + "/";
			}
		}
		if (downloadDone
				&& (!mDbHelper.findCollectionListViewItemById(Integer
						.parseInt(map.get("id").toString())))) {
			mDbHelper.addCollectionListViewByItem(new Infor_item(Integer
					.parseInt(map.get("id").toString()), map.get(
					"message_title").toString(), map.get("company").toString(),
					string, map.get("location").toString(), "4000-6000",
					"2014-04-06", "2014-06-09"));
			Toast.makeText(JobDetails.this, "You have collected!",
					Toast.LENGTH_SHORT).show();
		}
		Toast.makeText(this, "You have collected!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.job_detail, menu);

		return true;
	}

	public void submitProfile() {
		AlertDialog choiseAlert = new AlertDialog.Builder(JobDetails.this)
				.create();
		choiseAlert.setTitle("请选择");
		choiseAlert.setMessage("填写简历或上传已有的简历文件");
		// 设置按钮1填写简历模板
		choiseAlert.setButton(DialogInterface.BUTTON_NEGATIVE, "填写简历模板",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(JobDetails.this,
								MyProfile.class);
						Bundle bundle = new Bundle();
						bundle.putString("messageId", jobID);
						intent.putExtras(bundle);
						startActivity(intent);

					}
				});

		// 设置按钮2，调用系统文件管理系统，上传自己的文件，可打开文件管理器，但不能获得文件，也不能上传
		choiseAlert.setButton(DialogInterface.BUTTON_POSITIVE, "上传自己的简历",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("*/*");
						intent.addCategory(Intent.CATEGORY_OPENABLE);
						try {
							startActivityForResult(
									Intent.createChooser(intent, "选择你的简历"), 1);
						} catch (android.content.ActivityNotFoundException ex) {
							Toast.makeText(JobDetails.this, 11,
									Toast.LENGTH_SHORT).show();
							;
						}

					}
				});
		choiseAlert.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		case R.id.submitProfile:

			submitProfile();

			break;
		case R.id.collection:

			collectJob();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public String HTTPGetInfo() {
		String uri = URL + jobID + ".json";
		String result = "";
		HttpGet httpGet = new HttpGet(uri);// 将参数在地址中传递
		Log.i("URL = ", uri);
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
		// Log.i("result", result);
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
			saveFinish = 0;
			if (result.length() > 100) {
				JSONObject jsonObject = new JSONObject(result);
				JSONArray jsonArray = jsonObject.getJSONArray("job");
				map.put("id", jsonObject.getString("id"));// 信息的ID
				map.put("message_title", jsonObject.getString("message_title"));// 信息标题
				map.put("company", jsonObject.getString("company"));// 公司
				map.put("location", jsonObject.getString("location"));// 公司地点
				int i;
				int len = jsonArray.length();
				for (i = 0; i < len; i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					map.put("job_id" + i, jsonObject2.getString("jid"));// 岗位ID
					map.put("job_type" + i, jsonObject2.getString("type_id"));// 实习，兼职，全职
					map.put("job_area" + i, jsonObject2.getString("job_area"));// 工作地点
					map.put("job_responsibilities" + i,
							jsonObject2.getString("job_responsibilities"));// 工作岗位描述
					map.put("job_requirements" + i,
							jsonObject2.getString("job_requirements"));// 工作技能需求
					saveFinish += 100 / len + 1;
					Log.i("saveFinish", saveFinish + "%"+len);
				}
				saveFinish += 101;
				map.put("job_count", i);
				Log.i("jobcount", "" + i);
				Message message = new Message();
				message.what = 0x1f;
				mHandler.sendMessage(message);
			}
		}
	}

}
