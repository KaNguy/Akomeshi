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

case class Message(message: util.HashMap[Any, Any] = util.HashMap[Any, Any]) {
  def content: String = message.get("content").toString

  EventObjects.hashMapEmitter.on("WS_MESSAGE", (_, data) => {
    if (data.get("t") != null && data.get("t").toString.equals("MESSAGE_CREATE")) {
      EventObjects.hashMapEmitter.emit("D_MESSAGE", data.get("d").asInstanceOf[util.HashMap[Any, Any]])
    }
  })
}
