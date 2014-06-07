package com.ijob.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ijob.R;
import com.ijob.activity.FunctionShow;
import com.ijob.activity.Request;
import com.ijob.activity.VersionUpdate;

public class AboutUs extends Fragment {
	private SimpleAdapter MyAdapter;
	private List<Map<String, Object>> Datalist = new ArrayList<Map<String, Object>>();
	private String item[];
	private ListView mListView;

	public AboutUs() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.aboutus, null);
		item = view.getContext().getResources()
				.getStringArray(R.array.about_item);
		for (int i = 0; i < item.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("item", item[i]);
			Datalist.add(map);
		}
		MyAdapter = new SimpleAdapter(view.getContext(), Datalist,
				R.layout.about_item, new String[] { "item" },
				new int[] { R.id.about_item_text });
		mListView = (ListView) view.findViewById(R.id.about_listView1);
		mListView.setAdapter(MyAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				switch (position) {
				case 0:
					startActivity(new Intent(view.getContext(),
							FunctionShow.class));
					break;
				case 1:
					startActivity(new Intent(view.getContext(),
							VersionUpdate.class));
					break;
				case 2:
					startActivity(new Intent(view.getContext(), Request.class));
					break;
				default:
					break;
				}
			}
		});
		return view;
	}

}