package org.akomeshi
package event

/**
 * Created by KaNguy - 6/29/2021
 * File org.akomeshi.utility/Callback.scala
 */

trait Callback[T] {
  def call(channel: String, target: T)
}
