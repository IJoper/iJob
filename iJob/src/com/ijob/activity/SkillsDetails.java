package com.ijob.activity;

import com.example.ijob.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SkillsDetails extends Activity {
	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skillsdetails);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		textView = (TextView) findViewById(R.id.skillsdetails_textview);
		String details[] = getResources().getStringArray(R.array.skillsDetails);
		Bundle bundle = getIntent().getExtras();
		int id = Integer.parseInt(bundle.getString("id"));
		Log.i("position id", "" + id);
		textView.setText(details[id]);
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
