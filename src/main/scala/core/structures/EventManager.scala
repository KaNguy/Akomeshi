package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/24/2021
 * File core/structures/EventManager.scala
 */

// Akomeshi
import event.EventObjects
import utility.Constants
import core.structures.Message

// Utilities
import java.util

case class EventManager(/*eventData: Iterable[(Any, Any)]*/) {
  EventObjects.hashMapEmitter.on("WS_MESSAGE", (_, data) => {
    if (
      data.get("t") != null &&
      data.get("t").isInstanceOf[String] &&
      data.get("op") != null &&
      data.get("op") == Constants.GatewayOpcodes("DISPATCH")
    ) {
      if (data.get("t").equals(Constants.WebSocketEvents.map(_ => "MESSAGE_CREATE").head))
        EventObjects.messageEvent.emit("MESSAGE", Message(data.get("d").asInstanceOf[util.HashMap[Any, Any]]))
    }
  })
}
