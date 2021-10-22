package org.akomeshi
package event

/**
 * Created by KaNguy - 6/30/2021
 * File org.akomeshi.core/Events.scala
 */

// Akomeshi
import core.structures.Message

// Utilities
import java.util

object EventObjects {
  val dataEmitter: Emitter[String] = new Emitter[String]()
  val mapEmitter: Emitter[Map[String, Any]] = new Emitter[Map[String, Any]]()
  val hashMapEmitter: Emitter[util.HashMap[String, Any]] = new Emitter[util.HashMap[String, Any]]()
  val messageEvent: Emitter[Message] = new Emitter[Message]()
}
