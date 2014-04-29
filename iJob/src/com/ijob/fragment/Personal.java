package com.ijob.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ijob.MyCollection;
import com.example.ijob.MyFocus;
import com.example.ijob.MyProfile;
import com.example.ijob.R;
import com.ijob.db.DBHelper;

public class Personal extends Fragment {
	private ListView mListView;
	private SimpleAdapter mAdapter;
	private List<Map<String, Object>> DataList = new ArrayList<Map<String, Object>>();
    private int collection_count;
    private int profile_count;
    private int focus_count;
	
	public Personal() {
		collection_count = 0;
		profile_count = 0;
		focus_count = 0;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal, null);
        mListView = (ListView)view.findViewById(R.id.personal_listView1);
        DBHelper myDbHelper = new DBHelper(view.getContext());
        
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("icon", R.drawable.ic_launcher);
		map1.put("content",  view.getContext().getString(R.string.collect));
		map1.put("num", ""+myDbHelper.getCollectionListViewItemNum());
		DataList.add(map1);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("icon", R.drawable.ic_launcher);
		map2.put("content",  view.getContext().getString(R.string.profile));
		map2.put("num", profile_count);
		DataList.add(map2);
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("icon", R.drawable.ic_launcher);
		map3.put("content",  view.getContext().getString(R.string.setfocus));
		map3.put("num", focus_count);
		DataList.add(map3);
		
        mAdapter = new SimpleAdapter(view.getContext(), DataList, 
        		R.layout.personal_listview_item, 
        		new String[] {"icon","content","num"}, 
        		new int [] {R.id.personal_listview_item_imageView1, R.id.personal_listview_item_textView1, R.id.personal_listview_item_textView2});
        mListView.setAdapter(mAdapter);
        
        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				Log.i("position", ""+position);
				switch (position) {
				case 0:
					Intent intent = new Intent(view.getContext(), MyCollection.class);
					startActivity(intent);
					break;
				case 1:
					Intent intent1 = new Intent(view.getContext(), MyProfile.class);
					startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent(view.getContext(), MyFocus.class);
					startActivity(intent2);
					break;
				default:
					break;
				}
			}
		});
        return view;
    }

}