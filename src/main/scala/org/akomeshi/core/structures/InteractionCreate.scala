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
  private val memberInteractionData: SAMap = Util.toHashMap(interaction.get("member"))

  def applicationID: String = interaction.get("application_id").toString
  def channelID: String = interaction.get("channel_id").toString
  def channel: Channel = Channel(channelID)
  def guildID: String = interaction.get("guild_id").toString
  def id: String = interaction.get("id").toString
  def token: String = interaction.get("token").toString
  def interactionType: Int = Util.strToInt(interaction.get("type"))
  def version: Int = Util.strToInt(c(interaction.get("version")))
  def member: Member = Member(memberInteractionData)

  /**
   * Checks for null properties, returns -1 by default if it is null.
   * @param x Any parameter.
   * @return Parameter as a String or -1 (as a String).
   */
  private def c(x: Any): String = {
    if (x != null) x.toString
    else "-1"
  }

  case class Member(m: SAMap = new SAMap) {
    case class InteractionUser(u: SAMap = new SAMap) {
      def id: String = u.get("id").toString
      def username: String = u.get("username").toString
      def avatar: String = u.get("avatar").toString
      def discriminator: String = u.get("discriminator").toString
      def userFlags: List[String] = Util.getUserFlags(u.get("public_flags").toString.toInt)
    }
    def user: InteractionUser = InteractionUser(Util.toHashMap(m.get("user")))
    def roles: util.ArrayList[String] = m.get("roles").asInstanceOf[util.ArrayList[String]]
    def premiumSince: String = c(m.get("premium_since"))
    def permissions: List[String] = Util.getPermissionFlags(m.get("permissions").toString.toInt)
    def pending: Boolean = m.get("pending").toString.toBoolean
    def nick: String = c(m.get("nick"))
    def mute: Boolean = m.get("mute").toString.toBoolean
    def joinedAt: String = m.get("joined_at").toString
    def isPending: Boolean = m.get("is_pending").toString.toBoolean
    def deaf: Boolean = m.get("deaf").toString.toBoolean
  }
}
