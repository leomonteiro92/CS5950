package edu.wmich.investmentportfoliotracker.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.wmich.investmentportfoliotracker.model.User;

public class UserManager {

	private SQLiteDatabase db;

	public UserManager(SQLiteOpenHelper db) {
		this.db = db.getWritableDatabase();
	}

	public long insertUser(User user) {
		ContentValues values = new ContentValues();
		values.put(User.USERNAME, user.getUsername());
		values.put(User.PASSWORD, user.getPassword());
		return db.insert(User.TABLE_NAME, null, values);
	}

	public ArrayList<User> getUsers() {
		ArrayList<User> allUsers = new ArrayList<User>();
		Cursor cursor = db.query(User.TABLE_NAME, User.COLUMNS, null, null, null, null,
				null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			allUsers.add(toUser(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		return allUsers;
	}

	public User getUserByUsername(String username) {
		String[] args = new String[] {username+"%"};
		Cursor cursor = db.query(User.TABLE_NAME, User.COLUMNS, User.USERNAME + " LIKE ?",
				args, null, null, null);
		cursor.moveToFirst();
		if(cursor.isBeforeFirst() || cursor.isAfterLast()) return null;

		return toUser(cursor);
	}
	
	private User toUser(Cursor cursor){
		User user = new User();
		user.setId(cursor.getLong(cursor
				.getColumnIndex(User.ID)));
		user.setUsername(cursor.getString(cursor
				.getColumnIndex(User.USERNAME)));
		user.setPassword(cursor.getString(cursor
				.getColumnIndex(User.PASSWORD)));
		return user;
	}

}
