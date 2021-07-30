package org.akomeshi
package core.managers

/**
 * Created by KaNguy - 07/28/2021
 * File core/managers/EventDispatcher.scala
 */

// Akomeshi
import core.structures.EventManager
import event.Emitter

// Utilities
import java.util

case class EventDispatcher(listener: Emitter[util.HashMap[Any, Any]]) {
  /**
   * Passes events into the EventManager case class
   */
  listener.on("WS_MESSAGE", (_, event) => {
    EventManager(event).apply()
  })
}
