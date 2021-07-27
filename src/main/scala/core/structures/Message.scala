package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/22/2021
 * File core/structures/Message.scala
 */

// Akomeshi
import event.EventObjects
import utility.{Utilities => Util}

// Utility
import java.util

case class Message(message: util.HashMap[Any, Any] = new util.HashMap[Any, Any]()) {
  def content: String = message.get("content").toString
  def mentionsEveryone: Boolean = Util.strToBool(message.get("mention_everyone").toString)
  def pinned: Boolean = Util.strToBool(message.get("pinned").toString)
  def flags: Int = Util.strToInt(message.get("flags").toString)

  EventObjects.hashMapEmitter.on("WS_MESSAGE", (_, data) => {
    if (data.get("t") != null && data.get("t").toString.equals("MESSAGE_CREATE")) {
      EventObjects.hashMapEmitter.emit("D_MESSAGE", data.get("d").asInstanceOf[util.HashMap[Any, Any]])
    }
  })
}
