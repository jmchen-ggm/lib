//Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.mobvoi.streaming.io.TextStreamReader;
import com.mobvoi.streaming.location.Location;
import com.mobvoi.streaming.util.LogUtil;
import com.mobvoi.streaming.websocket.client.WebSocketClient;
import com.mobvoi.streaming.websocket.drafts.Draft_17;
import com.mobvoi.streaming.websocket.handshake.ServerHandshake;

/**
 * Abstract web socket
 * 
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public abstract class AbstractWebSocket extends WebSocketClient {
  private static final String GZIP = "gzip";
  private final static String TAG = AbstractWebSocket.class.getName();
  private final static int NORMAL_CODE = 1000;
  /**
   * Audio Data List
   */
  private List<AudioData> audioDataList;

  /**
   * is stop to read the data from audio list
   */
  private boolean isStopReadVoiceData;

  /**
   * is cancel the web task
   */
  private boolean isCancel;

  /**
   * Device id
   */
  private String deviceId;

  /**
   * Location info
   */
  private Location location;

  /**
   * Key to the server
   */
  private String key;
  
  /**
   * app version
   */
  private String version;
  
  /**
   * send data thread
   */
  private SendDataThread sendDataThread = new SendDataThread();
  
  /**
   * WebSocket listener
   */
  private WebSocketListener webSocketListener;
  
  /**
   * ByteArray output stream, use to buffer the result
   */
  private ByteArrayOutputStream byteArrayOutputStream;
  
  /**
   * When server silence, this will be true
   */
  private boolean isStopSendData;
  
  /**
   * is already throw exception
   */
  private boolean isException;
  
  /**
   * is gzip compression
   */
  private boolean isGzip;
  
  /**
   * is silence detect enable
   */
  private boolean isSilenceDetection;

  public boolean isCancel() {
    return isCancel;
  }

  public void cancel() {
    LogUtil.i(TAG, "cancel");
    isCancel = true;
  }

  public AbstractWebSocket(URI serverUri, List<AudioData> audioDataList, String key,
      String deviceId, Location location, WebSocketListener webSocketListener,
      String version, int connectTimeout, int readTimeout) {
    super(serverUri, new Draft_17(), connectTimeout, readTimeout);
    this.audioDataList = audioDataList;
    this.deviceId = deviceId;
    this.location = location;
    this.key = key;
    this.webSocketListener = webSocketListener;
    this.byteArrayOutputStream = new ByteArrayOutputStream();
    this.version = version;
    this.isSilenceDetection = true;
  }
  

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    LogUtil.d(TAG, "open socket");
    JSONObject headerObject = getHeaderJSONObject();
    // when open the socket, send the header info
    send(headerObject.toString());
  }

  @Override
  public void onMessage(String message) {
    try {
      JSONObject responseObject = new JSONObject(message);
      LogUtil.d(TAG, "receive msg: " + message);
      String type = responseObject.getString("type");
      if (type.equals("server_ready")) {
        // start to send the data
        sendDataThread.start();
      } else if (type.equals("speech_end")) {
        // stop the client speech
        String content = responseObject.getString("content").trim();
        if (content.length() == 0) {
          onError(ErrorCode.NO_SPEECH);
          // close the socket
          close();
        } else {
          onSpeechEnd(responseObject.getString("content"));
        }
      } else if (type.equals("server_error")) {
        // server error
        onError(ErrorCode.SYSTEM_ERROR);
        close();
      } else if (type.equals("result_end")) {
        // if the result is end, close the socket
        ByteArrayInputStream byteArrayInputStream = 
            new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        TextStreamReader textStreamReader = 
            new TextStreamReader(byteArrayInputStream, isGzip);
        String result = textStreamReader.readText();
        textStreamReader.close();
        JSONObject resultObject = new JSONObject(result);
        String status = resultObject.getString("status")
            .toLowerCase(Locale.getDefault());
        // check the status of content
        if(status.equals("success")) {
          onResult(resultObject);
        } else if(status.equals("invalid_input_format")) {
          onError(ErrorCode.LONG_SPEECH);
        } else {
          onError(ErrorCode.SYSTEM_ERROR);
        }
        close();
      } else if (type.equals("onebox_header")) {
          JSONObject contentObject = responseObject.getJSONObject("content");
          String contentEncoding = contentObject.getString("Content-Encoding");
          isGzip = (contentEncoding.equals(GZIP));
      }
    } catch (Exception e) {
      // if occur exception, system error
      LogUtil.e(TAG, e.getMessage(), e);
      onError(ErrorCode.SYSTEM_ERROR);
      close();
    }
  }

  @Override
  public void onMessage(ByteBuffer bytes) {
    try {
      byteArrayOutputStream.write(bytes.array());
    } catch (IOException e) {
      LogUtil.e(TAG, e.getMessage(), e);
    }
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    if (NORMAL_CODE == code) {
      LogUtil.d(TAG, "Code 1000 And Normal Close");
    } else {
      LogUtil.e(TAG, "close socket: [code: " + code + 
          "] [reason: " + reason + "] [remote: " + remote + "]");
      // if cancel the task, do not return
      onError(ErrorCode.NETWORK_ERROR);
    }
  }

  @Override
  public void onError(Exception ex) {
    LogUtil.e(TAG, ex.getMessage(), ex);
    if (ex instanceof SocketTimeoutException) {
      onError(ErrorCode.NETWORK_ERROR);
    } else {
      onError(ErrorCode.NETWORK_ERROR);
    }
    close();
  }

  class SendDataThread extends Thread {
    @Override
    public void run() {
      LogUtil.i(TAG, "Start to send data to server");
      do {
        if (audioDataList.size() > 0) {
          AudioData audioData = audioDataList.get(0);
          audioDataList.remove(0);
          LogUtil.d(TAG, "Send data to server:" + audioData.size);
          byte[] sendData = new byte[audioData.size];
          System.arraycopy(audioData.data, 0, sendData, 0, audioData.size);
          try {
            send(sendData);
          } catch (Exception e) {
          }
        } else {
          try {
            Thread.sleep(20);
          } catch (InterruptedException e) {
          }
        }
        if (isCancel() || isStopSendData()) {
          break;
        }
      } while (!isStopReadVoiceData || audioDataList.size() != 0);
      if (isCancel()) {
        LogUtil.i(TAG, "Cancel the task");
        close();
      } else {
        // send the end signal
        send(generateSignalEnd().toString());
        LogUtil.i(TAG, "Finish send data to server");
      }
    }
  }

  protected abstract JSONObject getHeaderJSONObject();
  
  protected String getKey() {
    return this.key;
  }
  
  protected String getDeviceId() {
    return this.deviceId;
  }
  
  protected String getVersion() {
    return this.version;
  }
  
  protected Location getLocation() {
    return this.location;
  }
  
  protected boolean getIsSilenceDetection() {
    return this.isSilenceDetection;
  }
  
  protected void setIsSilenceDectection(boolean isSilenceDetection) {
    this.isSilenceDetection = isSilenceDetection;
  }

  // generate the end signal json object
  private JSONObject generateSignalEnd() {
    try {
      return new JSONObject().put("signal", "end");
    } catch (JSONException e) {
      return new JSONObject();
    }
  }
  
  public void stop() {
    this.isStopReadVoiceData = true;
  }
  
  public boolean isStopSendData() {
    return isStopSendData;
  }
  
  public void stopSendData() {
    isStopSendData = true;
  }
  
  public void start() {
    this.connect();
  }
  
  private void onResult(JSONObject result) {
    if (isCancel()) {
      return;
    }
    webSocketListener.onResult(result);
  }
  
  private void onSpeechEnd(String content) {
    if (isCancel()) {
      return;
    }
    webSocketListener.onSpeechEnd(content);
  }
  
  private void onError(ErrorCode errorCode) {
    if (isCancel()) {
      return;
    }
    if (!isException) {
      webSocketListener.onError(errorCode);
      isException = true;
    }
  }
}
