package org.akomeshi
package event

/**
 * Created by KaNguy - 6/30/2021
 * File core/Events.scala
 */

object EventObjects {
  val dataEmitter: Emitter[String] = new Emitter[String]()
  val mapEmitter: Emitter[Map[Any, Any]] = new Emitter[Map[Any, Any]]()
}
