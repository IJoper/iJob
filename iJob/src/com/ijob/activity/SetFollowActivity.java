package com.ijob.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ijob.R;
import com.ijob.db.City_Item;
import com.ijob.db.DBHelper;
import com.ijob.db.Job_Item;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SetFollowActivity extends Activity {

	private GridView followedCityGridView;
	private GridView followedPostGridView;
	private SimpleAdapter gridViewAdapter;
	private SimpleAdapter postGridViewAdapter;
	// private Follow userFollow = new Follow();
	SharedPreferences preferences;
	public final static String SER_KEY = "com.example.ijob";
	private List<Map<String, Object>> followedItemList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> followedPostList = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.follow_label);
		preferences = getSharedPreferences("login",
				Context.MODE_PRIVATE);
		ActionBar actionBar = getActionBar();
		if (!preferences.getBoolean("first", true)) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		
		DBHelper dbHelper = new DBHelper(this);

		followedCityGridView = (GridView) findViewById(R.id.followedCityGridView);
		List<City_Item> followedCityList = dbHelper.getFocusCityList();
		for (int i = 0; i < followedCityList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("followedItem", followedCityList.get(i).getCityName());
			followedItemList.add(map);
		}
		gridViewAdapter = new SimpleAdapter(this, followedItemList,
				R.layout.follow_item,
				new java.lang.String[] { "followedItem" },
				new int[] { R.id.followedItem });
		followedCityGridView.setAdapter(gridViewAdapter);

		followedPostGridView = (GridView) findViewById(R.id.followedPostGridView);
		// ArrayList<String> followedPost = userFollow.getFollowedPostList();
		List<Job_Item> followedPost = dbHelper.getFocusJobList();
		for (int i = 0; i < followedPost.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("followedPost", followedPost.get(i).getJobName());
			followedPostList.add(map);
		}
		postGridViewAdapter = new SimpleAdapter(this, followedPostList,
				R.layout.follow_item,
				new java.lang.String[] { "followedPost" },
				new int[] { R.id.followedItem });
		followedPostGridView.setAdapter(postGridViewAdapter);

		ImageView addFollowCity = (ImageView) findViewById(R.id.addFollowCity);
		addFollowCity.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				Intent intent = new Intent(v.getContext(),CitySelectActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				finish();
			}
		});

		ImageView addFollowPost = (ImageView) findViewById(R.id.addFollowPost);
		addFollowPost.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),PostSelectActivity.class);
				
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				finish();
				
				
			}
		});
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			Intent intent = new Intent(this, MainActivity.class);
//			startActivity(intent);
//			this.finish();
//		}
//		return false;
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.city_select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		case R.id.saveCityChoice:
			if (preferences.getBoolean("first", true)) {
				Editor editor = preferences.edit();
				editor.putBoolean("first", false);
				editor.commit();
				startActivity(new Intent(SetFollowActivity.this, MainActivity.class));
			}
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
