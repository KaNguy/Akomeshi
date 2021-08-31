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

  def createSlashCommand(name: String, commandType: Int): Map[String, Any] = {
    Map("" -> "")
  }
}
