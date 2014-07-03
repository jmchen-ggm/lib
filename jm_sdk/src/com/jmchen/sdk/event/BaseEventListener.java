// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.event;

/**
 * Update At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public abstract class BaseEventListener {

	private final int priority;
	
	public BaseEventListener(final int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public abstract boolean callback(BaseEvent baseEvent); 
}
