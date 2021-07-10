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
    // TODO: Test and polish this
    this.universalGatewayClass.connection.send(JSONString.encode(PayloadModels.identifyPayload(token)), last = true, timeout = 100)
  }
}
