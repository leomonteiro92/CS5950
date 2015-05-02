package edu.wmich.cs5950.lmc.remindersapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

public class MyBroadcastReceiver extends BroadcastReceiver{
	
	public static final String TITLE = "title";
	public static final String TIME_INTERVAL = "interval";
	public static final String FREQUENCY = "frequency";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String title = (String) intent.getSerializableExtra(TITLE);
		
		Notification n = new NotificationCompat.Builder(context)
		.setTicker(title)
		.setSmallIcon(android.R.drawable.ic_menu_info_details)
		.setContentTitle("" + SystemClock.elapsedRealtime()/60)
		.setAutoCancel(true).build();
		
		NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);		
		nm.notify(0, n);
		context.startService(new Intent(context, ReminderService.class));
	}

}
