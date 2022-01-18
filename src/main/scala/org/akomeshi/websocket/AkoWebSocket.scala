package org.akomeshi
package websocket

/**
 * Created by KiyonoKara - 6/28/2021
 * File org.akomeshi.utility.websocket/WebSocket.scala
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

  /**
   * General interaction with the WebSocket connection; experimental.
   * @param action Type of action (SEND, CLOSE, PING, PONG, BINARY).
   * @param data Data used for SEND and BINARY actions only.
   * @param message Message data used for PING and PONG actions only.
   * @param statusCode Status code used for CLOSE only.
   * @param reason Reason for closure, limited to CLOSE only.
   * @param last Determining whether the data is complete for SEND and BINARY actions only.
   * @param timeout Latch timeout, defaults to 1000 milliseconds.
   */
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

  /**
   * Sends data to the WebSocket connection as regular text
   * @param data CharSequence which can be a String.
   * @param last Determine whether the data is complete.
   * @param timeout Latch timeout.
   */
  def send(data: CharSequence, last: Boolean, timeout: Int = 1000): Unit = {
    try {
      this.webSocket.sendText(data, last)
      latch.await(timeout, TimeUnit.MILLISECONDS)
    } catch {
      case error: Throwable => error.printStackTrace()
    }
  }

  /**
   * Closes the WebSocket connection.
   * @param statusCode Status code for the closure.
   * @param reason Reason for the closure.
   * @param timeout Latch timeout.
   */
  def close(statusCode: Int = WebSocket.NORMAL_CLOSURE, reason: String, timeout: Int = 1000): Unit = {
    try {
      this.webSocket.sendClose(statusCode, reason)
      latch.await(timeout, TimeUnit.MILLISECONDS)
    } catch {
      case error: Throwable => error.printStackTrace()
    }
  }

  /**
   * Pings the WebSocket
   * @param message Message in the form of a ByteBuffer.
   * @param timeout Latch timeout
   * @return CompletableFuture that does not need to be used.
   */
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

  /**
   * Pongs the WebSocket
   * @param message Message in the form of a ByteBuffer.
   * @param timeout Latch timeout
   * @return CompletableFuture that does not need to be used.
   */
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

  /**
   * Sends binary / compressed data.
   * @param data Data in the form of a ByteBuffer.
   * @param last Determine whether the data is complete.
   * @param timeout Latch timeout.
   * @return CompletableFuture that does not need to be used.
   */
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

  /**
   * Checks if a provided URL has the WebSocket protocol.
   * @param url Provide an URL.
   * @return Boolean
   */
  private def hasWebSocketProtocol(url: String): Boolean = {
    val webSocketURL: String = url.toLowerCase.trim.replaceAll(" ", "")
    if (webSocketURL.substring(0, 2).equals("ws") || webSocketURL.substring(0, 3).equals("wss")) true
    else false
  }
}