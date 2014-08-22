// Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import com.mobvoi.streaming.util.SpeechUtil;

/**
 * Audio Data
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public class AudioData {
  public byte[] data = new byte[SpeechUtil.DEFAULT_BUFFER];
  public int size;
}
