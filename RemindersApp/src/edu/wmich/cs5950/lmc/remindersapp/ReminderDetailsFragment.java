package edu.wmich.cs5950.lmc.remindersapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReminderDetailsFragment extends Fragment{
	
	private TextView txtTitle;
	private TextView txtInterval;
	private String title;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		title = (String) getActivity().getIntent().getSerializableExtra(MyBroadcastReceiver.TITLE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup v = (ViewGroup) inflater.inflate(R.layout.reminder_details_fragment, container, false);
		
		txtTitle = (TextView) v.findViewById(R.id.txtTitle);
		txtTitle.setText(title);
		txtInterval = (TextView)v.findViewById(R.id.txtInterval);
		txtInterval.setText("your alarm is ready");
		
		return v;
	}

}
