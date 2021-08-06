package org.akomeshi
package utility

/**
 * Created by KaNguy - 07/27/2021
 * File utility/Utilities.scala
 */

// Utilities
import java.util
import java.util.Date

// Collections
import scala.collection.mutable.ListBuffer

case object Utilities {
  /**
   * Converts unknown data that is presumed to be a string into a boolean
   * @param x Any, preferably a string-compatible parameter
   * @return Boolean
   */
  def strToBool(x: Any): Boolean = x.toString.toBoolean

  /**
   * Converts unknown data that is presumed to be a string into an integer
   * @param x Any, preferably a string-compatible parameter
   * @return Int
   */
  def strToInt(x: Any): Int = x.toString.toInt

  /**
   * Converts an object or weak reference HashMap directly into a HashMap
   * @param x Preferably the assumed HashMap
   * @return HashMap[Any, Any]
   */
  def toHashMap(x: Any): util.HashMap[Any, Any] = x.asInstanceOf[util.HashMap[Any, Any]]

  private val discordEpoch: Long = 1420070400000L

  def snowFlakeToDate(snowflake: Long): String = {
    if (!snowflake.isValidLong) return snowflake.toString
    if (snowflake < 4194304L) return snowflake.toString
    new Date((snowflake / 4194304L) + discordEpoch).toString
  }

  def getFlags(flags: Int): List[String] = {
    val userFlags: ListBuffer[String] = ListBuffer[String]()
    Constants.userFlags.foreach(i => {
      if ((flags & i._2) == i._2) userFlags += i._1
    })
    userFlags.toList
  }
}
