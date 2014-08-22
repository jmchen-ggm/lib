// Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import android.app.Activity;

/**
 * Mobvoi Onebox recognizer, it create the onebox web task
 * request the result from streaming server onebox task
 * 
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public class MobvoiOneboxRecognizer extends AbstractRecognizer{
  private String appkey;

  public MobvoiOneboxRecognizer(Activity activity, String appkey,
      RecognizerListener recognizerListener, String partner) {
    super(activity, recognizerListener, partner);
    this.appkey = appkey;
  }
  
  @Override
  protected AbstractWebSocket getWebSocket(List<AudioData> audioDataList) {
    OneboxWebSocket oneboxWebSocket = new OneboxWebSocket(getURI(), 
        audioDataList, appkey, getDeviceId(),
        getLocation(), getWebSocketListener(), getVersion(),
        getConnectTimeout(), getReadTimeout());
    oneboxWebSocket.setIsSilenceDectection(getIsSilenceDetection());
    return oneboxWebSocket;
  }
  
  private URI getURI() {
    try {
      String url = "ws://" + getServer() + "/websocket/onebox?partner=" + getPartner();
      URI uri = new URI(url);
      return uri;
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
