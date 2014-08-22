//Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

/**
 * Audio record listener
 *
 * @author Jimmy Chen, <jmchen@mobvoi.com>
 * @date 2013-8-3
 */
public interface AudioRecordListener {
  public void onVolumn(double volumn);
  public void OnError(ErrorCode errorCode);
}
