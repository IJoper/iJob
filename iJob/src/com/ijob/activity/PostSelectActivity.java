package com.ijob.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ijob.R;
import com.ijob.activity.CheckBoxAdapter.ViewHolder;
import com.ijob.activity.PostCheckBoxAdapter.PostViewHolder;
import com.ijob.db.DBHelper;
import com.ijob.db.Job_Item;

import android.Manifest.permission;
import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PostSelectActivity extends Activity {
	private ListView availablePostListView;
	private ListView availableProfessionListView;
	private SimpleAdapter ListViewAdapter;
	private PostCheckBoxAdapter PostCbAdapter;
	SharedPreferences preferences;
	private List<Map<String, Object>> profession = new ArrayList<Map<String, Object>>();
	private DBHelper dbHelper = new DBHelper(this);
	private Map<String, List<Job_Item>> jobListMap = new HashMap<String, List<Job_Item>>();
	private String currentChoiceTypeString;
	List<Job_Item> postChoicesList = new ArrayList<Job_Item>();
	private ProgressDialog progressDialog;
	private int saveFinish = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_choices);
		preferences = getSharedPreferences("login",
				Context.MODE_PRIVATE);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
		currentChoiceTypeString = "11";
		availableProfessionListView = (ListView) findViewById(R.id.availableProfession);

		// final List<Map<String, Object>> postList = new ArrayList<Map<String,
		// Object>>();
		String[] professionArray = getResources().getStringArray(
				R.array.job_id_name);
		ArrayList<String> professionList = new ArrayList<String>();
		getJobData();
		for (int i = 0; i < professionArray.length; i++) {
			professionList.add(professionArray[i]);
		}
		for (int i = 0; i < professionList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("profession", professionList.get(i));
			profession.add(map);
		}
		ListViewAdapter = new SimpleAdapter(this, profession,
				R.layout.profession, new String[] { "profession" },
				new int[] { R.id.profession });
		availableProfessionListView.setAdapter(ListViewAdapter);

		// TODO 关联职业职位

		availableProfessionListView
				.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						postChoicesList.clear();
						int temp = (arg2 + 11);

						currentChoiceTypeString = "" + (temp);
						postChoicesList.addAll(jobListMap.get(""
								+ currentChoiceTypeString));
						availablePostListView = (ListView) findViewById(R.id.postSelecting);
						PostCbAdapter.notifyDataSetChanged();
					}
				});

		availablePostListView = (ListView) findViewById(R.id.postSelecting);
		postChoicesList.addAll(jobListMap.get(currentChoiceTypeString));
		Log.i("on init999999999 item click", currentChoiceTypeString);
		if (postChoicesList == null) {
			Log.i("postchoices", "null");
		}
		PostCbAdapter = new PostCheckBoxAdapter(postChoicesList, this);
		availablePostListView.setAdapter(PostCbAdapter);
		availablePostListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PostViewHolder holder = (PostViewHolder) view.getTag();
				// 改变CheckBox的状态
				holder.checkBox.toggle();
				if (holder.checkBox.isChecked()) {
					Log.i("on post  item click",
							jobListMap.get(currentChoiceTypeString).size() + "");
					jobListMap.get(currentChoiceTypeString).get(position)
							.setJobChoose(1);

				} else {
					jobListMap.get(currentChoiceTypeString).get(position)
							.setJobChoose(0);
				}
			}
		});

		
	}
	
	public void updateData() {
		final Intent intent = new Intent(this, SetFollowActivity.class);
		UpdateData updateData = new UpdateData();
		Thread thread = new Thread(updateData);
		thread.start();
		progressDialog = new ProgressDialog(PostSelectActivity.this);
		progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
		progressDialog.setMessage("保存并提交中...");
		progressDialog.setProgress(100);
		progressDialog.setIndeterminate(false);
		progressDialog.show();
		new Thread() {
			public void run() {
				try {
					while (saveFinish <= 100) {
						progressDialog.setProgress(saveFinish);
						Thread.sleep(100);
					}
				} catch (Exception e) {
					// TODO: handle exception
					progressDialog.cancel();
					startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					finish();
					
				}
				if (saveFinish > 100) {
					progressDialog.cancel();
					startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					finish();
				}
			}
		}.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.city_select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this,SetFollowActivity.class);
			
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			this.finish();
			break;
		case R.id.saveCityChoice:
			preferences.getBoolean("changeSet", true);
			Editor editor = preferences.edit();
			editor.putBoolean("changeSet", true);
			editor.commit();
			Log.i("PostSelect", Boolean.toString(preferences.getBoolean("changeSet", true)));
			updateData();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getJobData() {
		int jobID[][] = { getResources().getIntArray(R.array.job_type11),
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

		for (int i = 11; i < 24; i++) {
			jobListMap.put("" + i, dbHelper.getJobListByIdArray(jobID[i - 11]));
		}
		Log.i("jobListMap size = ", "" + jobListMap.size());
	}

	class UpdateData implements Runnable {

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			dbHelper.deleteJobTable();

			// dbHelper.updateJobTableByList(jobListMap);
			for (int i = 11; i < 24; i++) {
				for (int j = 0; j < jobListMap.get("" + i).size(); j++) {
					dbHelper.addJobByItem(jobListMap.get("" + i).get(j));
				}
				saveFinish += 8;
			}
			saveFinish += 8;
		}

	}
}
