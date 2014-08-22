// Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import org.json.JSONObject;

/**
 * Recognizer Listener
 * OnResult return the normal result
 * OnError @see ErrorCode
 * OnVolumn Callback of voice volumn the range is 0~100
 * OnCancel cancel the recognizer task
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public interface RecognizerListener {
  
  public void onResult(JSONObject result);
  
  public void onSpeechEnd(String result);

  public void onError(ErrorCode errorCode);

  public void onVolumn(double volumn);

  public void onCancel();
}
