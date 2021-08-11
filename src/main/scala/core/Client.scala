package org.akomeshi
package core

/**
 * Created by KaNguy - 07/09/2021
 * File core/Client.scala
 */

// Akomeshi
import core.managers.{EventDispatcher, Heartbeat, TokenManager}
import core.structures.{PayloadModels, User, Interactions}
import utility.Constants
import event.EventObjects
import json.JSONString

// WebSocket
import java.net.http.WebSocket

class Client(val token: String, val intents: Array[String] = Array("GUILDS", "GUILD_MESSAGES")) {
  // Starts dispatching events from the HashMap emitter
  EventDispatcher(EventObjects.hashMapEmitter)

  // Pushes the token to the library's token storage in order to successfully authorize other actions the client wants to complete
  TokenManager.push("token", this.token)

  val universalGatewayClass: Gateway = new Gateway()

  /**
   * Opens the WebSocket connection, sends the identify payload, and starts sending heartbeats
   * @param token Bot token, if not provided, the token parameter of the class will be used
   */
  def login(token: String = this.token, intents: Array[String] = this.intents): Unit = {
    val gatewayIntents: Int = this.parseIntents(intents)
    this.universalGatewayClass.connection.send(JSONString.encode(PayloadModels.identifyPayload(token, gatewayIntents)), last = true)
    if (TokenManager.getToken == null) TokenManager.push("token", token)
    Heartbeat.sendHeartbeat(41250, this.universalGatewayClass.connection)
  }

  /**
   * Closes the WebSocket connection with a normal closure and shuts down other tasks
   * @param timeout Timeout, should at least be above 0 in some cases
   */
  def logout(timeout: Int = 300): Unit = {
    this.universalGatewayClass.connection.close(WebSocket.NORMAL_CLOSURE, "Normal closure", timeout = timeout)
    if (!Heartbeat.executor.isTerminated) Heartbeat.executor.shutdown()
  }

  private def parseIntents(intents: Array[String]): Int = {
    var intentsInt: Int = 0
    for (i <- intents) {
      intentsInt = intentsInt | Constants.intents(i.toUpperCase)
    }
    intentsInt
  }

  def user: User = User()

  def interactions: Interactions = Interactions()
}