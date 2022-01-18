package org.akomeshi
package utility

/**
 * Created by KiyonoKara - 07/27/2021
 * File org.akomeshi.utility/Utilities.scala
 */

// Utilities
import java.util
import java.util.Date

// Collections
import scala.collection.mutable.ListBuffer

case object Utilities {
  /**
   * Converts unknown data that is presumed to be a string into a boolean.
   * @param x Any, preferably a string-compatible parameter.
   * @return Boolean.
   */
  def strToBool(x: Any): Boolean = x.toString.toBoolean

  /**
   * Converts unknown data that is presumed to be a string into an integer.
   * @param x Any, preferably a string-compatible parameter.
   * @return Int.
   */
  def strToInt(x: Any): Int = x.toString.toInt

  /**
   * Converts an object or weak reference HashMap directly into a HashMap.
   * @param x Preferably the assumed HashMap.
   * @return HashMap[String, Any].
   */
  def toHashMap(x: Any): util.HashMap[String, Any] = x.asInstanceOf[util.HashMap[String, Any]]

  // Discord Epoch
  private val discordEpoch: Long = 1420070400000L

  /**
   * Converts a Discord snowflake into a human-readable date
   * @see [[https://discord.com/developers/docs/reference#snowflakes]]
   * @param snowflake Long.
   * @return Date in the form of a String.
   */
  def snowFlakeToDate(snowflake: Long): String = {
    if (!snowflake.isValidLong) return snowflake.toString
    if (snowflake < 4194304L) return snowflake.toString
    new Date((snowflake / 4194304L) + discordEpoch).toString
  }

  /**
   * Returns a list of user flags from an int.
   * @see [[https://discord.com/developers/docs/resources/user#user-object-user-flags]]
   * @param flags Integer of the user flags.
   * @return A list of user flags.
   */
  def getUserFlags(flags: Int): List[String] = {
    val userFlags: ListBuffer[String] = ListBuffer[String]()
    Constants.userFlags.foreach(i => {
      if ((flags & i._2) == i._2) userFlags += i._1
    })
    userFlags.toList
  }

  /**
   * Parses intents from an iterable collection into an integer.
   * @see [[https://discord.com/developers/docs/topics/gateway#gateway-intents]]
   * @param intents List of intents in a single-value collection.
   * @return Intents integer.
   */
  def parseIntents(intents: Iterable[String]): Int = {
    var intentsInt: Int = 0
    for (i <- intents) intentsInt = intentsInt | Constants.intents(i.toUpperCase)
    intentsInt
  }

  /**
   * Returns a list of permission flags from an int.
   * @see [[https://discord.com/developers/docs/topics/permissions#permissions-bitwise-permission-flags ]]
   * @param permissions Integer of the permission flags.
   * @return A list of permission flags.
   */
  def getPermissionFlags(permissions: Int): List[String] = {
    var permissionsList: List[String] = List.empty[String]
    Constants.permissionFlags.foreach(i => {
      if ((permissions & i._2) == i._2) permissionsList = permissionsList :+ i._1
    })
    permissionsList
  }
}
