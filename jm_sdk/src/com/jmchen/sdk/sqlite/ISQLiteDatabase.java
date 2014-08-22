// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Update At 2014年7月7日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月7日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public interface ISQLiteDatabase {
	public boolean isClose();
	
	/**
	 * Who use this method, who close the cursor 
	 */
	public Cursor query(final String table, final String[] columns,
			final String selection, final String[] selectionArgs, 
			final String groupBy, final String having, final String orderBy);
	
	/**
	 * Who use this method, who close the cursor 
	 */
	public Cursor rawQuery(final String sql, final String[] selectionArgs);
	
	/**
	 * return whether the sql exec successfully 
	 */
	public boolean execSQL(final String sql);
	
	/**
	 * return the row id of the record
	 */
	public long insert(final String table, final String primaryKey, final ContentValues values);
	
	/**
	 * Update some columns, return the row count which is effected by this sql 
	 */
	public int update(final String table, final ContentValues values, final String whereClause, 
			final String[] whereArgs);
	
	/**
	 * It equals update when the row exist, insert when row not exist 
	 */
	public long replace(final String table, final String primaryKey, final ContentValues values);
	
	/**
	 * Delete according where clauses
	 */
	public int delete(final String table, final String whereClause, final String[] whereArgs);
}
