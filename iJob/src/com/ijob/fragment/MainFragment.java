package com.ijob.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ijob.R;
import com.ijob.activity.CreateJsonData;
import com.ijob.activity.JobDetails;
import com.ijob.activity.MainActivity;
import com.ijob.activity.Request;
import com.ijob.db.City_Item;
import com.ijob.db.DBHelper;
import com.ijob.db.Infor_item;
import com.ijob.db.Job_Item;
import com.ijob.listview.XListView;
import com.ijob.listview.XListView.IXListViewListener;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainFragment extends Fragment implements IXListViewListener {
	private Handler mHandler;
	private int start = 111;
	private SimpleAdapter xlistItemAdapter;
	private List<Map<String, Object>> mDataList;
	private List<City_Item> myFocuscityList;
	private List<Job_Item> myFocusjobList;
	SharedPreferences preferences;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private String URL = "http://1.iters.sinaapp.com/messages/getMessageById/";
	private String uri = "http://iters.sinaapp.com/messages/searchMessageByLocationAndJobType.json";
	private XListView mListView;
	private DBHelper myDbHelper;
	private EditText editText;
	private Button search;
	private Button more;
	private int newsCount;

	public MainFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		preferences = getActivity().getSharedPreferences("login",
				Context.MODE_PRIVATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflater the layout
		final View view = inflater.inflate(R.layout.main_listview, null);
		preferences = getActivity().getSharedPreferences("login",
				Context.MODE_PRIVATE);
		mListView = (XListView) view.findViewById(R.id.main_xListView1);

		myDbHelper = new DBHelper(view.getContext());
		mDataList = myDbHelper.getMainListViewAllItem();
		if (mDataList == null) {
			mDataList = new ArrayList<Map<String, Object>>();
		}
		if (mDataList.size() > 0) {
			start = Integer.parseInt(mDataList.get(0).get("id").toString()) + 1;
		}
		// Log.i("DB size", "" + mDataList.size());

		mListView.setPullLoadEnable(true);

		xlistItemAdapter = new SimpleAdapter(view.getContext(), mDataList,
				R.layout.list_item, new String[] { "message_title",
						"peoplefocus", "company", "location" }, new int[] {
						R.id.company, R.id.peoplefocus, R.id.job,
						R.id.workplace });
		mListView.setAdapter(xlistItemAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// Log.i("msg.what", Integer.toString(msg.what));
				switch (msg.what) {// 如果下载的xml文件已经解析完毕，则会接收到信号msg.what=0x1f
				case 0x0f:
					Toast.makeText(view.getContext(),
							"" + newsCount + " News Update !",
							Toast.LENGTH_SHORT).show();
					xlistItemAdapter.notifyDataSetChanged();
					break;
				case 0x0a:
					Toast.makeText(view.getContext(), "No News Update !",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		};
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				String job_id;
				job_id = mDataList.get(position - 1).get("id").toString();
				// Log.i("job index", job_id+" "+position);
				Bundle bundle = new Bundle();
				bundle.putString("job_id", job_id);
				Intent intent = new Intent(getActivity(), JobDetails.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		return view;
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String current_timeString = formatter.format(curDate);
		mListView.setRefreshTime(current_timeString);
	}

	@Override
	public void onRefresh() {
		// TODO 自动生成的方法存根
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Log.i("mainFragment", Boolean.toString(preferences.getBoolean(
						"changeSet", true)));
				if (preferences.getBoolean("changeSet", true)) {
					mDataList.clear();
					myDbHelper.deleteMainListViewTable();
				}
				DownloadRunnable runnable = new DownloadRunnable();
				Thread thread = new Thread(runnable);// 获取新信息
				thread.start();
				onLoad();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			// TODO 自动生成的方法存根
			@Override
			public void run() {
				// getInformation();//获取旧信息
				// xlistItemAdapter.notifyDataSetChanged();
				onLoad();

			}
		}, 1000);

	}

	public String HTTPGetInfo() {
		myFocuscityList = myDbHelper.getFocusCityList();
		myFocusjobList = myDbHelper.getFocusJobList();

		String jidString = new CreateJsonData()
				.CreateJsonByJobList(myFocusjobList);
		String locString = new CreateJsonData()
				.CreateJsonByCityList(myFocuscityList);

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
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String httpresponse = new String();
			// for (int i = 0; i < 10; i++) {
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
				Log.i("1_mdatalistlength", "" + mDataList.size());
				JSONArray jsonArray = new JSONArray(result);
				newsCount = 0;
				if (jsonArray.length() > mDataList.size()) {
					for (int i = mDataList.size(); i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", jsonObject.getString("id"));// 信息的ID
						map.put("message_title",
								jsonObject.getString("message_title"));// 信息标题
						map.put("company", jsonObject.getString("company"));// 公司
						map.put("location", jsonObject.getString("location"));// 公司地点
						Log.i("DBHELPER", Boolean.toString(preferences
								.getBoolean("changeSet", true)));
						myDbHelper.addMainListViewByItem(new Infor_item(Integer
								.parseInt(jsonObject.getString("id")),
								jsonObject.getString("message_title"),
								jsonObject.getString("company"), "1",
								jsonObject.getString("location"), "5000-8000",
								"2014-04-02", "2014-04-06"));
						mDataList.add(0, map);
						newsCount++;
					}

					// Log.i("2_mdatalistlength", "" + mDataList.size());
					Message message = new Message();
					if (newsCount == 0) {
						message.what = 0x0a;
						mHandler.sendMessage(message);
					} else {
						message.what = 0x0f;
						mHandler.sendMessage(message);
					}

				}
				Editor editor = preferences.edit();
				editor.putBoolean("changeSet", false);
				editor.commit();
			}
		}
	}
}
