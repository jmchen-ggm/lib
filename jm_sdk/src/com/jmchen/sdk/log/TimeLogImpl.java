// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.log;

import java.util.ArrayList;

/**
 * Update At 2014年7月8日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月8日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public class TimeLogImpl implements ITimeLog {
	private final static String TAG = "Jmchen.TimeLogImpl";
	private ArrayList<TimeLogItem> logItemList = new ArrayList<TimeLogItem>();
	
	@Override
	public void start(int id) {
		TimeLogItem item = new TimeLogItem();
		item.id = id;
		item.timeStamp = System.currentTimeMillis();
		if (logItemList.contains(id)) {
			logItemList.clear();
		}
		logItemList.add(item);
	}

	@Override
	public void stage(int id, String commentFormat, Object... args) {
		if (logItemList.size() == 0) {
			Log.w(TAG, "TimeLog do not start");
		} else {
			TimeLogItem item = new TimeLogItem();
			item.id = id;
			item.timeStamp = System.currentTimeMillis();
			item.log = String.format(commentFormat, args);
			logItemList.add(item);
			int size = logItemList.size();
			print(logItemList.get(size - 1), logItemList.get(size - 2));
		}
	}
	
	private void print(TimeLogItem pre, TimeLogItem post) {
		Log.d(TAG, "[id: %d][UserTime: %d][Comment: %s]", post.id, post.timeStamp - pre.timeStamp, post.log);
	}

	@Override
	public void stop(int id) {
		logItemList.clear();
	}
	
	private class TimeLogItem {
		int id;
		String log;
		long timeStamp;
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TimeLogItem other = (TimeLogItem) obj;
			if (id != other.id)
				return false;
			return true;
		}
	}
}
