// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.event;

import android.os.Looper;

/**
 * Update At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public interface IEventPool {

	public void addListener(final String eventId, final BaseEventListener baseEventListener);
	
	public void removeListener(final String eventId, final BaseEventListener baseEventListener);
	
	public boolean hasListenerForEvent(final BaseEvent baseEvent);
	
	public boolean hasListenerForEventId(final String eventId);
	
	public boolean containsListenerForEvent(final BaseEvent baseEvent, final BaseEventListener baseEventListener);
	
	public boolean containsListenerForEventId(final String eventId, final BaseEventListener baseEventListener);
	
	public boolean publish(final BaseEvent baseEvent);
	
	public void asyncPublic(final BaseEvent baseEvent, Looper looper);
	
}
