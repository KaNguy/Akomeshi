package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/22/2021
 * File org.akomeshi.core/structures/Message.scala
 */

// Akomeshi
import org.akomeshi.utility.{Utilities => Util}

// Utility
import java.util

case class Message(message: util.HashMap[Any, Any] = new util.HashMap[Any, Any]()) {
  def content: String = message.get("content").toString
  def mentionsEveryone: Boolean = Util.strToBool(message.get("mention_everyone"))
  def pinned: Boolean = Util.strToBool(message.get("pinned"))
  def flags: Int = Util.strToInt(message.get("flags"))
  def messageType: Int = Util.strToInt(message.get("type"))
  def nonce: Int = Util.strToInt(message.get("nonce"))
  def isTTS: Boolean = Util.strToBool(message.get("tts"))
  def referencedMessage: Int = Util.strToInt(message.get("referenced_message"))
  def id: Int = Util.strToInt(message.get("id"))
  def timestamp: String = message.get("timestamp").toString
  def channel: Channel = Channel(message.get("channel_id").toString)
}
