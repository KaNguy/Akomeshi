package org.akomeshi
package core

/**
 * Created by KaNguy - 07/09/2021
 * File core/Client.scala
 */

// Akomeshi
import json.JSONString

class Client {
  val universalGatewayClass: Gateway = new Gateway()

  def login(token: String): Unit = {
    // TODO: Work on this a little more
    this.universalGatewayClass.connection.send(JSONString.encode(PayloadModels.identifyPayload(token)), last = true)
    Heartbeat.sendHeartbeat(41250, this.universalGatewayClass.connection)
  }
}
