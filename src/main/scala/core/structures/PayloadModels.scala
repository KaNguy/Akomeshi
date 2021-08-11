package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/01/2021
 * File core/structures/PayloadModels.scala
 */

// Akomeshi
import utility.Constants

object PayloadModels {
  val identifyPayload: (String, Int) => Map[Any, Any] = (token, intents) => Map("op" -> 2, "d" ->
    Map("token" -> s"Bot $token", "intents" -> intents, "properties" ->
      Map("$os" -> Constants.$os, "$browser" -> Constants.$browser, "$device" -> Constants.$device
      )
    )
  )

  val heartbeatPayload: Map[String, Any] = Map("op" -> 1, "d" -> "null")
}
