package org.akomeshi
package core.structures

import org.akomeshi.utility.Constants

/**
 * Created by KaNguy - 07/01/2021
 * File org.akomeshi.core/structures/PayloadModels.scala
 */

// Akomeshi

object PayloadModels {
  // Identification payload to the gateway.
  val identifyPayload: (String, Int) => Map[String, Any] = (token, intents) => Map("op" -> 2, "d" ->
    Map("token" -> s"Bot $token", "intents" -> intents, "properties" ->
      Map("$os" -> Constants.$os, "$browser" -> Constants.$browser, "$device" -> Constants.$device
      )
    )
  )

  // Heartbeat payload to keep the connection alive.
  val heartbeatPayload: Map[String, Any] = Map("op" -> 1, "d" -> "null")

  // Resume payload in case the connection needs to be resumed.
  val resumePayload: (String, String) => Map[String, Any] = (token, sessionID) => Map("op" -> 6, "d" ->
    Map("token" -> s"Bot $token", "session_id" -> sessionID, "seq" -> 7777)
  )
}
