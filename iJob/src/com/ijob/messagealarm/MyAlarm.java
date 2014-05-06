package com.ijob.messagealarm;

import com.example.ijob.MainActivity;
import com.example.ijob.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyAlarm extends Service {
	public static final int NOTIFICATION_ID = 0x0001;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE); 
			Notification notification = new Notification(
					R.drawable.ic_launcher, "有通知到来", System.currentTimeMillis());
			
			Intent notificationIntent = new Intent(this, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(this, "message", 
					intent.getExtras().getString("message").toString(),
					pendingIntent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_SOUND; //默认声音
            manager.notify(0, notification);//发起通知
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
