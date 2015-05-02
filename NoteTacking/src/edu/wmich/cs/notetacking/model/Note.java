package edu.wmich.cs.notetacking.model;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Note {

	public static final String TAG = "NoteFrag";

	private static final String JSON_ID = "id";
	private static final String JSON_SUBJECT = "subject";
	private static final String JSON_BODY = "body";

	private UUID id;
	private String subject;
	private String body;

	public Note() {
		this.id = UUID.randomUUID();
	}

	public Note(String subject, String body) {
		this.id = UUID.randomUUID();
		this.subject = subject;
		this.body = body;
	}

	public Note(JSONObject json) throws JSONException {
		id = UUID.fromString(json.getString(JSON_ID));
		subject = json.getString(JSON_SUBJECT);
		body = json.getString(JSON_BODY);

	}

	public UUID getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, id.toString());
		json.put(JSON_SUBJECT, subject);
		json.put(JSON_BODY, body);
		Log.d(TAG, "NOte - Json object created: " + json.toString());
		return json;
	}

}
