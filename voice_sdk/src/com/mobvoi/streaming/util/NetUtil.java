// Copyright 2012 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming.util;

/**
 * Net util, save some static variable
 * 
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public class NetUtil {
  
  /**
   * is debug flag
   */
  public final static boolean IS_DEBUG = false;
  
  /**
   * Http result code
   */
  public final static String RESULT_CODE = "result_code";
  /**
   * Net work result save key
   */
  public final static String RESULT_VALUE = "result_value";
  
  public final static String DEFAULT_PARTNER = "default";

  public final static int OK = 200;

  public final static int NOT_MODIFIED = 304;
  
  public final static String DEFAULT_STREAMING_SERVER =
      "streaming.mobvoi.com";
  
  public final static String DEFUG_SOURCE = "debug";
  
  public final static String RELEASE_SOURCE = "com.mobvoi.streaming.sdk";
  
  public final static String PCM_CONTENT_TYPE = 
      "audio/x-wav;codec=pcm;bit=16;rate=16000";
  
  public final static String SPEEX_CONTENT_TYPE = 
      "audio/x-speex-by-frame;rate=16000";
  
  /**
   * Connect timeout
   */
  public final static int CONNECT_TIMEOUT = 10000;
  /**
   * Read response timeout
   */
  public final static int READ_TIMEOUT = 30000;
  /**
   * Chunked buffer size
   */
  public final static int CHUNKED_MODE_BUFFER_SIZE = 1024;
  
  public static String getSource() {
    if (IS_DEBUG) {
      return DEFUG_SOURCE;
    } else {
      return RELEASE_SOURCE;
    }
  }
}
