package org.akomeshi
package utility

/**
 * Created by KaNguy - 07/27/2021
 * File utility/Utilities.scala
 */

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
}
