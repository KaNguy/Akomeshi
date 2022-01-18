package org.akomeshi
package core.structures

/**
 * Created by KiyonoKara - 08/09/2021
 * File org.akomeshi.core/structures/Interactions.scala
 */

// Akomeshi
import core.api.request.RequestFrame
import json.JSON
import core.managers.Cache
import org.akomeshi.utility.Constants
import org.akomeshi.utility.Utilities.toHashMap

// Utilities
import java.util

// Converters
import scala.jdk.CollectionConverters

case class Interactions() {
  private val readyCacheContents = Cache.readyCache.get(Constants.WebSocketEvents.map(_ => "READY").head)
  val applicationID: String = if (readyCacheContents != null) {
    toHashMap(toHashMap(readyCacheContents).get("application")).get("id").toString
  } else JSON.parseAsHashMap(RequestFrame.get(s"${Constants.apiURL}/v${Constants.APIVersion}/users/@me")).get("id").toString

  def getGlobalCommands: util.HashMap[String, Any] = {
    val request = RequestFrame.get(
      url = Constants.formatAPIURL + s"/applications/$applicationID/commands"
    )
    JSON.parseAsHashMap(s"{\"commands\":$request}")
  }

  /**
   * Gets commands of a guild as an Array, stored in a key.
   * @see [[https://discord.com/developers/docs/interactions/application-commands#get-guild-application-commands]]
   * @param guildID Guild ID.
   * @return HashMap with the key, "commands," with the array of guild commands.
   */
  def getGuildCommands(guildID: String): util.HashMap[String, Any] = {
    val request = RequestFrame.get(
      url = Constants.formatAPIURL + s"/applications/$applicationID/guilds/$guildID/commands"
    )
    JSON.parseAsHashMap(s"{\"commands\":$request}")
  }

  /**
   * Deletes a global slash command.
   * @see [[https://discord.com/developers/docs/interactions/application-commands#delete-global-application-command]]
   * @param commandID Provide the command ID.
   * @return Returns 204, no content.
   */
  def deleteGlobalCommand(commandID: String): String = {
    RequestFrame.delete(s"${Constants.formatAPIURL}/applications/$applicationID/commands/$commandID")
  }

  /**
   * Deletes a guild slash command.
   * @see [[https://discord.com/developers/docs/interactions/application-commands#delete-guild-application-command]]
   * @param guildID Provide the guild ID.
   * @param commandID Provide the command ID.
   * @return Returns 204, no content.
   */
  def deleteGuildCommand(guildID: String, commandID: String): String = {
    RequestFrame.delete(s"${Constants.formatAPIURL}/applications/$applicationID/guilds/$guildID/commands/$commandID")
  }

  /**
   * @see [[https://canary.discord.com/developers/docs/interactions/slash-commands#registering-a-command]]
   * @see [[https://canary.discord.com/developers/docs/interactions/slash-commands#create-global-application-command]]
   * @return 204, no content.
   */
  def registerGlobalCommand(commandData: util.HashMap[String, Any]): String = {
    RequestFrame.post(s"${Constants.formatAPIURL}/applications/$applicationID/commands", commandData)
  }

  /**
   * Creates a guild command.
   * @see [[https://discord.com/developers/docs/interactions/application-commands#create-guild-application-command]]
   * @param guildID Guild ID.
   * @param commandData Command data for the interaction.
   * @return 201, created.
   */
  def registerGuildCommand(guildID: String, commandData: util.HashMap[String, Any]): String = {
    RequestFrame.post(
      url = Constants.formatAPIURL + s"/applications/$applicationID/guilds/$guildID/commands",
      data = commandData
    )
  }
}
