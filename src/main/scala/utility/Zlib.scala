package org.akomeshi
package utility

/**
 * Created by KaNguy - 07/06/2021
 * File utility/Zlib.scala
 */

object Zlib {
  def isFlushed(bytes: Array[Byte]): Boolean = {
    if (bytes.length < 4) return false
    val offset: Int = bytes.length - 4

    val zlibSuffix = bytes(offset + 3) & 0xFF |
      (bytes(offset + 2) & 0xFF) << 8 |
      (bytes(offset + 1) & 0xFF) << 16 |
      (bytes(offset) & 0xFF) << 24

    zlibSuffix == Constants.zlibSuffix
  }
}
