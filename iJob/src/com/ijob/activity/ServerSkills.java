package com.ijob.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ijob.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ServerSkills extends Activity {
	private ListView myListView;
	private SimpleAdapter mAdapter;
	private List<Map<String, Object>> Datalist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Datalist = new ArrayList<Map<String, Object>>();
		String title[] = getResources().getStringArray(R.array.skillsTitle);
		for (int i = 0; i < title.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("skillstitle", title[i]);
			Datalist.add(map);
		}
		myListView = (ListView) findViewById(R.id.server_listView1);
		mAdapter = new SimpleAdapter(this, Datalist, R.layout.server_item,
				new String[] { "skillstitle" },
				new int[] { R.id.serverItem_textview });
		myListView.setAdapter(mAdapter);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				Log.i("position", "" + position);
				Intent intent = new Intent(view.getContext(),
						SkillsDetails.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", "" + position);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
