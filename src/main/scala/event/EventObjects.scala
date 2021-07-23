package org.akomeshi
package event

/**
 * Created by KaNguy - 6/30/2021
 * File core/Events.scala
 */

// Utilities
import java.util

object EventObjects {
  val dataEmitter: Emitter[String] = new Emitter[String]()
  val mapEmitter: Emitter[Map[Any, Any]] = new Emitter[Map[Any, Any]]()
  val hashMapEmitter: Emitter[util.HashMap[Any, Any]] = new Emitter[util.HashMap[Any, Any]]()
}
