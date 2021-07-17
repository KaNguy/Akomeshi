package org.akomeshi
package core.api.request

/**
 * Created by KaNguy - 07/15/2021
 * File core/api/request/RequestOutput.scala
 */

// Input / Output
import java.io.{InputStream, InputStreamReader, Reader}

// Networking
import java.net.HttpURLConnection

// Compression / Decompression
import java.util.zip.{GZIPInputStream, DeflaterInputStream}

object RequestOutput {
  /** Reads output of a connection established via the HttpURLConnection class
   *
   * @param connection HttpURLConnection
   * @param inputStream InputStream
   * @return String of the output
   */
  def read(connection: HttpURLConnection, inputStream: InputStream = null): String = {
    var connectionInputStream: InputStream = null
    if (inputStream != null) connectionInputStream = inputStream else connectionInputStream = connection.getInputStream

    // Set the reader to a null value before reading the output
    var reader: Reader = null

    // Decompression
    if (connection.getContentEncoding != null) {
      connection.getContentEncoding match {
        case "gzip" => reader = new InputStreamReader(new GZIPInputStream(connectionInputStream))
        case "deflate" => reader = new InputStreamReader(new DeflaterInputStream(connectionInputStream))
        case _ => reader = new InputStreamReader(connection.getInputStream)
      }
    } else reader = new InputStreamReader(connection.getInputStream)

    var ch: Int = 0

    val stringBuilder: StringBuilder = new StringBuilder()

    // Appended the data to the String Builder
    while (ch != -1) {
      ch = reader.read()
      if (ch == -1) {
        return stringBuilder.toString()
      }
      stringBuilder.append(ch.asInstanceOf[Char]).toString
    }

    stringBuilder.toString
  }
}
