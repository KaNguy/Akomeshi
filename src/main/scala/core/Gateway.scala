package org.akomeshi
package core

/**
 * Created by KaNguy - 06/28/2021
 * File core/Gateway.scala
 */

// Library
import json.{JSON, JSONString}
import utility.{Constants, Zlib}
import websocket.{AkoWebSocket, WebSocketListener}

import org.akomeshi.event.EventObjects

// NIO
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

// WebSocket
import java.net.http.WebSocket

// Utilities
import java.util.concurrent.{CompletionStage, ScheduledExecutorService, Executors}
import java.util

class Gateway {
  var connectionState: Int = 0

  val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
  var heartBeatInterval: Int = _

  val webSocketListener: WebSocketListener = new WebSocketListener {
    override def onBinary(webSocket: WebSocket, data: ByteBuffer, last: Boolean): CompletionStage[_] = {

      val dataArray = new Array[Byte](data.remaining())
      data.get(dataArray)

      val decompressedData = Zlib.decompress(dataArray)

      val decodedMessage: String = new String(decompressedData, StandardCharsets.UTF_8)

      var JSONData: Map[Any, Any] = Map.empty[Any, Any]
      try {
        JSONData = JSON.parseAsMap(decodedMessage)
      } catch {
        case _: Throwable => ()
      }

      if (JSONData.nonEmpty) EventObjects.mapEmitter.emit("WS_MESSAGE", JSONData)

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

      EventObjects.mapEmitter.emit("WS_MESSAGE", JSON.parseAsMap(stringBuilder.toString()))
      super.onText(webSocket, data, last)
    }

    override def onClose(webSocket: WebSocket, statusCode: Int, reason: String): CompletionStage[_] = {
      println("Connection closed: " + statusCode + ", " + reason)
      super.onClose(webSocket, statusCode, reason)
    }

    override def onError(webSocket: WebSocket, error: Throwable): Unit = {
      println("Error: " + error)
      super.onError(webSocket, error)
    }
  }

  val connection = new AkoWebSocket(Constants.gatewayURL, this.webSocketListener)
  EventObjects.mapEmitter.on("WS_MESSAGE", (channel, data) => {
    if (data.getOrElse("d", "d").asInstanceOf[util.HashMap[Any, Any]].containsKey("heartbeat_interval")) {
      heartBeatInterval = Integer.parseInt(data.getOrElse("d", "d").asInstanceOf[util.HashMap[Any, Any]].get("heartbeat_interval").toString)
      println(heartBeatInterval)
    }
    //scheduledExecutorService.scheduleAtFixedRate()
  })

}

object Gateway extends App {
  new Gateway()
}
