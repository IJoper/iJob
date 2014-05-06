package com.ijob.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ijob.JobDetails;
import com.example.ijob.R;
import com.ijob.db.DBHelper;
import com.ijob.db.Infor_item;
import com.ijob.listview.XListView;
import com.ijob.listview.XListView.IXListViewListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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


public class MainFragment extends Fragment implements IXListViewListener{
	private Handler mHandler;
	private int start = 111;
	private SimpleAdapter xlistItemAdapter;
	private List<Map<String, Object>> mDataList;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private String URL = "http://1.iters.sinaapp.com/messages/getMessageById/";
	private XListView mListView;
	private DBHelper myDbHelper;
	private EditText editText;
	private Button search;
	private Button more;
	
	public MainFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflater the layout
		View view = inflater.inflate(R.layout.main_listview, null);
		editText = (EditText)view.findViewById(R.id.main_editText);
		search = (Button)view.findViewById(R.id.main_serach);
		more = (Button)view.findViewById(R.id.main_more);
		mListView = (XListView) view.findViewById(R.id.main_xListView1);
		
		editText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				editText.setFocusable(true);
			}
		});
		
		myDbHelper = new DBHelper(view.getContext());
		mDataList = myDbHelper.getMainListViewAllItem();
		
		
		if (mDataList == null) {
			mDataList = new ArrayList<Map<String, Object>>();
		}
		if (mDataList.size() > 0) {
			start = Integer.parseInt(mDataList.get(0).get("id").toString())+1;
		}
		Log.i("start", ""+start);
		
		mListView.setPullLoadEnable(true);
		
		xlistItemAdapter = new SimpleAdapter(view.getContext(), mDataList,
				R.layout.list_item, new String[] { "message_title", "peoplefocus",
						"company", "location" }, new int[] { R.id.company,
						R.id.peoplefocus, R.id.job, R.id.workplace });
		mListView.setAdapter(xlistItemAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				String job_id;
				job_id = mDataList.get(position-1).get("id").toString();
				Log.i("job index", job_id+" "+position);
				Bundle bundle = new Bundle();
				bundle.putString("job_id", job_id);
				Intent intent = new Intent(getActivity(), JobDetails.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String inputString = editText.getText().toString();
				String locationString = new String();
				String jobtypeString = new String();
//				Log.i("input", inputString);
//				Log.i("length", ""+inputString.length());
				if (inputString.length() == 0) {
					Toast.makeText(v.getContext(), "You input nothing !", Toast.LENGTH_SHORT).show();
				}
				if (inputString.contains("，")) {
					locationString = inputString.substring(0, inputString.indexOf("，"));
					jobtypeString = inputString.substring(inputString.indexOf("，")+1, inputString.length());
//					Toast.makeText(v.getContext(), "l="+locationString+" j="+jobtypeString, Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(v.getContext(), "正确输入格式：地区，职位", Toast.LENGTH_SHORT).show();
				}
			}
		});

		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
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
				DownloadRunnable runnable = new DownloadRunnable();
				Thread thread = new Thread(runnable);//获取新信息
				thread.start();
				synchronized(thread){
					try {
						thread.wait(2000);;
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				xlistItemAdapter.notifyDataSetChanged();
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
				//getInformation();//获取旧信息
				//xlistItemAdapter.notifyDataSetChanged();
			}
		}, 1000);
	}
	public String HTTPGetInfo() {
		String uri = URL + start + ".json";
		String result = "";
		HttpGet httpGet = new HttpGet(uri);// 将参数在地址中传递
//		Log.i("URL = ", uri);
		try {
			HttpResponse response = new DefaultHttpClient().execute(httpGet);
//			Log.i("response state = ", ""+ response.getStatusLine().getStatusCode());
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
		Log.i("result", result);
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
			if (result.length() > 200) {
//				Log.i("1_mdatalistlength", ""+mDataList.size());
				JSONObject jsonObject = new JSONObject(result);
				JSONArray jsonArray = jsonObject.getJSONArray("job"); 
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", jsonObject.getString("id"));//信息的ID
				map.put("message_title", jsonObject.getString("message_title"));//信息标题
				map.put("company", jsonObject.getString("company"));//公司
				map.put("location", jsonObject.getString("location"));//公司地点
				
				int i;
				String string = new String();
				for (i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);
					if (i == jsonArray.length() - 1) {
						string += jsonObject2.getString("type_id");
					}else {
						string += jsonObject2.getString("type_id")+"/";
					}
//					map.put("job_id"+i, jsonObject2.getString("jid"));//岗位ID
//					map.put("job_type"+i, jsonObject2.getString("type_id"));//实习，兼职，全职
//					map.put("job_area"+i, jsonObject2.getString("job_area"));//工作地点
//					map.put("job_responsibilities"+i, jsonObject2.getString("job_responsibilities"));//工作岗位描述
//					map.put("job_requirements"+i, jsonObject2.getString("job_requirements"));//工作技能需求
				}
				map.put("job_type", string);
				map.put("job_count", jsonArray.length());
				mDataList.add(0,map);
				myDbHelper.addMainListViewByItem(new Infor_item(Integer.parseInt(jsonObject.getString("id")), jsonObject.getString("message_title"),
						jsonObject.getString("company"), string, jsonObject.getString("location"),"5000-8000", "2014-04-02", "2014-04-06"));
//				Log.i("2_mdatalistlength", ""+mDataList.size());
				start ++;
			}
		}
	}
}
