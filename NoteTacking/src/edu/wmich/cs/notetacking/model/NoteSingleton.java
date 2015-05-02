package edu.wmich.cs.notetacking.model;

import java.util.ArrayList;
import java.util.UUID;

import edu.wmich.cs.notetacking.util.NotesJSONSerializer;


import android.content.Context;
import android.util.Log;

public class NoteSingleton {

	public static final String TAG = "NoteFrag";
	
	private static final String FILENAME = "notes.json";
	
	private static NoteSingleton allNotes;
	private Context appContext;
	private ArrayList<Note> notes;
	
	private NotesJSONSerializer serializer;

	private NoteSingleton(Context context) {
		appContext = context;
		serializer = new NotesJSONSerializer(appContext, FILENAME);
		try {
			notes = serializer.loadNotes();
		} catch (Exception e) {
			notes = new ArrayList<Note>();
			Log.d(TAG, "NoteSingleton - Error loading crimes: ", e);
		}
	}

	public static NoteSingleton getInstance(Context context) {
		if (allNotes == null) {
			allNotes = new NoteSingleton(context.getApplicationContext());
		}
		return allNotes;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public Note getById(UUID id) {
		for (Note n : notes) {
			if (n.getId().equals(id))
				return n;
		}
		return null;
	}

	public void addNote(Note n) {
		notes.add(n);
	}
	
	public void remove(Note n){
		notes.remove(n);
		saveNotes();
	}

	public boolean saveNotes() {
		try {
			serializer.saveNotes(this.notes);
			Log.d(TAG, "NoteSingleton - Successfully saved");
			return true;
		} catch (Exception e) {
			Log.d(TAG, "Error " + e);
			return false;
		}
	}

}
