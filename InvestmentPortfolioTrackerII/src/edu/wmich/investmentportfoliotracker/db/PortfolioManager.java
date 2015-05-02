package edu.wmich.investmentportfoliotracker.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.wmich.investmentportfoliotracker.model.Portfolio;

public class PortfolioManager {

	private SQLiteDatabase db;
	
	public PortfolioManager(SQLiteOpenHelper db) {
		this.db = db.getWritableDatabase();
	}

	public long insertPortfolio(Portfolio p) {
		ContentValues values = new ContentValues();
		values.put(Portfolio.SYMBOL, p.getSymbol());
		values.put(Portfolio.NUMBER_OF_SHARES, p.getNumberOfShares());
		values.put(Portfolio.PRICE, p.getPrice());
		values.put(Portfolio.SUBTOTAL, p.getSubtotal());
		values.put(Portfolio.USER, p.getUser());
		return db.insert(Portfolio.TABLE_NAME, null, values);
	}
	
	public long updatePrice(Portfolio p){
		ContentValues values = new ContentValues();
		String[] args = {p.getId() + "" };
		return db.update(Portfolio.TABLE_NAME,
				values, Portfolio.ID + " =?", args);
	}

	public ArrayList<Portfolio> getPortfoliosByUser(long userId) {
		ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
		String[] args = { userId + "" };
		Cursor cursor = db.query(Portfolio.TABLE_NAME, Portfolio.COLUMNS,
				Portfolio.USER + " =?", args, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			portfolios.add(toPortfolio(cursor));
			cursor.moveToNext();
		}

		cursor.close();
		return portfolios;
	}
	
	public void deletePortfolio(Portfolio p){
		String[] args = {p.getId() + "" };
		db.delete(Portfolio.TABLE_NAME,
				Portfolio.USER + " =?", args);
	}

	public Portfolio getPortfolioById(long id) {
		String[] args = new String[] { id + "" };
		Cursor cursor = db.query(Portfolio.TABLE_NAME, Portfolio.COLUMNS,
				Portfolio.ID + " = ", args, null, null, null);
		cursor.moveToFirst();
		if (cursor.isBeforeFirst() || cursor.isAfterLast())
			return null;

		return toPortfolio(cursor);
	}

	private Portfolio toPortfolio(Cursor cursor) {
		Portfolio p = new Portfolio();
		p.setId(cursor.getLong(cursor.getColumnIndex(Portfolio.ID)));
		p.setSymbol(cursor.getString(cursor.getColumnIndex(Portfolio.SYMBOL)));
		p.setNumberOfShares(cursor.getInt(cursor
				.getColumnIndex(Portfolio.NUMBER_OF_SHARES)));
		p.setPrice(cursor.getDouble(cursor.getColumnIndex(Portfolio.PRICE)));
		p.setSubtotal(cursor.getDouble(cursor.getColumnIndex(Portfolio.SUBTOTAL)));
		
		p.setUser(cursor.getLong(cursor.getColumnIndex(Portfolio.USER)));
		
		return p;
	}
	

}
