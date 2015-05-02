package edu.wmich.cs5950.lmc.remindersapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

public class ReminderService extends IntentService{

	public ReminderService(){
		super("ReminderService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		int interval = (Integer) intent.getSerializableExtra(MyBroadcastReceiver.TIME_INTERVAL);
		SystemClock.sleep(interval * 1000);
	}

	

}
