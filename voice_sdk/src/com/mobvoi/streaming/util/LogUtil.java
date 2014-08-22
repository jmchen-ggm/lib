// Copyright 2012 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming.util;

import android.util.Log;

/**
 * Log util
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public class LogUtil {
  private final static boolean isDebug = false;
  private final static boolean isInfo = true;
  private final static boolean isWarn = true;
  private final static boolean isError = true;
  
  public static void d(String tag, String message) {
    if(isDebug) {
      Log.d(tag, message);
    }
  }
  
  public static void d(String tag, String message, Throwable e) {
    if(isDebug) {
      Log.d(tag, message, e);
    }
  }
  
  public static void i(String tag, String message) {
    if(isInfo) {
      Log.i(tag, message);
    }
  }
  
  public static void i(String tag, String message, Throwable e) {
    if(isInfo) {
      Log.i(tag, message, e);
    }
  }
  
  public static void w(String tag, String message) {
    if(isWarn) {
      Log.w(tag, message);
    }
  }
  
  public static void w(String tag, String message, Throwable e) {
    if(isWarn) {
      Log.w(tag, message, e);
    }
  }
  
  public static void e(String tag, String message) {
    if(isError) {
      Log.e(tag, message);
    }
  }
  
  public static void e(String tag, String message, Throwable e) {
    if(isError) {
      Log.e(tag, message, e);
    }
  }
}
