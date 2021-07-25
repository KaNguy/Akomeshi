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

case class Message(message: Map[Any, Any] = Map.empty[Any, Any]) {
  def content: String = {
    message("content").toString
  }

  EventObjects.mapEmitter.on("WS_MESSAGE", (_, data) => {
    if (data.getOrElse("t", "t") != null && data.getOrElse("t", "t").toString.equals("MESSAGE_CREATE")) {
      EventObjects.hashMapEmitter.emit("D_MESSAGE", data.getOrElse("d", "d").asInstanceOf[util.HashMap[Any, Any]])
    }
  })
}
