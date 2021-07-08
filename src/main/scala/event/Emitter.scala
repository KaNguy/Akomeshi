package org.akomeshi
package event

/**
 * Created by KaNguy - 6/29/2021
 * File utility/Emitter.scala
 */

// Utilities

import java.util
import java.util.UUID

class Emitter[T] {

  private val callbacks: util.HashMap[String, util.LinkedList[Pair[String, Callback[T]]]] = new util.HashMap()

  def emit(channel: String, data: T): Unit = {
    if (channel.equals("*")) {
      callbacks.entrySet().forEach(entry => {
        entry.getValue.forEach(callbackPair => {
          callbackPair.getU.call(channel, data)
        })
      })
    } else {
      var callbackPairList = callbacks.get("*")
      if (callbackPairList != null) {
        callbackPairList.forEach(callbackPair =>
          callbackPair.getU.call(channel, data)
        )
      }

      callbackPairList = callbacks.get(channel)
      if (callbackPairList == null) return

      callbackPairList.forEach(callbackPair => {
          callbackPair.getU.call(channel, data)
        }
      )
    }
  }

  def on(channel: String, callback: Callback[T]): String = {
    val uuid = UUID.randomUUID.toString
    var callbackPairList = callbacks.get(channel)
    if (callbackPairList == null) {
      callbackPairList = new util.LinkedList()
      callbackPairList.add(new Pair(uuid, callback))
      callbacks.put(channel, callbackPairList)
    } else callbackPairList.add(new Pair(uuid, callback))

    uuid
  }

  def off(uuid: String): Unit = {
    callbacks.entrySet.forEach(entry => {
        entry.getValue.removeIf(pair => pair.getT.equals(uuid))
      }
    )
  }
}
