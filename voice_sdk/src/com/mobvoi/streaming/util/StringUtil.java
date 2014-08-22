// Copyright 2012 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming.util;

import java.util.UUID;

/**
 * String util, use to check some strings whether satisfies us
 * 
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public class StringUtil {
  public final static String EMPTY_STRING = "";
  public final static String UNKNOW_STRING_ZH_CN = "不限";
  public final static String UNKNOW_STRING_EN = "undefined";

  public static boolean isNullOrEmpty(String str) {
    return str == null || str.trim().equals(EMPTY_STRING);
  }

  public static boolean notNullOrEmpty(String str) {
    return !isNullOrEmpty(str);
  }

  /**
   * if the string is null or empty, return 不限
   * 
   * @param str
   * @return
   * 
   * @author Jimmy Chen, <ggm19890303@live.cn>
   */
  public static String safeStringZhCn(String str) {
    // If the str is null or empty, return UNKNOW_STRING_ZH_CN
    if (isNullOrEmpty(str)) {
      return UNKNOW_STRING_ZH_CN;
    } else {
      return str;
    }
  }

  /**
   * if the string is null or empty, return undefined
   * 
   * @param str
   * @return
   * 
   * @author Jimmy Chen, <ggm19890303@live.cn>
   */
  public static String safeStringEn(String str) {
    // If the str is null or empty, return UNKNOW_STRING_EN
    if (isNullOrEmpty(str)) {
      return UNKNOW_STRING_EN;
    } else {
      return str;
    }
  }

  public static String notNullString(String str) {
    if (str == null) {
      return "";
    }
    return str;
  }

  public static String newGuid() {
    UUID uuid = UUID.randomUUID();
    String randomUUIDString = uuid.toString();
    return randomUUIDString;
  }
}
