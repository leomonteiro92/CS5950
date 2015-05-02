package edu.wmich.investmentportfoliotracker.db;

import edu.wmich.investmentportfoliotracker.model.Portfolio;
import edu.wmich.investmentportfoliotracker.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "porfolio.sqlite";
	private static final int VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + User.TABLE_NAME + " (" + User.ID
				+ " integer primary key autoincrement," + User.USERNAME
				+ " varchar(100)," + User.PASSWORD + " varchar(100) )");
		db.execSQL("create table " + Portfolio.TABLE_NAME + "(" + Portfolio.ID
				+ " integer primary key autoincrement," + Portfolio.SYMBOL
				+ " varchar(25)," + Portfolio.NUMBER_OF_SHARES + " integer,"
				+ Portfolio.USER + " integer references users(_id) )");
		ContentValues values = new ContentValues(2);
		values.put(User.ID, 1);
		values.put(User.USERNAME, "test");
		values.put(User.PASSWORD, "test");
		db.insert(User.TABLE_NAME, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Portfolio.TABLE_NAME);
		onCreate(db);
	}

}
