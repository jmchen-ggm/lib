//Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.os.Build;

import com.mobvoi.streaming.location.Location;
import com.mobvoi.streaming.util.NetUtil;
import com.mobvoi.streaming.util.StringUtil;

/**
 * Onebox web socket, generate the onebox header
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 * @date 2013-07-31
 */
class OneboxWebSocket extends AbstractWebSocket {

  public OneboxWebSocket(URI serverUri, List<AudioData> audioDataList, String key, String deviceId,
      Location location, WebSocketListener webSocketListener, String version, 
      int connectTimeout, int readTimeout) {
    super(serverUri, audioDataList, key, deviceId, location, webSocketListener, version,
        connectTimeout, readTimeout);
  }

  @Override
  protected JSONObject getHeaderJSONObject() {
    JSONObject headerObject = new JSONObject();
    try {
      headerObject.put("signal", "start");
      headerObject.put("Accept-Encoding", "gzip");
      headerObject.put("Content-Type", NetUtil.SPEEX_CONTENT_TYPE);
      headerObject.put("appkey", getKey());
      headerObject.put("source", NetUtil.getSource());
      headerObject.put("argv0", getKey());
      headerObject.put("argv1", Build.MODEL + "-" + Build.VERSION.RELEASE + "-" + getVersion());
      headerObject.put("argv2", getLocation().getFormAddress());
      headerObject.put("argv3", getDeviceId());
      headerObject.put("argv4", StringUtil.newGuid());
      headerObject.put("device-id", getDeviceId());
      headerObject.put("device-address", URLEncoder.encode(getLocation().getFormAddress(), "UTF-8"));
      headerObject.put("device-time", String.valueOf(new Date().getTime()));
      headerObject.put("device-version",  getVersion());
      headerObject.put("sign", getDeviceId());
      if (getIsSilenceDetection()) {
        headerObject.put("silence_detection", "enable");
      }
    } catch (Exception e) {
    }
    return headerObject;
  }
}
