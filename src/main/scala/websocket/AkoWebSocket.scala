package org.akomeshi
package websocket

/**
 * Created by KaNguy - 6/28/2021
 * File websocket/WebSocket.scala
 */

// Networking & Web
import java.net.http.{HttpClient, WebSocket}
import java.net.http.WebSocket.Listener
import java.net.URI

// New I/O
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

// Utilities
import java.util.concurrent.{CompletableFuture, CountDownLatch, TimeUnit}

class AkoWebSocket(var url: String = null, var listener: Listener = WebSocketListener(), connectionTimeout: Int = 1000) {
  private val hasWSProtocol: Boolean = this.hasWebSocketProtocol(url)
  if (!hasWSProtocol) throw new Error("The URL does not have a WebSocket protocol")

  private val latch: CountDownLatch = new CountDownLatch(1)

  private val httpClient: HttpClient = HttpClient.newHttpClient()
  private val webSocket: WebSocket = httpClient.newWebSocketBuilder().buildAsync(URI.create(url), listener).join()

  latch.await(this.connectionTimeout / 10, TimeUnit.MILLISECONDS)

  val subprotocol: String = webSocket.getSubprotocol

  def interact(action: String = null, data: CharSequence = null, message: ByteBuffer = null, statusCode: Int = WebSocket.NORMAL_CLOSURE, reason: String = "", last: Boolean = false, timeout: Int = 1000): Unit = {
    action.toUpperCase match {
      case "SEND" => this.webSocket.sendText(data, last)
      case "CLOSE" => this.webSocket.sendClose(statusCode, reason)
      case "PING" => this.webSocket.sendPing(message)
      case "PONG" => this.webSocket.sendPong(message)
      case "BINARY" => this.webSocket.sendBinary(ByteBuffer.wrap(data.toString.getBytes(StandardCharsets.UTF_8)), last)
      case _ => ()
    }
    latch.await(timeout, TimeUnit.MILLISECONDS)
  }

  def send(data: CharSequence, last: Boolean, timeout: Int = 1000): Unit = {
    try {
      this.webSocket.sendText(data, last)
      latch.await(timeout, TimeUnit.MILLISECONDS)
    } catch {
      case error: Throwable => error.printStackTrace()
    }
  }

  def close(statusCode: Int = WebSocket.NORMAL_CLOSURE, reason: String, timeout: Int = 1000): Unit = {
    try {
      this.webSocket.sendClose(statusCode, reason)
      latch.await(timeout, TimeUnit.MILLISECONDS)
    } catch {
      case error: Throwable => error.printStackTrace()
    }
  }

  def ping(message: ByteBuffer, timeout: Int = 1000): CompletableFuture[AkoWebSocket] = {
    try {
      this.webSocket.sendPing(message)
      latch.await(timeout, TimeUnit.MILLISECONDS)
      new CompletableFuture[AkoWebSocket]()
    } catch {
      case error: Throwable => error.printStackTrace()
        new CompletableFuture[AkoWebSocket]()
    }
  }

  def pong(message: ByteBuffer, timeout: Int = 1000): CompletableFuture[AkoWebSocket] = {
    try {
      this.webSocket.sendPong(message)
      latch.await(timeout, TimeUnit.MILLISECONDS)
      new CompletableFuture[AkoWebSocket]()
    } catch {
      case error: Throwable => error.printStackTrace()
        new CompletableFuture[AkoWebSocket]()
    }
  }

  def sendBinary(data: ByteBuffer, last: Boolean, timeout: Int = 1000): CompletableFuture[AkoWebSocket] = {
    try {
      this.webSocket.sendBinary(data, last)
      latch.await(timeout, TimeUnit.MILLISECONDS)
      new CompletableFuture[AkoWebSocket]()
    } catch {
      case error: Throwable => error.printStackTrace()
        new CompletableFuture[AkoWebSocket]()
    }
  }

  private def hasWebSocketProtocol(url: String): Boolean = {
    val webSocketURL: String = url.toLowerCase.trim.replaceAll(" ", "")
    if (webSocketURL.substring(0, 2).equals("ws") || webSocketURL.substring(0, 3).equals("wss")) {
      true
    } else {
      false
    }
  }
}