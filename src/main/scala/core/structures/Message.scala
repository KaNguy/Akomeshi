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

case class Message(message: util.HashMap[Any, Any] = new util.HashMap[Any, Any]()) {
  // TODO: Move this to a general utilities file
  private def cast[X, Y](x: Any): Y = x.asInstanceOf[X].asInstanceOf[Y]

  def content: String = message.get("content").toString
  def mentionsEveryone: Boolean = cast[String, Boolean](message.get("mention_everyone"))
  def pinned: Boolean = cast[String, Boolean](message.get("pinned"))
  def flags: Int = cast[String, Int](message.get("flags"))

  EventObjects.hashMapEmitter.on("WS_MESSAGE", (_, data) => {
    if (data.get("t") != null && data.get("t").toString.equals("MESSAGE_CREATE")) {
      EventObjects.hashMapEmitter.emit("D_MESSAGE", data.get("d").asInstanceOf[util.HashMap[Any, Any]])
    }
  })
}
