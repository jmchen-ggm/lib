//Copyright 2012 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Text stream writer, use to write the output stream.
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
public class TextStreamWriter extends TextStreamAccessor {
  private OutputStream stream = null;

  public TextStreamWriter(OutputStream stream, String charsetName) {
    setCharsetName(charsetName);
    this.stream = stream;
  }

  public TextStreamWriter(OutputStream stream) {
    this(stream, DEFAULT_CHARSET_NAME);
  }

  public TextStreamWriter(File file, String charsetName) throws FileNotFoundException {
    this(new FileOutputStream(file), charsetName);
  }

  public TextStreamWriter(File file) throws FileNotFoundException {
    this(new FileOutputStream(file), DEFAULT_CHARSET_NAME);
  }

  public TextStreamWriter(String filePath, String charsetName) throws FileNotFoundException {
    this(new File(filePath), charsetName);
  }

  public TextStreamWriter(String filePath) throws FileNotFoundException {
    this(new File(filePath), DEFAULT_CHARSET_NAME);
  }

  public void write(byte[] bytes) throws IOException {
    stream.write(bytes);
  }

  public void write(byte[] bytes, int offset, int length) throws IOException {
    stream.write(bytes, offset, length);
  }

  public void write(String text) throws IOException {
    byte[] bytes = text.getBytes(getCharsetName());
    write(bytes);
  }

  public void writeLine(String text) throws IOException {
    write(text + "\r\n");
  }

  public void flush() throws IOException {
    stream.flush();
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
}
