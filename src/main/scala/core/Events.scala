package org.akomeshi
package core

/**
 * Created by KaNguy - 6/30/2021
 * File core/Events.scala
 */

// Event emission
import org.akomeshi.utility.Emitter

object Events {
  val dataEmitter: Emitter[String] = new Emitter[String]()
}
