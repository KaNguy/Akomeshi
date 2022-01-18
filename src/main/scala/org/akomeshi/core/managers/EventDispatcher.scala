package org.akomeshi
package core.managers

/**
 * Created by KiyonoKara - 07/28/2021
 * File org.akomeshi.core/managers/EventDispatcher.scala
 */

// Akomeshi
import core.structures.EventManager
import event.Emitter

// Utilities
import java.util

case class EventDispatcher(listener: Emitter[util.HashMap[String, Any]]) {
  // Passes events into the EventManager case class
  listener.on("WS_MESSAGE", (_, event) => {
    EventManager(event).apply()
  })
}
