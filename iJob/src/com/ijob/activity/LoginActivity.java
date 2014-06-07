package com.ijob.activity;

import com.example.ijob.R;
import com.ijob.messagealarm.BootBroadcastReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

public class LoginActivity extends Activity {
	private SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				preferences = getSharedPreferences("login",
						Context.MODE_PRIVATE);
				boolean loginTimes = preferences.getBoolean("first", true);
				// 判断是不是首次登录，
				if (loginTimes) {
					Intent intent1 = new Intent(LoginActivity.this,
							GuideActivity.class);
					startActivity(intent1);
					finish();
				} else {
					Intent intent2 = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent2);
					finish();
				}
			}
		}, 1000);

	}
	
//	@Override
//	protected void onDestroy() {  
//        unregisterReceiver(receiver);  
//    };
	
}
