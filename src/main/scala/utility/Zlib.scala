package org.akomeshi
package utility

/**
 * Created by KaNguy - 07/06/2021
 * File utility/Zlib.scala
 */

// Input / Output & NIO
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

// ZIP
import java.util.zip.{Inflater, InflaterOutputStream, DataFormatException}

// Ref
import java.lang.ref.SoftReference

object Zlib {
  protected final val maxBufferSize: Int = 2048
  private final val inflater: Inflater = new Inflater()

  var bufferCache: ByteBuffer = _
  var decompressionBuffer: SoftReference[ByteArrayOutputStream] = _

  /**
   * Performs a check whether the data has been flushed
   * @param bytes Byte data
   * @return Boolean
   */
  def isFlushed(bytes: Array[Byte]): Boolean = {
    if (bytes.length < 4) return false
    val offset: Int = bytes.length - 4

    val zlibSuffix = bytes(offset + 3) & 0xFF |
      (bytes(offset + 2) & 0xFF) << 8 |
      (bytes(offset + 1) & 0xFF) << 16 |
      (bytes(offset) & 0xFF) << 24

    zlibSuffix == Constants.zlibSuffix
  }

  /**
   * Fills the buffer cache with the data
   * @param bytes Byte data
   */
  def bufferDelegation(bytes: Array[Byte]): Unit = {
    if (bufferCache == null) bufferCache = ByteBuffer.allocate(bytes.length * 2)

    if (bufferCache.capacity() < bytes.length + bufferCache.position()) {
      bufferCache.flip()
      bufferCache = this.reallocateBuffer(bufferCache, (bufferCache.capacity() + bytes.length) * 2)
    }

    bufferCache.put(bytes)
  }

  /**
   * Reallocates the byte buffer
   * @param data Byte data
   * @param length Byte data length, use the buffer's capacity added with the length, multiply it by all by two
   * @return ByteBuffer
   */
  def reallocateBuffer(data: ByteBuffer, length: Int): ByteBuffer = {
    val buffer: ByteBuffer = ByteBuffer.allocate(length)
    buffer.put(data)
    buffer
  }

  /**
   * Performs ZLIB decompression and decompresses the buffer data.
   * @param bytes Byte data
   * @return Byte array
   */
  def decompress(bytes: Array[Byte]): Array[Byte] = {
    if (!this.isFlushed(bytes)) return null

    val buffer: ByteArrayOutputStream = this.getDecompressionBuffer

    val decompressor: InflaterOutputStream = new InflaterOutputStream(buffer, inflater)
    try {
      decompressor.write(bytes)
      buffer.toByteArray
    } catch {
      case error: Throwable => throw new DataFormatException("Malformed data").initCause(error)
    } finally {
      if (buffer.size > maxBufferSize) {
        decompressionBuffer = newDecompressionBuffer
      } else {
        buffer.reset()
      }
    }
  }

  /**
   * Create a new decompression buffer
   * @return SoftReference[ByteArrayOutputStream]
   */
  private def newDecompressionBuffer: SoftReference[ByteArrayOutputStream] = {
    new SoftReference(new ByteArrayOutputStream(Math.min(1024, maxBufferSize)))
  }

  /**
   * Gets a decompression buffer and sets one up
   * @return A byte array output stream
   */
  private def getDecompressionBuffer: ByteArrayOutputStream = {
    if (decompressionBuffer == null) decompressionBuffer = this.newDecompressionBuffer

    var buffer: ByteArrayOutputStream = decompressionBuffer.get()
    if (buffer == null) {
      buffer = new ByteArrayOutputStream(Math.min(1024, maxBufferSize))
      decompressionBuffer = new SoftReference(new ByteArrayOutputStream(Math.min(1024, maxBufferSize)))
    }

    buffer
  }
}
