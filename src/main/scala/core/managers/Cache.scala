package org.akomeshi
package core.managers

/**
 * Created by KaNguy - 07/31/2021
 * File core/managers/Cache.scala
 */

// Akomeshi
import utility.Constants

// Utilities
import java.util

// TODO: Implement other caches but complete the ready cache
object Cache {
  val readyCache: util.HashMap[Any, Any] = new util.HashMap[Any, Any]()
  // TODO Document this
  def push(hashMap: util.HashMap[Any, Any], key: String, value: util.HashMap[Any, Any]): Unit = hashMap.put(key, value)
}
