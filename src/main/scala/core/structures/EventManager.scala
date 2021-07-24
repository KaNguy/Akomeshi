package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/24/2021
 * File core/structures/EventManager.scala
 */

// Akomeshi
import event.EventObjects

// Utilities
import java.util

case class EventManager(/*eventData: Iterable[(Any, Any)]*/) {
  EventObjects.mapEmitter.on("WS_MESSAGE", (_, data) => {
    if (data.getOrElse("t", "t") != null && data.getOrElse("t", "t").isInstanceOf[String]) {
      EventObjects.hashMapEmitter.emit("D_MESSAGE", data.getOrElse("d", "d").asInstanceOf[util.HashMap[Any, Any]])
    }
  })

  // TODO: Don't actually use this
  def dispatch(event: Iterable[(Any, Any)]): util.HashMap[Any, Any] = {
    if (event.toMap.getOrElse("t", "t") != null &&
      event.toMap.getOrElse("d", "d") != null &&
      event.toMap.getOrElse("op", "op") != null &&
      event.toMap.getOrElse("op", "op") == 0) event.toMap.getOrElse("d", "d").asInstanceOf[util.HashMap[Any, Any]]
    else {
      val eventMap = new util.HashMap[Any, Any]()
      eventMap.put("op", 0)
      eventMap.put("s", null)
      eventMap.put("t", null)
      eventMap.put("d", null)
      eventMap
    }
  }
}
