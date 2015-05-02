package edu.wmich.cs5950.lmc.remindersapp;

import android.support.v4.app.Fragment;

public class ReminderDetailsActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		return new ReminderDetailsFragment();
	}

}
