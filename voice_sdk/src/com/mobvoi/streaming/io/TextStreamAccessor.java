//Copyright 2012 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.streaming.io;

import java.io.Closeable;

/**
 * Text Stream accessor, use to define the charset, the default charset is UTF-8
 *
 * @author Jimmy Chen, <ggm1980303@live.cn>
 */
abstract class TextStreamAccessor implements Closeable {
  public final static String DEFAULT_CHARSET_NAME = "UTF-8";
  private String charsetName = null;

  public String getCharsetName() {
    return charsetName;
  }

  public void setCharsetName(String charsetName) {
    this.charsetName = charsetName;
  }

  public abstract void close();
}
