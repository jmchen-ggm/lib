// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jmchen.sdk.Util;
import com.jmchen.sdk.log.Log;

/**
 * Update At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public class JMSQLiteDb {

	private final static String TAG = "Jmchen.JMSQLiteDB";
	private SQLiteDatabase sqliteDb;
	private String dbCachePath;
	
	public static JMSQLiteDb createDB(String dbCachePath) {
		JMSQLiteDb db = new JMSQLiteDb();
		if (Util.isNullOrEmpty(dbCachePath)) {
			return null;
		} else {
			db.sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbCachePath, null);
			db.dbCachePath = dbCachePath;
		}
		return db.sqliteDb == null ? null : db;
	}
	
	public String getPath() {
		if (checkDB()) {
			return sqliteDb.getPath();
		} else {
			return null;
		}
	}
	
	public boolean isOpen() {
		if (checkDB()) {
			return sqliteDb.isOpen();
		} else {
			return false;
		}
	}
	
	public void close() {
		if (sqliteDb.isOpen()) {
			sqliteDb.close();
		}
		sqliteDb = null;
	}
	
	public Cursor query(final String table, final String[] columns,
			final String selection, final String[] selectionArgs, 
			final String groupBy, final String having, final String orderBy) {
		if (checkDB()) {
			return sqliteDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		} else {
			return EmptyCursor.INSTANCE;
		}
	}
	
	public Cursor rawQuery(final String sql, final String[] selectionArgs) {
		if (checkDB()) {
			return sqliteDb.rawQuery(sql, selectionArgs);
		} else {
			return EmptyCursor.INSTANCE;
		}
	}
	
	public void execSQL(final String sql) {
		if (checkDB()) {
			sqliteDb.execSQL(sql);
		}
	}
	
	public long insert(final String table, final String primaryKey, final ContentValues values) {
		if (checkDB()) {
			return sqliteDb.insert(table, primaryKey, values);
		} else {
			return -1;
		}
	}
	
	public int update(final String table, final ContentValues values, final String whereClause, 
			final String[] whereArgs) {
		if (checkDB()) {
			return sqliteDb.update(table, values, whereClause, whereArgs);
		} else {
			return -1;
		}
	}

	public long replace(final String table, final String primaryKey, final ContentValues values) {
		if (checkDB()) {
			return sqliteDb.replace(table, primaryKey, values);
		} else {
			return -1;
		}
	}
	
	public int delete(final String table, final String whereClause, final String[] whereArgs) {
		if (checkDB()) {
			return sqliteDb.delete(table, whereClause, whereArgs);
		} else {
			return -1;
		}
	}
	
	/**
	 * if db is null, return false; else return true
	 */
	private boolean checkDB() {
		boolean result = sqliteDb == null;
		if (result) {
			Log.w(TAG, "sqlite database is null, cache path = %s", dbCachePath);
		}
		return !result;
	}
	
	public void beginTransaction() {
		if (checkDB()) {
			sqliteDb.beginTransaction();
		}
	}
	
	public void endTransaction() {
		if (checkDB()) {
			sqliteDb.setTransactionSuccessful();
			sqliteDb.endTransaction();
		}
	}
}
