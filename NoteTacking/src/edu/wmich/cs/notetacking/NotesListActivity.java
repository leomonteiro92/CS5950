package edu.wmich.cs.notetacking;

import android.support.v4.app.Fragment;


public class NotesListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new NotesListFragment();
	}

}
