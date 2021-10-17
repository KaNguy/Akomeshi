package org.akomeshi
package utility

/**
 * Created by KaNguy - 08/09/2021
 * File org.akomeshi.utility/SlashCommandBuilder.scala
 */

import java.util

object SlashCommandBuilder {
  /**
   * Application command types.
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-types]]
   */
  type CommandType = Int
  val CHAT_INPUT: CommandType = 1
  val USER_COMMAND: CommandType = 2
  val MESSAGE: CommandType = 3

  /**
   * Application option structure
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-type]]
   */
  type CommandOptionType = Int
  val SUB_COMMAND: CommandOptionType = 1
  val SUB_COMMAND_GROUP: CommandOptionType = 2
  val STRING: CommandOptionType = 3
  val INTEGER: CommandOptionType = 4
  val BOOLEAN: CommandOptionType = 5
  val USER_OPTION: CommandOptionType = 6
  val CHANNEL: CommandOptionType = 7
  val ROLE: CommandOptionType = 8
  val MENTIONABLE: CommandOptionType = 9
  val NUMBER: CommandOptionType = 10

  /**
   * Creates a slash command.
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object]]
   * @param name        Name of the slash command.
   * @param commandType Command type.
   * @return
   */
  def createSlashCommand(name: String, commandType: Int): Commands = new Commands()

  /**
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object]]
   */
  class Commands(private val command: util.HashMap[String, Any] = new util.HashMap()) {
    private var options: Array[util.HashMap[String, Any]] = Array.empty[util.HashMap[String, Any]]

    /**
     * Adds a basic label to an application command being built.
     * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object]]
     * @param name Name of the command.
     * @param description Description of the command.
     * @param commandType Type of command.
     * @return Command.
     */
    def label(name: String, description: String, commandType: CommandType): Commands = {
      command.put("name", name)
      command.put("type", commandType)
      command.put("description", description)
      Commands.this
    }

    def addOption(name: String, description: String, commandType: CommandType, required: Boolean, choices: Array[util.HashMap[String, String]] = Array.empty[util.HashMap[String, String]]): Commands = {
      val option: util.HashMap[String, Any] = new util.HashMap[String, Any]()
      option.put("name", name)
      option.put("description", description)
      option.put("type", commandType)
      option.put("required", required.toString)
      if (choices.nonEmpty) option.put("choices", choices)
      this.options = this.options :+ option
      Commands.this
    }

    def createChoice[T](name: String, value: T): util.HashMap[String, Any] = {
      val choice: util.HashMap[String, Any] = new util.HashMap[String, Any]()
      choice.put("name", name)
      choice.put("value",
        if (value.isInstanceOf[String]) {
          if (value.toString.length < 100)
            value
          else
            value.toString.substring(0, 100)
        } else {
          value
        }
      )
      choice
    }

    def buildChoices(choices: util.HashMap[String, Any]*): Array[util.HashMap[String, Any]] = {
      var optionChoices: Array[util.HashMap[String, Any]] = Array.empty[util.HashMap[String, Any]]
      if (choices.length > 25) {
        for ((c, i) <- choices.zipWithIndex)
          if (i < 25) optionChoices = optionChoices :+ c
      } else for (choice <- choices)
        optionChoices = optionChoices :+ choice
      optionChoices
    }

    def build: util.HashMap[String, Any] = {
      // TODO: Put command together with this method.
      new util.HashMap[String, Any]()
    }

    def getCommand: util.HashMap[String, Any] = command
  }
}