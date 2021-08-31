package org.akomeshi
package utility

/**
 * Created by KaNguy - 08/09/2021
 * File org.akomeshi.utility/SlashCommandBuilder.scala
 */

object SlashCommandBuilder {
  /**
   * Application command types.
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-types]]
   */
  type CommandType = Int
  val CHAT_INPUT: CommandType = 1
  val USER: CommandType = 1
  val MESSAGE: CommandType = 3

  /**
   * Creates a slash command.
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object]]
   * @param name Name of the slash command.
   * @param commandType Command type.
   * @return
   */
  def createSlashCommand(name: String, commandType: Int): Map[String, Any] = {
    if (name.isBlank) Map()
    if (commandType != CHAT_INPUT && commandType != USER && commandType != MESSAGE) Map()
    // TODO: Add command building, taking two parameters isn't working.
  }
}
