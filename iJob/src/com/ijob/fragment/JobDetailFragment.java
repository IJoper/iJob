package com.ijob.fragment;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.w3c.dom.Text;

import com.example.ijob.MainActivity;
import com.example.ijob.R;
import com.ijob.fragment.MainFragment.DownloadRunnable;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class JobDetailFragment extends Fragment {
	
	private SimpleAdapter Adapter;
	private List<Map<String, Object>> DataList = new ArrayList<Map<String, Object>>();
	private String URL = "http://1.iters.sinaapp.com/messages/getMessageById/";
	Map<String, Object> mMap = new HashMap<String, Object>();
	private Button backButton;
	private Button onlineButton;
	private Button offlineButton;
	private TextView companyTextView;
	private TextView messageTextView;
	private TextView locationTextView;
	private ListView mListView;
	private View jobView;
	int job_id;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.i("msg.what", Integer.toString(msg.what));
			switch (msg.what) {//������ص�xml�ļ��Ѿ�������ϣ������յ��ź�msg.what=0x11
			case 0x11:
				messageTextView.setText(mMap.get("message_title").toString());
				companyTextView.setText(mMap.get("company").toString());
				locationTextView.setText(mMap.get("location").toString());
				
				Adapter = new SimpleAdapter(jobView.getContext(), DataList,
						R.layout.jobitem, new String[] { "job_id", "job_type",
								"job_area", "job_responsibilities", "job_requirements"}, 
								new int[] { R.id.jobitem_jobid,
								R.id.jobitem_type_id, R.id.jobitem_job_area, 
								R.id.jobitem_job_responsibilities, R.id.jobitem_job_requirements });
				mListView.setAdapter(Adapter);
			default:
				break;
			}
		}
	};
	
    public JobDetailFragment(int _job_id) {
    	this.job_id = _job_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jobdetails, null);
        jobView = view;
        backButton = (Button)view.findViewById(R.id.jobdetails_back);
        onlineButton = (Button)view.findViewById(R.id.jobdetails_online);
        offlineButton = (Button)view.findViewById(R.id.jobdetails_offline);
        companyTextView = (TextView)view.findViewById(R.id.jobdetails_company);
        locationTextView = (TextView)view.findViewById(R.id.jobdetails_location);
        messageTextView = (TextView)view.findViewById(R.id.jobdetails_message_title);
        mListView = (ListView)view.findViewById(R.id.jobdetails_listView1);
        
        DownloadRunnable runnable = new DownloadRunnable();
		new Thread(runnable).start();	
    
        backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
				MainFragment contentFragment = (MainFragment)fragmentManager.findFragmentByTag("A");
	            fragmentManager.beginTransaction()
	            .replace(R.id.content, contentFragment == null ? new MainFragment():contentFragment,"A")
	            .commit();
				
			}
		});
        return view;
    }
    
    public String HTTPGetInfo() {
		String uri = URL + job_id + ".json";
		String result = "";
		HttpGet httpGet = new HttpGet(uri);// �������ڵ�ַ�д���
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
		Log.i("result", result);
		return result;
	}

	// ��̨�߳̽������ص�json��ʽ�ļ�
	class DownloadRunnable implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String httpresponse = new String();
			httpresponse = HTTPGetInfo();
			try {
				JSONparser(httpresponse);
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}

		public void JSONparser(String result) throws JSONException {
			if (result.length() > 100) {
				Log.i("1_mdatalistlength", ""+DataList.size());
				JSONObject jsonObject = new JSONObject(result);
				JSONArray jsonArray = jsonObject.getJSONArray("job"); 
				mMap.put("id", jsonObject.getString("id"));//��Ϣ��ID
				mMap.put("message_title", jsonObject.getString("message_title"));//��Ϣ����
				mMap.put("company", jsonObject.getString("company"));//��˾
				mMap.put("location", jsonObject.getString("location"));//��˾�ص�
				int i;
				for (i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("job_id"+i, jsonObject2.getString("jid"));//��λID
					map.put("job_type"+i, jsonObject2.getString("type_id"));//ʵϰ����ְ��ȫְ
					map.put("job_area"+i, jsonObject2.getString("job_area"));//�����ص�
					map.put("job_responsibilities"+i, jsonObject2.getString("job_responsibilities"));//������λ����
					map.put("job_requirements"+i, jsonObject2.getString("job_requirements"));//������������
					DataList.add(map);
				}
				Log.i("2_mdatalistlength", ""+DataList.size());
				Message message = mHandler.obtainMessage();
				message.what = 0x11;
				mHandler.sendMessage(message);
			}
			if (result == null) {
				Log.i("���ص�JSON�ļ�", "�յ�");
			}
		}
	}

}
