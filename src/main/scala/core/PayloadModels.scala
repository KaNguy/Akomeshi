package org.akomeshi
package core

/**
 * Created by KaNguy - 07/01/2021
 * File core/PayloadModels.scala
 */

// Akomeshi
import org.akomeshi.utility.Constants

object PayloadModels {
  val identifyPayload: String => Map[Any, Any] = (token) =>  Map("op" -> 2, "d" ->
    Map("token" -> s"Bot ${token}", "intents" -> 513 /* Default intents, will change this later */, "properties" ->
      Map("$os" -> Constants.$os, "$browser" -> Constants.$browser, "$device" -> Constants.$device
      )
    )
  )
}
