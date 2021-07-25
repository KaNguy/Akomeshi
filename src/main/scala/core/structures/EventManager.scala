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

case class EventManager(/*eventData: Iterable[(Any, Any)]*/) {
  EventObjects.mapEmitter.on("WS_MESSAGE", (_, data) => {
    if (
      data("t") != null &&
      data("t").isInstanceOf[String] &&
      data("op") != null &&
      data("op") == Constants.GatewayOpcodes("DISPATCH")
    ) {
        EventObjects.hashMapEmitter.emit("D_MESSAGE", data.getOrElse("d", "d").asInstanceOf[util.HashMap[Any, Any]])
    }
  })
}
