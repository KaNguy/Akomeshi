package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/22/2021
 * File core/structures/Message.scala
 */

// Akomeshi
import event.EventObjects

// Utility
import java.util

case class Message() {
  EventObjects.mapEmitter.on("WS_MESSAGE", (_, data) => {
    if (data.getOrElse("t", "t") != null && data.getOrElse("t", "t").toString.equals("MESSAGE_CREATE")) {
      EventObjects.hashMapEmitter.emit("MESSAGE", data.getOrElse("d", "d").asInstanceOf[util.HashMap[Any, Any]])
    }
  })
}
