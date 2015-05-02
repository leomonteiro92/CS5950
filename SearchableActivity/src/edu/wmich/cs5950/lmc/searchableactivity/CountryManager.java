package edu.wmich.cs5950.lmc.searchableactivity;

import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CountryManager {
	
	private SQLiteDatabase db;
	
	public CountryManager(SQLiteOpenHelper db){
		this.db = db.getWritableDatabase();
	}
	
	public ArrayList<Country> getCountryByName(String name) {
		ArrayList<Country> countries = new ArrayList<Country>();
		String[] args = new String[] {name+"%"};
		Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, new String[] {
				DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_CAPITAL_NAME
		}, DatabaseHelper.COLUMN_NAME + " LIKE ?",
				args, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			countries.add(toCountry(cursor));
			cursor.moveToNext();
		}

		cursor.close();
		return countries;
	}
	
	public ArrayList<Country> getCountries() {
		ArrayList<Country> countries = new ArrayList<Country>();
		Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, new String[]{
				DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_CAPITAL_NAME
		}, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			countries.add(toCountry(cursor));
			cursor.moveToNext();
		}

		cursor.close();
		return countries;
	}
	
	private Country toCountry(Cursor cursor){
		Country c = new Country();
		c.setId(cursor.getInt(cursor
				.getColumnIndex(DatabaseHelper.COLUMN_ID)));
		c.setName(cursor.getString(cursor
				.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
		c.setCapitalName(cursor.getString(cursor
				.getColumnIndex(DatabaseHelper.COLUMN_CAPITAL_NAME)));
		return c;
	}

}
