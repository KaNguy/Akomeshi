package org.akomeshi
package utility

/**
 * Created by KaNguy - 6/30/2021
 * File utility/Constants.scala
 */

case object Constants {
  val packageName: String = "Akomeshi"
  val packageVersion: String = "0.0.1"

  private val APIVersion: Int = 9
  private val portNumber: Int = 443

  val gatewayURL: String = s"wss://gateway.discord.gg:${portNumber.toString}?v=${APIVersion.toString}&encoding=json&compress=zlib-stream"
  val apiURL: String = "https://discord.com/api"
  val cdnURL: String = "https://cdn.discordapp.com"
  val inviteURL: String = "https://discord.gg"
  val templateURL: String = "https://discord.new"

  val userAgent: String = s"DiscordBot ($packageName, $packageVersion)"

  val $os: String = System.getProperty("os.name")
  val $browser, $device: String = packageName

  val zlibSuffix: Int = 0x0000FFFF

  /**
   * WebSocket events from the Discord Gateway
   *
   * Note: Get any event from the Seq
   * {{{
   *   WebSocketEvents.map(x => "READY").head
   * }}}
   * */
  val WebSocketEvents: Seq[String] = Seq(
    "READY",
    "RESUMED",
    "APPLICATION_COMMAND_CREATE",
    "APPLICATION_COMMAND_DELETE",
    "APPLICATION_COMMAND_UPDATE",
    "GUILD_CREATE",
    "GUILD_DELETE",
    "GUILD_UPDATE",
    "INVITE_CREATE",
    "INVITE_DELETE",
    "GUILD_MEMBER_ADD",
    "GUILD_MEMBER_REMOVE",
    "GUILD_MEMBER_UPDATE",
    "GUILD_MEMBERS_CHUNK",
    "GUILD_INTEGRATIONS_UPDATE",
    "GUILD_ROLE_CREATE",
    "GUILD_ROLE_DELETE",
    "GUILD_ROLE_UPDATE",
    "GUILD_BAN_ADD",
    "GUILD_BAN_REMOVE",
    "GUILD_EMOJIS_UPDATE",
    "CHANNEL_CREATE",
    "CHANNEL_DELETE",
    "CHANNEL_UPDATE",
    "CHANNEL_PINS_UPDATE",
    "MESSAGE_CREATE",
    "MESSAGE_DELETE",
    "MESSAGE_UPDATE",
    "MESSAGE_DELETE_BULK",
    "MESSAGE_REACTION_ADD",
    "MESSAGE_REACTION_REMOVE",
    "MESSAGE_REACTION_REMOVE_ALL",
    "MESSAGE_REACTION_REMOVE_EMOJI",
    "THREAD_CREATE",
    "THREAD_UPDATE",
    "THREAD_DELETE",
    "THREAD_LIST_SYNC",
    "THREAD_MEMBER_UPDATE",
    "THREAD_MEMBERS_UPDATE",
    "USER_UPDATE",
    "PRESENCE_UPDATE",
    "TYPING_START",
    "VOICE_STATE_UPDATE",
    "VOICE_SERVER_UPDATE",
    "WEBHOOKS_UPDATE",
    "INTERACTION_CREATE",
    "STAGE_INSTANCE_CREATE",
    "STAGE_INSTANCE_UPDATE",
    "STAGE_INSTANCE_DELETE",
    "GUILD_STICKERS_UPDATE",
  )
}
