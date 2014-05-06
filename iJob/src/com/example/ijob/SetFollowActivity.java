package com.example.ijob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ijob.db.City_Item;
import com.ijob.db.DBHelper;
import com.ijob.db.Job_Item;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SetFollowActivity extends Activity {

	private GridView followedCityGridView;
	private GridView followedPostGridView;
	private SimpleAdapter gridViewAdapter;
	private SimpleAdapter postGridViewAdapter;
	// private Follow userFollow = new Follow();
	public final static String SER_KEY = "com.example.ijob";
	private List<Map<String, Object>> followedItemList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> followedPostList = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.follow_label);

		DBHelper dbHelper = new DBHelper(this);

		if (dbHelper.getCityNum() == 0) {
			dbHelper.initCityTable(getResources().getIntArray(R.array.city_id),
					getResources().getStringArray(R.array.city_name));
		}
		if (dbHelper.getJobNum() == 0) {
			int initJobIdArray[][] = {
					getResources().getIntArray(R.array.job_type11),
					getResources().getIntArray(R.array.job_type12),
					getResources().getIntArray(R.array.job_type13),
					getResources().getIntArray(R.array.job_type14),
					getResources().getIntArray(R.array.job_type15),
					getResources().getIntArray(R.array.job_type16),
					getResources().getIntArray(R.array.job_type17),
					getResources().getIntArray(R.array.job_type18),
					getResources().getIntArray(R.array.job_type19),
					getResources().getIntArray(R.array.job_type20),
					getResources().getIntArray(R.array.job_type21),
					getResources().getIntArray(R.array.job_type22),
					getResources().getIntArray(R.array.job_type23) };
			String initJobNameArray[][] = {
					getResources().getStringArray(R.array.job_type11_name),
					getResources().getStringArray(R.array.job_type12_name),
					getResources().getStringArray(R.array.job_type13_name),
					getResources().getStringArray(R.array.job_type14_name),
					getResources().getStringArray(R.array.job_type15_name),
					getResources().getStringArray(R.array.job_type16_name),
					getResources().getStringArray(R.array.job_type17_name),
					getResources().getStringArray(R.array.job_type18_name),
					getResources().getStringArray(R.array.job_type19_name),
					getResources().getStringArray(R.array.job_type20_name),
					getResources().getStringArray(R.array.job_type21_name),
					getResources().getStringArray(R.array.job_type22_name),
					getResources().getStringArray(R.array.job_type23_name) };
			dbHelper.initJobTable(initJobIdArray, initJobNameArray);
		}

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

		TextView addFollowCityTextView = (TextView) findViewById(R.id.addFollowCity);
		addFollowCityTextView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						CitySelectActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable(SER_KEY, userFollow);
				// intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		TextView addFollowPostTextView = (TextView) findViewById(R.id.addFollowPost);
		addFollowPostTextView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						PostSelectActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable(SER_KEY, userFollow);
				// intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			this.finish();
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_follow, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
