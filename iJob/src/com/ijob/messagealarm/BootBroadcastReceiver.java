package com.ijob.messagealarm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO 自动生成的方法存根
		Log.i("callBootBroadcastReceiver", "callBootBroadcastReceiver");
		if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
			Log.i("xxxx", "BootBroadcastReceiver");
			Intent checkIntent = new Intent(context, MessageActivity.class);
			context.startService(checkIntent);
		}
		if (intent.getAction().equals(ACTION)) {
			Log.i("#####", "BootBroadcastReceiver");
			Intent checkIntent = new Intent(context, MessageActivity.class);
			checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(checkIntent);
		}
		context.unregisterReceiver(this);
	}

}
