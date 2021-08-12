package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/01/2021
 * File core/structures/PayloadModels.scala
 */

// Akomeshi
import utility.Constants

object PayloadModels {
  // Identification payload to the gateway.
  val identifyPayload: (String, Int) => Map[Any, Any] = (token, intents) => Map("op" -> 2, "d" ->
    Map("token" -> s"Bot $token", "intents" -> intents, "properties" ->
      Map("$os" -> Constants.$os, "$browser" -> Constants.$browser, "$device" -> Constants.$device
      )
    )
  )

  // Heartbeat payload to keep the connection alive.
  val heartbeatPayload: Map[String, Any] = Map("op" -> 1, "d" -> "null")

  // Resume payload in case the connection needs to be resumed.
  val resumePayload: (String, String) => Map[Any, Any] = (token, sessionID) => Map("op" -> 6, "d" ->
    Map("token" -> s"Bot $token", "session_id" -> sessionID, "seq" -> 7777)
  )
}
