package com.ijob.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ijob.R;
import com.ijob.activity.MBTIchcek;
import com.ijob.activity.ServerSkills;

public class HelpServerFragment extends Fragment {

	private ListView myListView;
	private SimpleAdapter mAdapter;
	private List<Map<String, Object>> Datalist;
	private String wageSearchURL = "http://article.zhaopin.com/payquery/search_form.do?type=1";

	public HelpServerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Datalist = new ArrayList<Map<String, Object>>();
		String title[] = getResources().getStringArray(R.array.serverTitle);
		for (int i = 0; i < title.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title[i]);
			Datalist.add(map);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.server, null);
		myListView = (ListView) view.findViewById(R.id.server_listView1);
		mAdapter = new SimpleAdapter(view.getContext(), Datalist,
				R.layout.server_item, new String[] { "title" },
				new int[] { R.id.serverItem_textview });
		myListView.setAdapter(mAdapter);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				Intent intent;
				switch (position) {
				case 0:
					intent = new Intent(view.getContext(), ServerSkills.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(view.getContext(), MBTIchcek.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent("android.intent.action.VIEW", Uri
							.parse(wageSearchURL));
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
		return view;
	}

}
