package org.akomeshi
package utility

/**
 * Created by KaNguy - 08/09/2021
 * File org.akomeshi.utility/SlashCommandBuilder.scala
 */

import java.util

object SlashCommandBuilder {
  def main(args: Array[String]): Unit = {
    val command = new Commands()
    println(command.buildChoices(
      command.createChoice("1", 1),
      command.createChoice("2", 2),
      command.createChoice("3", 3),
      command.createChoice("4", 4),
      command.createChoice("5", 5),
      command.createChoice("6", 6),
      command.createChoice("7", 7),
      command.createChoice("8", 8),
      command.createChoice("9", 9),
      command.createChoice("10", 10),
      command.createChoice("11", 11),
      command.createChoice("12", 12),
      command.createChoice("13", 13),
      command.createChoice("14", 14),
      command.createChoice("15", 15),
      command.createChoice("16", 16),
      command.createChoice("17", 17),
      command.createChoice("18", 18),
      command.createChoice("19", 19),
      command.createChoice("20", 20),
      command.createChoice("21", 21),
      command.createChoice("22", 22),
      command.createChoice("23", 23),
      command.createChoice("24", 24),
      command.createChoice("25", 25),
      command.createChoice("26", 26)
    ).mkString("(", ", ", ")"))
  }

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
    private val options: Array[util.HashMap[Any, Any]] = Array.empty[util.HashMap[Any, Any]]

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
      this.options :+ option
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