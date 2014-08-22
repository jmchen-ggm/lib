// Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;

import com.mobvoi.streaming.location.Location;
import com.mobvoi.streaming.util.DeviceIdUtil;
import com.mobvoi.streaming.util.LogUtil;
import com.mobvoi.streaming.util.NetUtil;

/**
 * Abstract Recognizer, 
 *
 * @author Jimmy Chen, <jmchen@mobvoi.com>
 * @date 2013-7-31
 */
public abstract class AbstractRecognizer {
  private final static String TAG = AbstractRecognizer.class.getName();
  
  private final static int CONNECT_DEFAULT_TIMEOUT = 10000;
  
  private final static int READ_DEFAULT_TIMEOUT = 30000;
  
  /**
   * Recognizer listener
   */
  private RecognizerListener recognizerListener;
  
  /**
   * abstract web task, send the voice data to streaming server
   */
  private AbstractWebSocket abstractWebSocket;
  
  /**
   * Record voice task
   */
  private AudioRecordTask audioRecordTask;
  
  /**
   * deviceId
   */
  private String deviceId;
  
  /**
   * app version
   */
  private String version;
  
  /**
   * connect to server timeout
   */
  private int connectTimeout;
  
  /**
   * read result from server timeout
   */
  private int readTimeout;
  
  /**
   * is need to enable the silence detection
   */
  private boolean isSilenceDetection;
  
  /**
   * server address
   */
  private String server;
  
  /**
   * partner
   */
  private String partner;
  
  /**
   * location info
   */
  private Location location;
  
  public AbstractRecognizer(Activity activity, RecognizerListener recognizerListener,
      String partner) {
    this.recognizerListener = recognizerListener;
    this.deviceId = DeviceIdUtil.generateDeviceId(activity);
    try {
      this.version = activity.getPackageManager().
          getPackageInfo(activity.getPackageName(), 0).versionName;
    } catch (NameNotFoundException e) {
      this.version = "1.0.0";
    }
    this.connectTimeout = CONNECT_DEFAULT_TIMEOUT;
    this.readTimeout = READ_DEFAULT_TIMEOUT;
    this.isSilenceDetection = true;
    this.partner =  partner;
    this.server = NetUtil.DEFAULT_STREAMING_SERVER;
    this.location = new Location();
  }
  
  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }
  
  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }
  
  public void setSilenceDetection(boolean isSilenceDetection) {
    this.isSilenceDetection = isSilenceDetection;
  }
  
  /**
   * set the device address, it must be updated
   * by each query
   *
   * @param address
   */
  public void setDeviceLocation(Location location) {
    this.location = location;
  }
  
  public void setServer(String server) {
    this.server = server;
  }

  protected boolean getIsSilenceDetection() {
    return this.isSilenceDetection;
  }
  
  protected String getDeviceId() {
    return this.deviceId;
  }
  
  protected String getVersion() {
    return this.version;
  }
  
  protected WebSocketListener getWebSocketListener() {
    return this.webSocketListener;
  }
  
  protected int getConnectTimeout() {
    return this.connectTimeout;
  }
  
  protected int getReadTimeout() {
    return this.readTimeout;
  }
  
  protected String getPartner() {
    return this.partner;
  }
  
  protected String getServer() {
    return this.server;
  }
  
  protected Location getLocation() {
    return this.location;
  }
  
  public void startSpeech() {
    LogUtil.d(TAG, "startSpeech");
    
    // init the synchronized list
    List<AudioData> audioDataList = Collections.synchronizedList(new LinkedList<AudioData>());

    // init the streaming record task
    audioRecordTask = new AudioRecordTask(audioDataList);
    
    abstractWebSocket = getWebSocket(audioDataList);

    audioRecordTask.setAudioRecordListener(audioRecordListener);

    LogUtil.d(TAG, "start execute streamRecordTask");
    audioRecordTask.start();
    
    LogUtil.d(TAG, "start execute abstractWebTask");
    abstractWebSocket.start();
  }
  
  private Handler handler = new Handler();
  
  private AudioRecordListener audioRecordListener = new AudioRecordListener() {
    @Override
    public void onVolumn(final double volumn) {
      handler.post(new Runnable() {
        @Override
        public void run() {
          recognizerListener.onVolumn(volumn);
        }
      });
    }
    
    @Override
    public void OnError(final ErrorCode errorCode) {
      audioRecordTask.stop();
      abstractWebSocket.cancel();
      handler.post(new Runnable() {
        @Override
        public void run() {
          recognizerListener.onError(errorCode);
        }
      });
    }
  };
  
  private WebSocketListener webSocketListener = new WebSocketListener() {
    @Override
    public void onSpeechEnd(final String result) {
      // stop to record
      audioRecordTask.stop();
      abstractWebSocket.stopSendData();
      handler.post(new Runnable() {
        @Override
        public void run() {
          recognizerListener.onSpeechEnd(result);
        }
      });
    }
    
    @Override
    public void onResult(final JSONObject result) {
      // stop to record
      audioRecordTask.stop();
      handler.post(new Runnable() {
        @Override
        public void run() {
          recognizerListener.onResult(result);
        }
      });
    }
    
    @Override
    public void onError(final ErrorCode errorCode) {
      // stop to record
      audioRecordTask.stop();
      handler.post(new Runnable() {
        @Override
        public void run() {
          recognizerListener.onError(errorCode);
        }
      });
    }
  };
  
  protected abstract AbstractWebSocket getWebSocket(List<AudioData> audioDataList);
  
  // stop speech task
  public void stopSpeech() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    
    if (audioRecordTask != null) {
      audioRecordTask.stop();
    }
    
    if (abstractWebSocket != null) {
      abstractWebSocket.stop();
    }
    
    // sleep 200ms wait for resource release
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
    }
  }

  // cancel the recording task and web task
  public void cancelSpeech() {
    
    if (audioRecordTask != null) {
      audioRecordTask.stop();
    }

    if (abstractWebSocket != null) {
      abstractWebSocket.cancel();
    }

    // sleep 200ms wait for resource release
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
    }
    
    // when cancel the task, do the call back
    recognizerListener.onCancel();
  }
  
  /**
   * close the recognizer
   */
  public void close() {
    LogUtil.d(TAG, "close the recognizer");
  }
}
