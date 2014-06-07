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
import com.ijob.db.City_Item;
import com.ijob.db.DBHelper;
import com.ijob.db.Job_Item;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SearchViewActivity extends Activity {
	// private SearchViewAdapter mAdapter;
	private ListView mSearchListView;
	private SimpleAdapter myAdapter;
	private List<Map<String, Object>> mDataList;
	private DBHelper myDbHelper;
	private Handler handler;
	private int cityID;
	private int jobID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplelistview);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		if (getIntent().getAction().equals(Intent.ACTION_SEARCH)) {
			// the string used to query
			String query = getIntent().getStringExtra(SearchManager.QUERY);
			// Log.i("query string", query);
			myDbHelper = new DBHelper(this);
			mDataList = new ArrayList<Map<String, Object>>();

			mSearchListView = (ListView) findViewById(R.id.simpleListview1);
			myAdapter = new SimpleAdapter(this, mDataList, R.layout.list_item,
					new String[] { "message_title", "peoplefocus", "company",
							"location" }, new int[] { R.id.company,
							R.id.peoplefocus, R.id.job, R.id.workplace });
			mSearchListView.setAdapter(myAdapter);

			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// Log.i("msg.what", Integer.toString(msg.what));
					switch (msg.what) {// 如果下载的xml文件已经解析完毕，则会接收到信号msg.what=0x1f
					case 0x2f:
						myAdapter.notifyDataSetChanged();
					default:
						break;
					}
				}
			};

			mSearchListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO 自动生成的方法存根
					String job_id;
					job_id = mDataList.get(position).get("id").toString();
					// Log.i("job index", job_id+" "+position);
					Bundle bundle = new Bundle();
					bundle.putString("job_id", job_id);
					Intent intent = new Intent(view.getContext(),
							JobDetails.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
			doMySearch(query);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
			// the string used to query
			String query = intent.getStringExtra(SearchManager.QUERY);
			// Log.i("input string", query);
			doMySearch(query);
		}
	}

	private void doMySearch(String str) {
		ArrayList<String> data = new ArrayList<String>();
		// TODO 查询

		if (str.length() == 0) {
			Toast.makeText(this, "You input nothing !", Toast.LENGTH_SHORT)
					.show();
		}
		if (str.contains("，")) {
			String locationString = str.substring(0, str.indexOf("，"));
			String jobtypeString = str.substring(str.indexOf("，") + 1,
					str.length());
			// Log.i("input location", locationString);
			// Log.i("input job", jobtypeString);
			cityID = myDbHelper.getCityIdByName(locationString);
			jobID = myDbHelper.getJobIdByName(jobtypeString);
			if (cityID != -1 && jobID != -1) {
				DownloadRunnable runnable = new DownloadRunnable(
						locationString, jobID);
				Thread thread = new Thread(runnable);
				thread.start();
			} else {
				if (cityID == -1 && jobID != -1) {
					Toast.makeText(this, "你所查找的地区不存在或暂时没数据", Toast.LENGTH_SHORT)
							.show();
				} else if (cityID != -1 && jobID == -1) {
					Toast.makeText(this, "你所查找的职位描述不正确或暂时没数据",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "你所查找的地区和职位描述有误", Toast.LENGTH_SHORT)
							.show();
				}
			}
			// Toast.makeText(v.getContext(),
			// "l="+locationString+" j="+jobtypeString,
			// Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "请按正确格式输入：地区，职位", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		searchView.setSubmitButtonEnabled(true);
		searchView.setQueryHint(getResources().getString(R.string.search_hint));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			this.finish();
			return true;
		case R.id.menu_search:
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public String HTTPGetInfo(String location, int jobID) {
		String jidString = "" + jobID;
		String locString = location;
		String uri = "http://iters.sinaapp.com/messages/searchMessageByLocationAndJobType.json";
		String result = "";
		HttpPost httpPost = new HttpPost(uri);// 将参数在地址中传递
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("jid", jidString));
		params.add(new BasicNameValuePair("location", locString));
		// Log.i("locString", locString);
		// Log.i("jidString", jidString);
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
		Log.i("result", result);
		return result;
	}

	// 后台线程解析下载的json格式文件
	class DownloadRunnable implements Runnable {
		private String location;
		private int jobId;

		public DownloadRunnable(String loc, int jobid) {
			this.location = loc;
			this.jobId = jobid;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String httpresponse = new String();
			// for (int i = 0; i < 10; i++) {
			mDataList.clear();
			httpresponse = HTTPGetInfo(location, jobID);
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
				// Log.i("1_mdatalistlength", ""+mDataList.size());
				JSONArray json = new JSONArray(result);
				for (int i = 0; i < json.length(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					JSONObject jsonObject = json.getJSONObject(i);
					map.put("id", jsonObject.getString("id"));// 信息的ID
					map.put("message_title",
							jsonObject.getString("message_title"));// 信息标题
					map.put("company", jsonObject.getString("company"));// 公司
					map.put("location", jsonObject.getString("location"));// 公司地点
					mDataList.add(map);
				}
				Message message = new Message();
				message.what = 0x2f;
				handler.sendMessage(message);
			}

		}
	}

}
