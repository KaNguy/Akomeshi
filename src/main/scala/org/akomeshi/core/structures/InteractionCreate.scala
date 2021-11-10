package org.akomeshi.core.structures

/**
 * Created by KaNguy - 10/29/2021
 * File org.akomeshi.core/structures/InteractionCreate.scala
 */

// Akomeshi
import org.akomeshi.utility.{Utilities => Util}
import org.akomeshi.core.structures.Channel

// Java Utilities
import java.util

case class InteractionCreate(interaction: util.HashMap[String, Any] = new util.HashMap[String, Any]()) {
  type SAMap = util.HashMap[String, Any]
  private val data: SAMap = Util.toHashMap(interaction.get("data"))
  private val member: SAMap = Util.toHashMap(interaction.get("member"))

  def applicationID: String = interaction.get("application_id").toString
  def channelID: String = interaction.get("channel_id").toString
  def channel: Channel = Channel(channelID)
  def guildID: String = interaction.get("guild_id").toString
  def id: String = interaction.get("id").toString
  def token: String = interaction.get("token").toString
  def interactionType: Int = Util.strToInt(interaction.get("type"))
  def version: Int = Util.strToInt(c(interaction.get("version")))

  /**
   * Checks for null properties, returns -1 by default if it is null.
   * @param x Any parameter.
   * @return Parameter as a String or -1 (as a String).
   */
  private def c(x: Any): String = {
    if (x != null) x.toString
    else "-1"
  }
  // TODO: Handle data and member maps
}
