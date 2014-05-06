package com.example.ijob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ijob.CheckBoxAdapter.ViewHolder;
import com.ijob.db.City_Item;
import com.ijob.db.DBHelper;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_choices);
		
		availableCityGridView = (GridView) findViewById(R.id.citySelecting);
		final List<City_Item> cityChoicesList = dbHelper.getCityList();
		for (int i = 0; i < cityChoicesList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("City",cityChoicesList.get(i).getCityName());
			cityList.add(map);
		}

		cBoxAdapter = new CheckBoxAdapter(cityChoicesList, this);
		availableCityGridView.setAdapter(cBoxAdapter);
		availableCityGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder holder = (ViewHolder) view.getTag();  
                // ¸Ä±äCheckBoxµÄ×´Ì¬  
                holder.checkBox.toggle();
			}
		});
		Button submitButton = (Button)findViewById(R.id.submit);
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateFollow(availableCityGridView, cityChoicesList);
				Intent intent = new Intent(v.getContext(),SetFollowActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void updateFollow(GridView v,List<City_Item> list) {
		for (int i = 0; i < v.getChildCount(); i++) {
			View view=v.getChildAt(i);
			CheckBox checkbox = (CheckBox)view.findViewById(R.id.citySelectionItem);
			if (checkbox.isChecked()) {
				list.get(i).setCityChoose(1);
			}
			else {
				list.get(i).setCityChoose(0);
			}
		}
		dbHelper.updateCityTableByList(list);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.city_select, menu);
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
