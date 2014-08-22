// Copyright 2013 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming;

import java.util.List;

import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.mobvoi.streaming.speex.Speex;
import com.mobvoi.streaming.util.LogUtil;
import com.mobvoi.streaming.util.SpeechUtil;

/**
 * Audio record task, use to record the task
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
class AudioRecordTask implements Runnable {
  private final static String TAG = AudioRecordTask.class.getName();
  
  /**
   * Audio recorder
   */
  private AudioRecord audioRecord;
  
  /**
   * min record buffer size, use to read the data from audio record stream
   */
  private int recordBufSize = 0;
  
  /**
   * recording flag
   */
  private boolean isRecording = true;
  
  /**
   * audio record listener
   */
  private AudioRecordListener audioRecordListener;
  
  /**
   * Audio data list, use to send the data to server through websocket channel
   */
  private List<AudioData> audioDataList;
  
  /**
   * speex encoding
   */
  private Speex speex;

  AudioRecordTask(List<AudioData> audioDataList) {
    this.audioDataList = audioDataList;

    // init the record bug size
    recordBufSize = AudioRecord.getMinBufferSize(SpeechUtil.SAMPLE_RATE,
        SpeechUtil.CHANNEL, SpeechUtil.AUDIO_FORMAT);

    this.speex = new Speex();
  }

  void stop() {
    isRecording = false;
  }

  @Override
  public void run() {
    try {
      speex.init();
      
      LogUtil.i(TAG, "initialize the audio record");
      
      audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, 
          SpeechUtil.SAMPLE_RATE, SpeechUtil.CHANNEL,
          SpeechUtil.AUDIO_FORMAT, SpeechUtil.AUDIO_BUF_SIZE_IN_BYTES);
      
      // if audio record statue is not initialized
      if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
        audioRecordListener.OnError(ErrorCode.AUDIO_ERROR);
      } else {
        LogUtil.i(TAG, "start to record");
        audioRecord.startRecording();
        short data[] = new short[recordBufSize];
        int read = 0;
        // read the record data and write to the output stream

        while (isRecording) {
          read = audioRecord.read(data, 0, speex.getFrameSize());
          // use speex to encode the data
          byte[] speexData = new byte[read];
          int size = speex.encode(data, 0, speexData, speex.getFrameSize());
          // create the audio data
          if (size > 0) {
            AudioData audioData = new AudioData();
            audioData.size = size;
            System.arraycopy(speexData, 0, audioData.data, 0, audioData.size);
            audioDataList.add(audioData);
          } else {
            Thread.sleep(20);
          }
          // calculate the voice
          int v = 0;
          for (int i = 1; i < data.length; i += 2) {
            v += data[i] * data[i];
          }
          // callback the volumn
          updateVolumn((int) (Math.abs((int) (v / (float) read) / 100000) >> 1));
        }
      }
    } catch (Exception e) {
      LogUtil.d(TAG, e.getMessage(), e);
    } finally {
      if (audioRecord != null) {
        if (audioRecord.getState() == AudioRecord.RECORDSTATE_RECORDING) {
          // stop the audio record
          LogUtil.i(TAG, "stop record");
          audioRecord.stop();
        }
        try {
          // release audio record
          LogUtil.i(TAG, "release record");
          audioRecord.release();
        } catch (Exception e) {
          LogUtil.d(TAG, e.getMessage(), e);
        }
        audioRecord = null;
      }
      speex.close();
    }
  }
  
  void setAudioRecordListener(AudioRecordListener audioRecordListener) {
    this.audioRecordListener = audioRecordListener;
  }

  private void updateVolumn(double volumn) {
    // call back the volumn in main thread
    audioRecordListener.onVolumn(volumn);
  }

  /**
   * start the thread
   */
  void start() {
    Thread thread = new Thread(this);
    thread.start();
  }
}
