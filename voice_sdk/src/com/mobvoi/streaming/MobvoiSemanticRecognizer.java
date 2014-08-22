// Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import android.app.Activity;

/**
 * Mobvoi sematic recognizer, it request the query analysis result from
 * streaming server query analysis task
 * 
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public class MobvoiSemanticRecognizer extends AbstractRecognizer{
  private String apikey;

  public MobvoiSemanticRecognizer(Activity activity, String apikey,
      RecognizerListener recognizerListener, String partner) {
    super(activity, recognizerListener, partner);
    this.apikey = apikey;
  }
  
  @Override
  protected AbstractWebSocket getWebSocket(List<AudioData> audioDataList) {
    SemanticWebSocket semanticWebSocket = new SemanticWebSocket(getURI(),
        audioDataList, apikey, getDeviceId(), getLocation(),
        getWebSocketListener(), getVersion(),
        getConnectTimeout(), getReadTimeout());
    semanticWebSocket.setIsSilenceDectection(getIsSilenceDetection());
    return semanticWebSocket;
  }
  
  private URI getURI() {
    try {
      String url = "ws://" + getServer() + "/websocket/semantic?partner=" + getPartner();
      URI uri = new URI(url);
      return uri;
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
