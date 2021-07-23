package org.akomeshi
package core.miscellaneous

/**
 * Created by KaNguy - 07/21/2021
 * File core/miscellaneous/Logger.scala
 */

case class Logger(log: Boolean = false) {
  private def check(callback: () => Unit): Unit = {
    if (this.log) callback()
  }

  def debug(x: String*): Unit = {
    this.check(() => println(x.mkString("", ", ", "")))
  }

  def show(x: Any): Unit = {
    this.check(() => println(x))
  }
}