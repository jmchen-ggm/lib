// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.event;

/**
 * Update At 2014年7月4日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月4日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public class EventCenter {

	private static IEventPool impl = null;
	
	public static IEventPool getEventPool() {
		if (impl == null) {
			impl = new BaseEventPool();
		}
		return impl;
	}
}
