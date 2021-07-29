package org.akomeshi
package utility

/**
 * Created by KaNguy - 07/27/2021
 * File utility/Utilities.scala
 */

case object Utilities {
  def strToBool(x: Any): Boolean = x.toString.toBoolean
  def strToInt(x: Any): Int = x.toString.toInt
}
