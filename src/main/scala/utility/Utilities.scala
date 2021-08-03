package org.akomeshi
package utility

/**
 * Created by KaNguy - 07/27/2021
 * File utility/Utilities.scala
 */

// Utilities
import java.util

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
}
