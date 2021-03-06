// Copyright 2014 By jiaminchen, <jmchen.ggm@gmail.com>
package com.jmchen.sdk;

/**
 * Update At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 * Create At 2014年7月3日 By jiaminchen, <jmchen.ggm@gmail.com>
 **/
public class Util {

	public final static boolean isNullOrEmpty(String str) {
		if (str != null && str.trim().length() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public final static String notNullStr(String target, String default_) {
		if (target == null) {
			return default_;
		} else {
			return target;
		}
	}
	
	public static JMStack getStack() {
		return new JMStack();
	}
	
	public static class JMStack {

		@Override
		public String toString() {
			return getStack(true);
		}

		public static String getStack(final boolean printLine) {
			StackTraceElement[] stes = new Throwable().getStackTrace();
			if ((stes == null) || (stes.length < 4)) {
				return "";
			}
			StringBuilder t = new StringBuilder();

			for (int i = 3; i < stes.length; i++) {
				if (stes[i].getClassName().contains(BaseConstants.LOG_CLASS)) {
					continue;
				}
				t.append("[");
				t.append(stes[i].getClassName());
				t.append(":");
				t.append(stes[i].getMethodName());
				if (printLine) {
					t.append("(" + stes[i].getLineNumber() + ")]");
				} else {
					t.append("]");
				}
			}
			return t.toString();
		}

	}
}
