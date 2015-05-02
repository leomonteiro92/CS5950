package edu.wmich.cs5950.lmc.searchableactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_NAME = "countries";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CAPITAL_NAME = "capital";

	private static final String DB_NAME = "countries.sqlite";
	private static final int VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME + " (" + COLUMN_ID 
				+ " integer primary key autoincrement," + COLUMN_NAME
				+ " varchar(100)," + COLUMN_CAPITAL_NAME + " varchar(100) )");
		ContentValues usa = new ContentValues();
		usa.put(COLUMN_ID, 1);
		usa.put(COLUMN_NAME, "United States");
		usa.put(COLUMN_CAPITAL_NAME, "Washington");
		db.insert(TABLE_NAME, null, usa);
		
		ContentValues bra = new ContentValues();
		bra.put(COLUMN_ID, 2);
		bra.put(COLUMN_NAME, "Brazil");
		bra.put(COLUMN_CAPITAL_NAME, "Brasilia");
		db.insert(TABLE_NAME, null, bra);
		
		ContentValues jpn = new ContentValues();
		jpn.put(COLUMN_ID, 3);
		jpn.put(COLUMN_NAME, "Japan");
		jpn.put(COLUMN_CAPITAL_NAME, "Tokyo");
		db.insert(TABLE_NAME, null, jpn);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}
