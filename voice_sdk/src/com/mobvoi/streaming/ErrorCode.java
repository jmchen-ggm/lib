package com.mobvoi.streaming;

public enum ErrorCode {
  SYSTEM_ERROR("系统错误"), 
  NETWORK_ERROR("网络错误"), 
  AUDIO_ERROR("录音设备错误"),
  NO_SPEECH("语音未能识别"),
  LONG_SPEECH("语音太长");

  private String value;

  private ErrorCode(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
