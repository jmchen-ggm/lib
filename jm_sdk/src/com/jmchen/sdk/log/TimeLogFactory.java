// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.log;

/**
 * Update At 2014��7��8�� By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014��7��8�� By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public class TimeLogFactory {

	public static ITimeLog craete() {
		return new TimeLogImpl();
	}
}
