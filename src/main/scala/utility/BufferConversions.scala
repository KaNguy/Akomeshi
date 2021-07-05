package org.akomeshi
package utility

/**
 * Created by KaNguy - 07/05/2021
 * File utility/BufferConversions.scala
 */

import java.nio.ByteBuffer
import java.nio.charset.{Charset, StandardCharsets}

object BufferConversions {
  def stringToByteBuffer(data: String): ByteBuffer = ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8))

  def byteBufferToString(buffer: ByteBuffer, charset: Charset): String = {
    var bytes: Array[Byte] = null
    if (buffer.hasArray) bytes = buffer.array
    else {
      bytes = new Array[Byte](buffer.remaining)
      buffer.get(bytes)
    }

    new String(bytes, charset)
  }
}
