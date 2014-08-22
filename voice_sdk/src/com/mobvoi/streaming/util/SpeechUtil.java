package com.mobvoi.streaming.util;

import android.media.AudioFormat;


public class SpeechUtil {
  public static final int DEFAULT_BUFFER = 1024 * 4;
  
  /**
   * Sample rate
   */
  public final static int SAMPLE_RATE = 16000;
  /**
   * Single channel
   */
  public final static int CHANNEL = AudioFormat.CHANNEL_CONFIGURATION_MONO;
  /**
   *  Each sample is 2 bytes
   */
  public final static int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
  /**
   * Num of seconds of data in the buff = 8192/sampleRate*2 
   * = 8192/(8000*2) = around half second
   */
  public final static int AUDIO_BUF_SIZE_IN_BYTES = 8192;
  /**
   * Connect timeout
   */
  public final static int CONNECT_TIMEOUT = 10000;
  /**
   * Read response timeout
   */
  public final static int READ_TIMEOUT = 30000;
  /**
   * Chunked buffer size, use defualt chunked buffer size
   */
  public final static int CHUNKED_MODE_BUFFER_SIZE = 0;
  
  // big Indian
  public static byte[] shortToBytes(short s) {
    byte[] temp = new byte[2];
    temp[1] = (byte) (s >> 8);
    temp[0] = (byte) s;
    return temp;
  }
  

  public static  short bytesToShort(byte[] b) {
    return (short) (b[1] & 0xff | (b[0] & 0xff) << 8);
  }
  
  public static byte[] shortsToBytes(short[] s) {
    byte[] b = new byte[s.length * 2];
    for(int i = 0; i < s.length; i++) {
      byte[] temp = shortToBytes(s[i]);
      b[i * 2] = temp[0];
      b[i * 2+ 1] = temp[1];
    }
    return b;
  }
  
  public static short[] bytesToShorts(byte[] b) {
    short[] s = new short[b.length / 2];
    for (int i = 0; i < s.length; i++) {
      byte[] temp = new byte[2];
      temp[0] = b[i * 2];
      temp[1] = b[i * 2 + 1];
      s[i] = bytesToShort(temp);
    }
    return s;
  }
}
