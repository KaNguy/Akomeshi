package org.akomeshi
package core

/**
 * Created by KaNguy - 07/28/2021
 * File core/EventDispatcher.scala
 */

// Akomeshi
import event.Emitter
import structures.EventManager

// Utilities
import java.util

case class EventDispatcher(listener: Emitter[util.HashMap[Any, Any]]) {
  listener.on("WS_MESSAGE", (_, event) => {
    EventManager(event).apply()
  })
}
