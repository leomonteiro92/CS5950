package edu.wmich.cs5950.lmc.remindersapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ReminderFragment extends Fragment {
	
	private EditText inputTitle;
	private EditText inputInterval;
	private EditText inputFrequency;
	private Button btnSetAlarm;
	private int interval;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup v = (ViewGroup) inflater.inflate(R.layout.reminder_fragment, container, false);
		
		inputTitle = (EditText) v.findViewById(R.id.inputTitle);
		inputInterval = (EditText) v.findViewById(R.id.inputInterval);
		inputFrequency = (EditText) v.findViewById(R.id.inputFrequency);
		btnSetAlarm = (Button) v.findViewById(R.id.btnSetAlarm);
		
		btnSetAlarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				interval = Integer.parseInt(inputInterval.getText().toString());
				
				Intent i = new Intent(getActivity(), MyBroadcastReceiver.class);
				i.putExtra(MyBroadcastReceiver.TITLE, inputTitle.getText().toString());
				i.putExtra(MyBroadcastReceiver.TIME_INTERVAL, inputInterval.getText().toString());
				i.putExtra(MyBroadcastReceiver.FREQUENCY, inputFrequency.getText().toString());
				
				PendingIntent pi = PendingIntent
						.getBroadcast(getActivity().getApplicationContext(), 2, i, 0);
				
				AlarmManager am = (AlarmManager) getActivity().getSystemService(Activity.ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (interval * 1000), pi);
			}
		});
		
		return v;
	}

}
