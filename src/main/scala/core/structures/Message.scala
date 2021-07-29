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
  def messageType: Int = Util.strToInt(message.get("type").toString)
  def nonce: Int = Util.strToInt(message.get("nonce").toString)
  def isTTS: Boolean = Util.strToBool(message.get("tts").toString)
}
