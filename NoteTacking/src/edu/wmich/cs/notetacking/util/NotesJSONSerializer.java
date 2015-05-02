package edu.wmich.cs.notetacking.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import edu.wmich.cs.notetacking.model.Note;

import android.content.Context;
import android.util.Log;

public class NotesJSONSerializer {

	public static final String TAG = "NoteFrag";

	private Context mContext;
	private String mFilename;

	public NotesJSONSerializer(Context c, String f) {
		mContext = c;
		mFilename = f;
	}

	public ArrayList<Note> loadNotes() throws IOException, JSONException {
		ArrayList<Note> notes = new ArrayList<Note>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
					.nextValue();
			for (int i = 0; i < array.length(); i++) {
				notes.add(new Note(array.getJSONObject(i)));
			}
		} catch (Exception e) {

		} finally {
			if (reader != null)
				reader.close();
		}
		return notes;
	}

	public void saveNotes(ArrayList<Note> notes) throws JSONException,
			IOException {
		JSONArray array = new JSONArray();
		for (Note n : notes) {
			array.put(n.toJSON());
		}

		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
			Log.d(TAG, "NJS - Saved " + array.length() + " notas");
		} finally {
			if (writer != null)
				writer.close();
		}
	}

}
