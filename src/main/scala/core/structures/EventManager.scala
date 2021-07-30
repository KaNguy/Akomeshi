package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/24/2021
 * File core/structures/EventManager.scala
 */

// Akomeshi
import event.EventObjects
import utility.Constants

// Utilities
import java.util

case class EventManager(event: util.HashMap[Any, Any]) {
  /**
   * Dispatches events
   * @param event The event emitted from the WS_MESSAGE channel
   */
  def apply(event: util.HashMap[Any, Any] = this.event): Unit = {
    if (
      event.get("t") != null &&
        event.get("t").isInstanceOf[String] &&
        event.get("op") != null &&
        event.get("op") == Constants.GatewayOpcodes("DISPATCH")
    ) {
      if (event.get("t").equals(Constants.WebSocketEvents.map(_ => "MESSAGE_CREATE").head))
        EventObjects.messageEvent.emit("MESSAGE_CREATE", Message(event.get("d").asInstanceOf[util.HashMap[Any, Any]]))
    }
  }
}
