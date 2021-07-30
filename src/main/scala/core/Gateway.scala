package org.akomeshi
package core

/**
 * Created by KaNguy - 06/28/2021
 * File core/Gateway.scala
 */

// Akomeshi
import json.JSON
import utility.{Constants, Zlib}
import websocket.{AkoWebSocket, WebSocketListener}
import event.EventObjects
import miscellaneous.Trace

// NIO
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

// WebSocket
import java.net.http.WebSocket

// Utilities
import java.util.concurrent.CompletionStage
import java.util

class Gateway {
  var connectionState: Int = 0

  lazy val webSocketListener: WebSocketListener = new WebSocketListener {
    override def onBinary(webSocket: WebSocket, data: ByteBuffer, last: Boolean): CompletionStage[_] = {

      val dataArray = new Array[Byte](data.remaining())
      data.get(dataArray)

      val decompressedData = Zlib.decompress(dataArray)

      val decodedMessage: String = new String(decompressedData, StandardCharsets.UTF_8)

      var JSONData: util.HashMap[Any, Any] = new util.HashMap[Any, Any]()
      try {
        JSONData = JSON.parseAsHashMap(decodedMessage)
      } catch {
        case _: Throwable => ()
      }

      if (!JSONData.isEmpty) EventObjects.hashMapEmitter.emit("WS_MESSAGE", JSONData)

      super.onBinary(webSocket, data, last)
    }

    override def onOpen(webSocket: WebSocket): Unit = {
      EventObjects.dataEmitter.emit("WS_OPEN", "1")
      connectionState = 1
      super.onOpen(webSocket)
    }

    override def onText(webSocket: WebSocket, data: CharSequence, last: Boolean): CompletionStage[_] = {
      val stringBuilder: StringBuilder = new StringBuilder()
      stringBuilder.append(data)

      EventObjects.hashMapEmitter.emit("WS_MESSAGE", JSON.parseAsHashMap(stringBuilder.toString()))
      super.onText(webSocket, data, last)
    }

    override def onClose(webSocket: WebSocket, statusCode: Int, reason: String): CompletionStage[_] = {
      Trace.logger.debug("Connection closed: " + statusCode + (if (reason.nonEmpty) ", " else "") + reason)
      connectionState = 0
      super.onClose(webSocket, statusCode, reason)
    }

    override def onError(webSocket: WebSocket, error: Throwable): Unit = {
      Trace.logger.debug("Error: " + error)
      super.onError(webSocket, error)
    }
  }
  val connection = new AkoWebSocket(Constants.gatewayURL, this.webSocketListener)
}
