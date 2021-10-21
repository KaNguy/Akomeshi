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
  def createSlashCommand(name: String, description: String = String.valueOf(Character.MIN_VALUE), commandType: CommandType = CHAT_INPUT): Commands = {
    new Commands(name, description, commandType)
  }

  /**
   * Commands class of the SlashCommandBuilder
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object]]
   * @param name Name of the command.
   * @param description Description of the command.
   * @param commandType Type of command.
   * @param command The command itself, this field should not be modified since its keys and values may be overwritten.
   */
  class Commands(var name: String, description: String = String.valueOf(Character.MIN_VALUE), commandType: CommandType = CHAT_INPUT, private val command: util.HashMap[String, Any] = new util.HashMap()) {
    private var options: Array[util.HashMap[String, Any]] = Array.empty[util.HashMap[String, Any]]

    /**
     * Adds a basic label to an application command being built.
     * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object]]
     * @param name Name of the command.
     * @param description Description of the command.
     * @param commandType Type of command.
     * @return Command.
     */
    def label(name: String = this.name, description: String = this.description, commandType: CommandType = this.commandType): Commands = {
      command.put("name", name)
      command.put("type", commandType)
      command.put("description", description)
      Commands.this
    }

    /**
     * Adds other optional fields, can be called if needed (adds ALL optionals).
     * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-structure]]
     * @param guildID Guild ID if the command is not global.
     * @param defaultPermissions Default permissions, the API sets it to true if it isn't used.
     * @param strict If strict is true, other boolean parameters will be added to the command, otherwise it is ignored unless an individual parameter is true.
     * @return Command.
     */
    def addOptionals(guildID: String = String.valueOf(Character.MIN_VALUE), defaultPermissions: Boolean = false, strict: Boolean = false): Commands = {
      if (!"".equals(guildID)) command.put("guild_id", guildID)
      if (strict && !defaultPermissions)
        command.put("default_permissions", defaultPermissions)
      else command.put("default_permissions", defaultPermissions)
      Commands.this
    }

    /**
     * Adds an option to the an options Array in the class and will be used if it is not empty to build the command.
     * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-structure]]
     * @param name Name of the option.
     * @param description Description of the option.
     * @param commandType Type of (application) command.
     * @param required Optional parameter to assert whether the option is optional or required.
     * @param choices Optional parameter for adding choices.
     * @param channelTypes Array or Iterable List of channel types that the option is restricted to.
     * @param strict If true, it will add all optional parameters to the command option.
     * @return Commands.
     */
    def addOption(name: String,
                  description: String,
                  commandType: CommandType,
                  required: Boolean = false,
                  choices: Array[util.HashMap[String, Any]] = Array.empty[util.HashMap[String, Any]],
                  channelTypes: Iterable[Int] = Iterable.empty[Int],
                  strict: Boolean = false): Commands = {
      val option: util.HashMap[String, Any] = new util.HashMap[String, Any]()
      option.put("name", name)
      option.put("description", description)
      option.put("type", commandType)

      if (strict && !required)
        option.put("required", required)
      else if (required) option.put("required", required)

      if (choices.nonEmpty) option.put("choices", choices)
      if (channelTypes.nonEmpty) option.put("channel_types", channelTypes)
      this.options = this.options :+ option
      Commands.this
    }

    /**
     * Creates a choice for an option as a HashMap
     * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-choice-structure]]
     * @param name Name of the choice.
     * @param value Value of the choice.
     * @tparam T String, Integer, or Double.
     * @return HashMap of the choice.
     */
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

    /**
     * Assorts the choices into an Array.
     * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-structure]]
     * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-choice-structure]]
     * @param choices HashMap of choices.
     * @return Array of choices.
     */
    def buildChoices(choices: util.HashMap[String, Any]*): Array[util.HashMap[String, Any]] = {
      var optionChoices: Array[util.HashMap[String, Any]] = Array.empty[util.HashMap[String, Any]]
      for ((c, i) <- choices.zipWithIndex)
        if (i < 25)
          optionChoices = optionChoices :+ c
      optionChoices
    }

    /**
     * Finalizes an application command.
     * @return Command HashMap.
     */
    def build: util.HashMap[String, Any] = {
      if (!command.containsKey("options")) command.put("options", this.options)
      command
    }

    /**
     * Gets the built command or the command regardless if it was built or not.
     * @return Command HashMap.
     */
    def getCommand: util.HashMap[String, Any] = command
  }

  class CommandPermissions(id: String, permissionType: Int, permission: Boolean) {

  }
}