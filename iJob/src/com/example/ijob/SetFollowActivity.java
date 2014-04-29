package com.example.ijob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ijob.listview.XListView;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.os.Build;

public class SetFollowActivity extends Activity {

	private GridView followedCityGridView;
	private GridView followedPostGridView;
	private SimpleAdapter gridViewAdapter;
	private SimpleAdapter postGridViewAdapter;
	//private Follow userFollow = new Follow();
	public  final static String SER_KEY = "com.example.ijob";  
	private List<Map<String, Object>> followedItemList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> followedPostList = new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.follow_label);

		
		
		followedCityGridView = (GridView) findViewById(R.id.followedCityGridView);
		final Follow userFollow = (Follow) getApplication();
		ArrayList<String> followedCityList = userFollow.getFollowedCity();
		
		System.out.print(userFollow.getCityChoices().size());
		System.out.println("testing222222222222222222222222222222222222222222222");
		for (int i = 0; i < followedCityList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("followedItem",followedCityList.get(i));
			followedItemList.add(map);
		}
		gridViewAdapter = new SimpleAdapter(this,followedItemList,  
							                R.layout.follow_item, 
							                new java.lang.String[] { "followedItem" },  
							                new int[] { R.id.followedItem });
		followedCityGridView.setAdapter(gridViewAdapter);
		
		
		followedPostGridView = (GridView) findViewById(R.id.followedPostGridView);
		String[] followedPost = userFollow.getFollowedPost();
		for (int i = 0; i < followedPost.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("followedPost",followedPost[i]);
			followedPostList.add(map);
		}
		postGridViewAdapter = new SimpleAdapter(this,followedPostList,  
							                R.layout.follow_item, 
							                new java.lang.String[] { "followedPost" },  
							                new int[] { R.id.followedItem });
		followedPostGridView.setAdapter(postGridViewAdapter);
		
		TextView addFollowCityTextView = (TextView) findViewById(R.id.addFollowCity);
		addFollowCityTextView.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),CitySelectActivity.class);
				//Bundle bundle = new Bundle();
				//bundle.putSerializable(SER_KEY, userFollow);
				//intent.putExtras(bundle); 
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_set_follow,
//					container, false);
//			return rootView;
//		}
//	}

}
