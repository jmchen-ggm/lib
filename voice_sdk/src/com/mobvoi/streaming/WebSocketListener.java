//Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import org.json.JSONObject;

/**
 * Web socket listener 
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 * @date 2013-07-31
 */
public interface WebSocketListener {
  public void onResult(JSONObject result);
  public void onSpeechEnd(String result);
  public void onError(ErrorCode errorCode);
}
