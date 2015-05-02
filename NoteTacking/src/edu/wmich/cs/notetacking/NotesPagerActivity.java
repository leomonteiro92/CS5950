package edu.wmich.cs.notetacking;

import java.util.ArrayList;
import java.util.UUID;

import edu.wmich.cs.notetacking.model.Note;
import edu.wmich.cs.notetacking.model.NoteSingleton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class NotesPagerActivity extends FragmentActivity{
	
	private ViewPager mViewPager;
	private ArrayList<Note> notes;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		notes = NoteSingleton.getInstance(getApplicationContext()).getNotes();
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return notes.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				Note n = notes.get(arg0);
				return NotesDetailsFragment.newInstance(n.getId());
			}
		});
		
		UUID noteId = (UUID) getIntent().getSerializableExtra(NotesDetailsFragment.EXTRA_NOTE_ID);
		for(int i = 0 ; i < notes.size(); i++){
			if(notes.get(i).getId().equals(noteId)){
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		

		
	}

}
