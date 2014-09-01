// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Update At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public class SQLiteDb implements ISQLiteDatabase {
	private final static String TAG = "Jmchen.SQLiteDb";
	
	private Callback callback;
	private JMSQLiteDb db;
	
	public interface Callback {
		public void preClose();
		public void postBeginTransation();
		public void postEndTransation();
	}
	
	public SQLiteDb(Callback callback) {
		this.callback = callback;
	}
	
	@Override
	public boolean isClose() {
		return false;
	}

	@Override
	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor rawQuery(String sql, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execSQL(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long insert(String table, String primaryKey, ContentValues values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long replace(String table, String primaryKey, ContentValues values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String table, String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
