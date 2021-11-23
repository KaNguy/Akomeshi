package org.akomeshi.core.structures

/**
 * Created by KaNguy - 10/29/2021
 * File org.akomeshi.core/structures/InteractionCreate.scala
 */

// Akomeshi
import org.akomeshi.utility.{Utilities => Util}

// Java Utilities
import java.util

case class InteractionCreate(interaction: util.HashMap[String, Any] = new util.HashMap[String, Any]()) {
  type SAMap = util.HashMap[String, Any]
  private val data: util.HashMap[String, util.ArrayList[String]] =
    if (interaction.containsKey("data"))
      interaction.get("data").asInstanceOf[util.HashMap[String, util.ArrayList[String]]]
    else new util.HashMap[String, util.ArrayList[String]]()

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

  /**
   * Member class from an interaction.
   * @param m Map of a member.
   */
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

  /**
   * Holds properties for type 1 data (CHAT_INPUT data).
   * @param d Type 1 data (CHAT_INPUT data).
   */
  case class Type1Data(d: SAMap = new SAMap) {
    case class Type1DataOptions(o: util.HashMap[String, util.ArrayList[String]]) {
      def get: util.ArrayList[util.HashMap[String, Any]] = {
        o.get("options").asInstanceOf[util.ArrayList[util.HashMap[String, Any]]]
      }
    }

    def name: String = c(d.get("name"))
    def id: String = c(d.get("id"))

    def options: util.ArrayList[util.HashMap[String, Any]] = Type1Data().Type1DataOptions(data).get
  }

  /**
   * Holds properties for type 2 data (USER data).
   * @param d Type 2 data (USER data).
   */
  case class Type2Data(d: SAMap = new SAMap) {
    def id: String = c(d.get("id"))
    def name: String = c(d.get("name"))
    case class Resolved(r: SAMap = new SAMap) {
      def members: Unit = {

      }
    }
  }

  case class Type2DataMember(memberT2: SAMap) {
    private val memberInstance: Member = Member(memberT2)
    def avatar: String = c(memberT2.get("avatar"))
    def isPending: Boolean = memberInstance.isPending
    def joinedAt: String = memberInstance.joinedAt
    def nick: String = memberInstance.nick
    def pending: Boolean = memberInstance.pending
    def permissions: List[String] = memberInstance.permissions
    def premiumSince: String = memberInstance.premiumSince
    def roles: Array[String] = memberT2.get("roles").asInstanceOf[Array[String]]
  }
}
