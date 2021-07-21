package org.akomeshi
package core

/**
 * Created by KaNguy - 07/09/2021
 * File core/Client.scala
 */

// Akomeshi
import json.JSONString

// WebSocket
import java.net.http.WebSocket

class Client {
  val universalGatewayClass: Gateway = new Gateway()

  def login(token: String): Unit = {
    // TODO: Work on this a little more
    this.universalGatewayClass.connection.send(JSONString.encode(PayloadModels.identifyPayload(token)), last = true)
    Heartbeat.sendHeartbeat(41250, this.universalGatewayClass.connection)
  }

  def logout(): Unit = {
    this.universalGatewayClass.connection.close(WebSocket.NORMAL_CLOSURE, "Normal closure", timeout = 0)
    if (!Heartbeat.executor.isTerminated) Heartbeat.executor.shutdown()
  }
}
