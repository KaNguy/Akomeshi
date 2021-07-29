package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/01/2021
 * File core/PayloadModels.scala
 */

// Akomeshi
import utility.Constants

object PayloadModels {
  val identifyPayload: String => Map[Any, Any] = token => Map("op" -> 2, "d" ->
    Map("token" -> s"Bot $token", "intents" -> 513 /* Default intents, will change this later */ , "properties" ->
      Map("$os" -> Constants.$os, "$browser" -> Constants.$browser, "$device" -> Constants.$device
      )
    )
  )

  val heartbeatPayload: Map[String, Any] = Map("op" -> 1, "d" -> "null")
}
