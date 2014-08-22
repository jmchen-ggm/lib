//Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import java.net.URI;
import java.util.List;

import org.json.JSONObject;

import android.os.Build;

import com.mobvoi.streaming.location.Location;
import com.mobvoi.streaming.util.LogUtil;
import com.mobvoi.streaming.util.NetUtil;
import com.mobvoi.streaming.util.StringUtil;

/**
 * generate semantic web socket header
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 * @date 2013-7-31
 */
class SemanticWebSocket extends AbstractWebSocket {

  private final static String TAG = SemanticWebSocket.class.getName();

  public SemanticWebSocket(URI serverUri, List<AudioData> audioDataList, String key,
      String deviceId, Location location, WebSocketListener webSocketListener,
      String version, int connectTimeout, int readTimeout) {
    super(serverUri, audioDataList, key, deviceId, location, 
        webSocketListener, version, connectTimeout, readTimeout);
  }
  
  @Override
  protected JSONObject getHeaderJSONObject() {
    JSONObject headerObject = new JSONObject();
    try {
      headerObject.put("signal", "start");
      headerObject.put("Accept-Encoding", "gzip");
      headerObject.put("Content-Type", NetUtil.SPEEX_CONTENT_TYPE);
      headerObject.put("apikey", getKey());
      headerObject.put("source", NetUtil.getSource());
      headerObject.put("argv0", getKey());
      headerObject.put("argv1", Build.MODEL + "-" + Build.VERSION.RELEASE + "-" + getVersion());
      headerObject.put("argv2", getLocation().getFormAddress());
      headerObject.put("argv3", getDeviceId());
      headerObject.put("argv4", StringUtil.newGuid());
      headerObject.put("param0", getLocation().getCity());
      if (getIsSilenceDetection()) {
        headerObject.put("silence_detection", "enable");
      }
    } catch (Exception e) {
      LogUtil.e(TAG, e.getMessage(), e);
    }
    return headerObject;
  }
}
