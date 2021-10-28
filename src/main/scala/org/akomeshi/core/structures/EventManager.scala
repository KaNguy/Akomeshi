package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/24/2021
 * File org.akomeshi.core/structures/EventManager.scala
 */

// Akomeshi
import event.EventObjects
import core.managers.Cache
import org.akomeshi.utility.Constants
import org.akomeshi.utility.Utilities.toHashMap

// Utilities
import java.util

case class EventManager(event: util.HashMap[String, Any]) {
  /**
   * Dispatches events
   * @param event The org.akomeshi.event emitted from the WS_MESSAGE channel
   */
  def apply(event: util.HashMap[String, Any] = this.event): Unit = {
    if (
      event.get("t") != null &&
      event.get("t").isInstanceOf[String] &&
      event.get("op") != null &&
      event.get("op") == Constants.GatewayOpcodes("DISPATCH") &&
      event.get("d") != null
    ) {
      if (event.get("t").equals(Constants.WebSocketEvents.map(_ => "READY").head)) {
        Cache.push(Cache.readyCache, "READY", toHashMap(event.get("d")))
      }

      if (event.get("t").equals(Constants.WebSocketEvents.map(_ => "MESSAGE_CREATE").head)) {
        EventObjects.messageEvent.emit("MESSAGE_CREATE", Message(event.get("d").asInstanceOf[util.HashMap[String, Any]]))
      }

      if (event.get("t").equals(Constants.WebSocketEvents.map(_ => "INTERACTION_CREATE").head)) {
        EventObjects.hashMapEmitter.emit("INTERACTION_CREATE", toHashMap(event.get("d")))
      }
    }
  }
}
