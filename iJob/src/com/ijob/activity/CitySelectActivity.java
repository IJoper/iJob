package com.ijob.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ijob.R;
import com.ijob.activity.CheckBoxAdapter.ViewHolder;
import com.ijob.db.City_Item;
import com.ijob.db.DBHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class CitySelectActivity extends Activity {

	private GridView availableCityGridView;
	private CheckBoxAdapter cBoxAdapter;
	private List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
	DBHelper dbHelper = new DBHelper(this);
	SharedPreferences preferences;
	private ProgressDialog progressDialog;
	private int saveFinish = 0;
	List<City_Item> cityChoicesList = new ArrayList<City_Item>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_choices);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		availableCityGridView = (GridView) findViewById(R.id.citySelecting);
		cityChoicesList = dbHelper.getCityList();
		for (int i = 0; i < cityChoicesList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("City", cityChoicesList.get(i).getCityName());
			cityList.add(map);
		}

		cBoxAdapter = new CheckBoxAdapter(cityChoicesList, this);
		availableCityGridView.setAdapter(cBoxAdapter);
		availableCityGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder holder = (ViewHolder) view.getTag();
				// 改变CheckBox的状态
				holder.checkBox.toggle();
			}
		});
		
	}

	public void updateFollow(GridView v, List<City_Item> list) {
		for (int i = 0; i < v.getChildCount(); i++) {
			View view = v.getChildAt(i);
			CheckBox checkbox = (CheckBox) view
					.findViewById(R.id.citySelectionItem);
			if (checkbox.isChecked()) {
				list.get(i).setCityChoose(1);
			} else {
				list.get(i).setCityChoose(0);
			}
		}
		final Intent intent = new Intent(v.getContext(),
				SetFollowActivity.class);
		UpdateCityTable updateCityTable = new UpdateCityTable(list);
		Thread thread = new Thread(updateCityTable);
		thread.start();
		progressDialog = new ProgressDialog(CitySelectActivity.this);
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

	public class UpdateCityTable implements Runnable {
		List<City_Item> list;

		UpdateCityTable(List<City_Item> _list) {
			this.list = _list;
		}

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			dbHelper.deleteCityTable();
			int size = list.size();
			for (int i = 0; i < list.size(); i++) {
				dbHelper.addCityByItem(list.get(i));
				saveFinish += 100 / size + 1;
			}
			saveFinish += 100 / size + 1;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.city_select, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.saveCityChoice) {
			preferences = getSharedPreferences("login",Context.MODE_PRIVATE);
			preferences.getBoolean("changeSet", true);
			Editor editor = preferences.edit();
			editor.putBoolean("changeSet", true);
			editor.commit();
			Log.i("citySelect", Boolean.toString(preferences.getBoolean("changeSet", true)));
			updateFollow(availableCityGridView, cityChoicesList);
			return true;
		}
		if (id == android.R.id.home) {
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
