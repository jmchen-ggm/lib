// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk.event;

/**
 * Update At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public abstract class BaseEvent {
	/**
	 * �¼��ص�
	 */
	public Runnable callback = null;
	protected String id;
	/**
	 * �Ƿ���Ҫ������ȼ�˳��
	 */
	protected boolean order;
	
	public String getId() {
		return id;
	}
	
	public boolean isOrder() {
		return order;
	}
}
