package com.example.ijob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ijob.fragment.MainFragment;
import com.ijob.fragment.MenuFragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.os.Build;

public class CitySelectActivity extends Activity {

	private GridView availableCityGridView;
	private SimpleAdapter gridViewAdapter;
	//private Follow userFollow;
	private List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_choices);
		
		availableCityGridView = (GridView) findViewById(R.id.citySelecting);
		//userFollow = (Follow) getIntent().getSerializableExtra(SetFollowActivity.SER_KEY);
		final Follow userFollow = (Follow) getApplication();
		ArrayList<String> cityChoicesList = userFollow.getCityChoices();
		for (int i = 0; i < cityChoicesList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("City",cityChoicesList.get(i));
			cityList.add(map);
		}
		gridViewAdapter = new SimpleAdapter(this,cityList,  
							                R.layout.city_selection_item, 
							                new String[] { "City" },  
							                new int[] { R.id.citySelectionItem });
		availableCityGridView.setAdapter(gridViewAdapter);
		Button submitButton = (Button)findViewById(R.id.submit);
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateFollow(availableCityGridView,userFollow);
				Intent intent = new Intent(v.getContext(),SetFollowActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void updateFollow(GridView v, Follow userFollow) {
		System.out.print(userFollow.getCityChoices().size());
		for (int i = 0; i < v.getChildCount(); i++) {
			View view=v.getChildAt(i);
			CheckBox checkbox = (CheckBox)view.findViewById(R.id.citySelectionItem);
			if (checkbox.isChecked()) {
				userFollow.removeCityChoiceFromList(checkbox.getText().toString());
				userFollow.addCityToFollowedList(checkbox.getText().toString());
			}
		}
		System.out.print(userFollow.getCityChoices().size());
		System.out.println("testinggggggggggggggggggggggggggggggggggggggg");
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
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
