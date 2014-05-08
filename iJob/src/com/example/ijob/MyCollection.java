package com.example.ijob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ijob.db.DBHelper;
import com.ijob.fragment.MainFragment;
import com.ijob.fragment.Personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MyCollection extends Activity{
	private	ImageView backImageView;
	private	Button cleanallButton;
	private	ListView myListView;
	private	DBHelper myDbHelper;
	private SimpleAdapter myAdapter;
	private List<Map<String, Object>> DataList;
	
	private int myid;
	private int delete_position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //声明隐藏标题
		setContentView(R.layout.personal_collection);
		backImageView = (ImageView)findViewById(R.id.personal_collection_back);
		cleanallButton = (Button)findViewById(R.id.personal_collection_cleanall);
		myListView = (ListView)findViewById(R.id.personal_collection_listview1);
		myDbHelper = new DBHelper(this);
		DataList = myDbHelper.getCollectionListViewAllItem();
		
		if (DataList == null) {
			DataList = new ArrayList<Map<String,Object>>();
		}
		
		myAdapter = new SimpleAdapter(this, DataList, R.layout.list_item, 
				new String[] {"message_title", "peoplefocus","company", "location" }, 
				new int[] { R.id.company,R.id.peoplefocus, R.id.job, R.id.workplace });
		myListView.setAdapter(myAdapter);
		
		
		//长按删除
		myListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				myDbHelper = new DBHelper(view.getContext());
				myid = (int)Integer.parseInt(DataList.get(position).get("id").toString());
				delete_position = position;
				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setMessage(getString(R.string.delete_hint));
				builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						
						if (myDbHelper.findCollectionListViewItemById(myid)) {
							myDbHelper.deleteCollectionListViewById(myid);
							DataList.remove(delete_position);
							myAdapter.notifyDataSetChanged();
						}
					}
				});
				
				builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						//do nothing to return to the menu scene
					}
				});
				AlertDialog exitDialog = builder.create();
				exitDialog.show();
				return false;
			}
		});
		//点击查看
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				String job_id;
				Log.i("position", " "+position);
				job_id = DataList.get(position).get("id").toString();
				Bundle bundle = new Bundle();
				bundle.putString("job_id", job_id);
				Intent intent = new Intent(view.getContext(), JobDetails.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
	            finish();
			}
		});
		cleanallButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				myDbHelper.deleteCollectionListViewTable();
				DataList.removeAll(DataList);
				myAdapter.notifyDataSetChanged();
				Toast.makeText(v.getContext(), "All items has been clean!", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
