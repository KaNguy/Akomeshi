package org.akomeshi
package core

/**
 * Created by KaNguy - 07/09/2021
 * File org.akomeshi.core/Client.scala
 */

// Akomeshi
import core.managers.{EventDispatcher, Heartbeat, TokenManager}
import core.structures.{Interactions, PayloadModels, User}
import event.EventObjects
import json.JSONString
import org.akomeshi.utility.Utilities

// WebSocket
import java.net.http.WebSocket

class Client(val token: String, val intents: Iterable[String] = Array("GUILDS", "GUILD_MESSAGES")) {
  // Starts dispatching events from the HashMap emitter
  EventDispatcher(EventObjects.hashMapEmitter)

  // Pushes the token to the library's token storage in order to successfully authorize other actions the client wants to complete
  TokenManager.push("token", this.token)

  val universalGatewayClass: Gateway = new Gateway()

  /**
   * Opens the WebSocket connection, sends the identify payload, and starts sending heartbeats.
   * @see [[https://discord.com/developers/docs/topics/gateway#connecting-to-the-gateway]]
   * @param token Bot token, if not provided, the token parameter of the class will be used.
   */
  def login(token: String = this.token, intents: Iterable[String] = this.intents): Unit = {
    val gatewayIntents: Int = Utilities.parseIntents(intents)
    this.universalGatewayClass.connection.send(JSONString.encode(PayloadModels.identifyPayload(token, gatewayIntents)), last = true)
    if (TokenManager.getToken == null) TokenManager.push("token", token)
    Heartbeat.sendHeartbeat(41250, this.universalGatewayClass.connection)
  }

  /**
   * Closes the WebSocket connection with a normal closure and shuts down other tasks.
   * @see [[https://discord.com/developers/docs/topics/gateway#disconnections]]
   * @param timeout Timeout, should at least be above 0 in some cases.
   */
  def logout(timeout: Int = 300): Unit = {
    this.universalGatewayClass.connection.close(WebSocket.NORMAL_CLOSURE, "Normal closure", timeout = timeout)
    if (!Heartbeat.executor.isTerminated) Heartbeat.executor.shutdown()
  }

  /**
   * User object of the Client class.
   * @return User object.
   */
  def user: User = User()

  /**
   * Interactions object of the Client class.
   * @return Interactions object.
   */
  def interactions: Interactions = Interactions()
}