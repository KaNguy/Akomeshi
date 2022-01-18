package org.akomeshi
package core

/**
 * Created by KiyonoKara - 06/28/2021
 * File org.akomeshi.core/Gateway.scala
 */

// Akomeshi
import json.JSON
import websocket.{AkoWebSocket, WebSocketListener}
import event.EventObjects
import miscellaneous.Trace
import org.akomeshi.utility.{Constants, Zlib}

// NIO
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

// WebSocket
import java.net.http.WebSocket

// Utilities
import java.util.concurrent.CompletionStage
import java.util

class Gateway {
  /**
   * General connection state
   * 0 = Not connected
   * 1 = Connected
   */
  var connectionState: Int = 0

  lazy val webSocketListener: WebSocketListener = new WebSocketListener {
    /**
     * Listener for binary data, for handling compressed data in this library
     * @see [[https://discord.com/developers/docs/topics/gateway#encoding-and-compression]]
     * @param webSocket The WebSocket object
     * @param data Byte data from the org.akomeshi.event
     * @param last Verifies if the WebSocket message has more content or not (or if it is a separate message)
     * @return CompletionStage[_]
     */
    override def onBinary(webSocket: WebSocket, data: ByteBuffer, last: Boolean): CompletionStage[_] = {

      val dataArray = new Array[Byte](data.remaining())
      data.get(dataArray)

      val decompressedData = Zlib.decompress(dataArray)

      if (decompressedData == null) super.onBinary(webSocket, data, last)

      val decodedMessage: String = new String(decompressedData, StandardCharsets.UTF_8)

      var JSONData: util.HashMap[String, Any] = new util.HashMap[String, Any]()
      try {
        JSONData = JSON.parseAsHashMap(decodedMessage)
      } catch {
        case _: Throwable => ()
      }

      if (!JSONData.isEmpty) EventObjects.hashMapEmitter.emit("WS_MESSAGE", JSONData)

      super.onBinary(webSocket, data, last)
    }

    /**
     * WebSocket open connection org.akomeshi.event
     * @param webSocket WebSocket object
     */
    override def onOpen(webSocket: WebSocket): Unit = {
      EventObjects.dataEmitter.emit("WS_OPEN", "1")
      connectionState = 1
      super.onOpen(webSocket)
    }

    /**
     * Listener for WebSocket data; regular text data
     * @param webSocket WebSocket object
     * @param data CharSequence data that can be cast into a String
     * @param last Verifies if the message is a separate message
     * @return CompletionStage[_]
     */
    override def onText(webSocket: WebSocket, data: CharSequence, last: Boolean): CompletionStage[_] = {
      val stringBuilder: StringBuilder = new StringBuilder()
      stringBuilder.append(data)

      EventObjects.hashMapEmitter.emit("WS_MESSAGE", JSON.parseAsHashMap(stringBuilder.toString()))
      super.onText(webSocket, data, last)
    }

    /**
     * Event when the WebSocket closes
     * @param webSocket WebSocket object
     * @param statusCode Status code, should generally result in 1000 which is a clean close
     * @param reason Reason for closure
     * @return CompletionStage[_]
     */
    override def onClose(webSocket: WebSocket, statusCode: Int, reason: String): CompletionStage[_] = {
      Trace.logger.debug("Connection closed: " + statusCode + (if (reason.nonEmpty) ", " else "") + reason)
      connectionState = 0
      super.onClose(webSocket, statusCode, reason)
    }

    /**
     * Event for errors
     * @param webSocket WebSocket object
     * @param error Error that was emitted
     */
    override def onError(webSocket: WebSocket, error: Throwable): Unit = {
      Trace.logger.debug("Error: " + error)
      super.onError(webSocket, error)
    }
  }
  val connection = new AkoWebSocket(Constants.gatewayURL, this.webSocketListener)
}
