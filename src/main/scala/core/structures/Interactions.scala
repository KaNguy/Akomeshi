package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 08/09/2021
 * File core/structures/Interactions.scala
 */

// Akomeshi
import core.api.request.RequestFrame
import utility.Constants
import utility.Utilities.toHashMap
import json.JSON
import core.managers.Cache

// Utilities
import java.util

case class Interactions() {
  val applicationID: String = toHashMap(toHashMap(Cache.readyCache.get(Constants.WebSocketEvents.map(_ => "READY").head)).get("application")).get("id").toString

  // TODO: This currently gets the application's commands and is raw but should do something different
  // TODO: Implement interactions and slash commands since message content will be a privileged intent
  def getCommands: util.HashMap[Any, Any] = {
    val request = RequestFrame.get(
      url = Constants.formatAPIURL + s"/applications/$applicationID/commands"
    )
    JSON.parseAsHashMap(s"{\"commands\":$request}")
  }
}
