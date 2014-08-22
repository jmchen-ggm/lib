//Copyright 2012 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Text reader, use to read the text from a file or a input stream
 * 
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public class TextStreamReader extends TextStreamAccessor {
  // Default buffer size
  public final static int BUFFER_SIZE = 1024 * 50;
  private InputStream stream = null;
  private boolean isGzip;

  public TextStreamReader(InputStream stream, String charsetName) {
    setCharsetName(charsetName);
    this.stream = stream;
  }

  public TextStreamReader(InputStream stream) {
    this(stream, DEFAULT_CHARSET_NAME);
  }
  
  public TextStreamReader(InputStream stream, boolean isGzip) {
    this(stream, DEFAULT_CHARSET_NAME);
    this.isGzip = isGzip;
  }

  public TextStreamReader(File file, String charsetName) throws FileNotFoundException {
    this(new FileInputStream(file), charsetName);
  }

  public TextStreamReader(File file) throws FileNotFoundException {
    this(new FileInputStream(file), DEFAULT_CHARSET_NAME);
  }

  public TextStreamReader(String filePath, String charsetName) throws FileNotFoundException {
    this(new File(filePath), charsetName);
  }

  public TextStreamReader(String filePath) throws FileNotFoundException {
    this(new File(filePath), DEFAULT_CHARSET_NAME);
  }

  public byte[] readBytes(int offset) throws IOException {
    BufferedInputStream bis = new BufferedInputStream(stream);
    /* bis.mark(2);
    // 取前两个字节
    byte[] header = new byte[2];
    int result = bis.read(header);

    bis.reset();

    int headerData = getShort(header);

    // Gzip 流 的前两个字节是 0x1f8b
    if (result != -1 && headerData == 0x1f8b) {
      stream = new GZIPInputStream(bis);
    } else {
      stream = bis;
    }*/
    
    if (isGzip) {
      stream = new GZIPInputStream(bis);
    } else {
      stream = bis;
    }
    
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    byte[] bytes = new byte[BUFFER_SIZE];
    int length = 0;
    int tempOffset = offset;
    while ((length = stream.read(bytes, tempOffset, bytes.length)) > 0) {
      tempOffset = 0;
      buffer.write(bytes, 0, length);
    }
    return buffer.toByteArray();
  }

  public byte[] readBytes() throws IOException {
    return readBytes(0);
  }

  public String readText() throws IOException {
    byte[] bytes = readBytes();
    return new String(bytes, getCharsetName());
  }

  @Override
  public void close() {
    if (stream != null) {
      try {
        stream.close();
      } catch (IOException e) {

      }
      stream = null;
    }
  }

/*  private int getShort(byte[] data) {
    return (int) ((data[0] << 8) | data[1] & 0xFF);
  }*/
}
