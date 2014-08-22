// Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming.util;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * generate the deciceId
 * 
 * @author Jimmy Chen, <ggm1980303@live.cn>
 * @date 2013-07-30
 */
public class DeviceIdUtil {
  // TODO(jmchen): save the default device id to file
  public static final String DEFUALT_DEVICE_ID = StringUtil.newGuid();

  public static String generateDeviceId(Activity actvity) {
    try {
      DisplayMetrics metrics = new DisplayMetrics();
      actvity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
      WifiManager wifi = (WifiManager) actvity.getSystemService(Context.WIFI_SERVICE);
      TelephonyManager telephonyManager = (TelephonyManager) actvity
          .getSystemService(Context.TELEPHONY_SERVICE);
      StringBuffer buffer = new StringBuffer();
      buffer.append(Build.VERSION.RELEASE);
      buffer.append(Build.MODEL);
      buffer.append(wifi.getConnectionInfo().getMacAddress());
      buffer.append(telephonyManager.getDeviceId());
      buffer.append(metrics.heightPixels);
      buffer.append(metrics.widthPixels);
      return SHA1Util.SHA1(buffer.toString()).substring(0, 32);
    } catch (Exception e) {
      return DEFUALT_DEVICE_ID;
    }
  }
}
