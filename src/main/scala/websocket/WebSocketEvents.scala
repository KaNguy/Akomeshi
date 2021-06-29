package org.akomeshi
package websocket

/**
 * Created by KaNguy - 6/29/2021
 * File websocket/WebSocketEvents.scala
 */

// WebSocket
import java.net.http.WebSocket

// New I/O
import java.nio.ByteBuffer

// Utilities
import java.util.concurrent.CompletionStage

trait WebSocketEvents extends WebSocketListener {
  override def onOpen(webSocket: WebSocket): Unit
  override def onText(webSocket: WebSocket, data: CharSequence, last: Boolean): CompletionStage[_]
  override def onPing(webSocket: WebSocket, message: ByteBuffer): CompletionStage[_]
  override def onPong(webSocket: WebSocket, message: ByteBuffer): CompletionStage[_]
  override def onClose(webSocket: WebSocket, statusCode: Int, reason: String): CompletionStage[_]
  override def onError(webSocket: WebSocket, error: Throwable): Unit
  override def onBinary(webSocket: WebSocket, data: ByteBuffer, last: Boolean): CompletionStage[_]
}
