package com.ijob.messagealarm;

import com.example.ijob.R;
import com.ijob.activity.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;

public class MyAlarm extends Service {
	public static final int NOTIFICATION_ID = 0x0001;
	private SharedPreferences preferences;
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("callMyAlarm", "callMyAlarm");
		if (intent != null) {
			NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
			Notification notification = new Notification(
					R.drawable.logo, "有通知到来", System.currentTimeMillis());
			preferences = getSharedPreferences("login",Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putBoolean("changeSet", true);
			editor.commit();

			Intent notificationIntent = new Intent(this, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(this, "message", intent.getExtras()
					.getString("message").toString(), pendingIntent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_SOUND; // 默认声音
			manager.notify(0, notification);// 发起通知
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("ondestroy", "onDestroy() executed");
	}
}
