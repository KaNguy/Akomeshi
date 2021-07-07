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
   def onOpen(webSocket: WebSocket): Unit
   def onText(webSocket: WebSocket, data: CharSequence, last: Boolean): CompletionStage[_]
   def onPing(webSocket: WebSocket, message: ByteBuffer): CompletionStage[_]
   def onPong(webSocket: WebSocket, message: ByteBuffer): CompletionStage[_]
   def onClose(webSocket: WebSocket, statusCode: Int, reason: String): CompletionStage[_]
   def onError(webSocket: WebSocket, error: Throwable): Unit
   def onBinary(webSocket: WebSocket, data: ByteBuffer, last: Boolean): CompletionStage[_]
}
