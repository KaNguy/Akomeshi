package org.akomeshi
package websocket

/**
 * Created by KaNguy - 6/28/2021
 * File websocket/WebSocket.scala
 */

// Networking & Web
import java.net.http.{HttpClient, HttpHeaders, HttpRequest, HttpResponse, WebSocket}
import java.net.http.WebSocket.{Builder, Listener}
import java.net.{ConnectException, URI}

// New I/O
import java.nio.ByteBuffer
import java.nio.CharBuffer

// Utilities
import java.util.concurrent.{CompletableFuture, CompletionStage, CountDownLatch, TimeUnit}

// Local
import org.akomeshi.websocket.WebSocketListener

class AkoWebSocket(var url: String = null, var listener: Listener = new WebSocketListener(), connectionTimeout: Int = 1000) {
  private val hasWSProtocol: Boolean = this.hasWebSocketProtocol(url)
  if (!hasWSProtocol) throw new Error("The URL does not have a WebSocket protocol")

  private val latch: CountDownLatch = new CountDownLatch(1)

  private val httpClient: HttpClient = HttpClient.newHttpClient()
  private var webSocket: WebSocket = httpClient.newWebSocketBuilder().buildAsync(URI.create(url), listener).join()

  val subprotocol: String = webSocket.getSubprotocol

  def interact(action: String = null, data: CharSequence = null, message: ByteBuffer = null, statusCode: Int = WebSocket.NORMAL_CLOSURE, reason: String = "", last: Boolean = false, timeout: Int = 1000): Unit = {
    action.toUpperCase match {
      case "SEND" => {
        this.webSocket.sendText(data, last)
      } case "CLOSE" => {
        this.webSocket.sendClose(statusCode, reason)
      } case "PING" => {
        this.webSocket.sendPing(message)
      } case "PONG" => {
        this.webSocket.sendPong(message)
      } case "BINARY" => {
        // Sends binaries, untested
        this.webSocket.sendBinary(ByteBuffer.allocate(CharBuffer.wrap(data).length() * Character.BYTES), last)
      }
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
