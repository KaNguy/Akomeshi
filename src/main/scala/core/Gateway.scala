package org.akomeshi
package core

/**
 * Created by KaNguy - 06/28/2021
 * File core/Gateway.scala
 */

// Library
import org.akomeshi.websocket.{AkoWebSocket, WebSocketListener, WebSocketEvents}
import org.akomeshi.utility.{Constants, JSON, JSONString}
import org.akomeshi.core.{Events, PayloadModels}

// WebSocket
import java.net.http.WebSocket

// Utilites
import java.util.concurrent.CompletionStage

class Gateway {
  var connectionState: Int = 0

  val webSocketListener: WebSocketListener = new WebSocketListener {
    override def onOpen(webSocket: WebSocket): Unit = {
      Events.dataEmitter.emit("WS_OPEN", "1")
      connectionState = 1
      super.onOpen(webSocket)
    }

    override def onText(webSocket: WebSocket, data: CharSequence, last: Boolean): CompletionStage[_] = {
      println(data)
      Events.mapEmitter.on("WS_MESSAGE", (channel, data) => {
        if (data.getOrElse("op", "op").equals("10")) connection.send(JSONString.encode(PayloadModels.identifyPayload("")), true)
      })

      Events.mapEmitter.emit("WS_MESSAGE", JSON.parse(data.toString, true))
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

  Events.mapEmitter.on("WS_MESSAGE", (channel, data) => {
    println(data)
    //if (data.getOrElse("op", "op").equals("10"))
  });
  val connection = new AkoWebSocket(Constants.gatewayURL, this.webSocketListener)
  connection.send(JSONString.encode(PayloadModels.identifyPayload("")), true)
}

object Gateway extends App {
  new Gateway()
}
