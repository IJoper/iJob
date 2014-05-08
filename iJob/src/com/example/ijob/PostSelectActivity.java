package com.example.ijob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ijob.CheckBoxAdapter.ViewHolder;
import com.example.ijob.PostCheckBoxAdapter.PostViewHolder;
import com.ijob.db.DBHelper;
import com.ijob.db.Job_Item;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PostSelectActivity extends Activity{
	private ListView availablePostListView;
	private ListView availableProfessionListView;
	private SimpleAdapter ListViewAdapter;
	private PostCheckBoxAdapter PostCbAdapter;
	private List<Map<String, Object>> profession = new ArrayList<Map<String,Object>>();
	private DBHelper dbHelper = new DBHelper(this);
	private Map<String, List<Job_Item>> jobListMap = new HashMap<String, List<Job_Item>>();
	private String currentChoiceTypeString;
	List<Job_Item> postChoicesList = new ArrayList<Job_Item>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_choices);
		currentChoiceTypeString = "11";
		availableProfessionListView = (ListView) findViewById(R.id.availableProfession);
		
		//final List<Map<String, Object>> postList = new ArrayList<Map<String, Object>>();
		String []professionArray = getResources().getStringArray(R.array.job_id_name);
		ArrayList<String> professionList = new ArrayList<String>();
		getJobData();
		for (int i = 0; i < professionArray.length; i++) {
			professionList.add(professionArray[i]);
		}
		for (int i = 0; i < professionList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("profession",professionList.get(i));
			profession.add(map);
		}
		ListViewAdapter = new SimpleAdapter(this,profession,  
							                R.layout.profession, 
							                new String[] { "profession" },  
							                new int[] { R.id.profession });
		availableProfessionListView.setAdapter(ListViewAdapter);
		
		//TODO 关联职业职位
		
		availableProfessionListView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				postChoicesList.clear();
				int temp = (arg2+11);
			
				currentChoiceTypeString = ""+(temp);
				postChoicesList.addAll(jobListMap.get(""+currentChoiceTypeString));
				availablePostListView = (ListView)findViewById(R.id.postSelecting);
				PostCbAdapter.notifyDataSetChanged();
			}
		});
		
		availablePostListView = (ListView)findViewById(R.id.postSelecting);
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
                	Log.i("on post  item click", jobListMap.get(currentChoiceTypeString).size()+"");
					jobListMap.get(currentChoiceTypeString).get(position).setJobChoose(1);
					
				}
				else {
					
					jobListMap.get(currentChoiceTypeString).get(position).setJobChoose(0);
					
				}
			}
		});
		
		
		Button submitButton = (Button)findViewById(R.id.submitPostChoice);
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateData();
				Intent intent = new Intent(v.getContext(),SetFollowActivity.class);
				startActivity(intent);
			}
		});
		
	}
	public void getJobData() {
		int jobID[][] = {getResources().getIntArray(R.array.job_type11),
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
						getResources().getIntArray(R.array.job_type23)};
		
		for (int i = 11; i < 24; i++) {
			jobListMap.put(""+i, dbHelper.getJobListByIdArray(jobID[i-11]));
		}
		Log.i("jobListMap size = ", ""+jobListMap.size());
	}
	public void updateData() {
		dbHelper.updateJobTableByList(jobListMap);
	}

}
