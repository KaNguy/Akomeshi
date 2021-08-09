package org.akomeshi
package core.managers

/**
 * Created by KaNguy - 07/31/2021
 * File core/managers/Cache.scala
 */

// Utilities
import java.util

/**
 * Cache object that holds the HashMap caches for the client.
 * It is advised to not push to the HashMap variables in this object from the client since that may cause unforeseen consequences.
 */
object Cache {
  val readyCache: util.HashMap[Any, Any] = new util.HashMap[Any, Any]()

  /**
   * Indirectly pushes to a HashMap
   * @param hashMap HashMap value or variable
   * @param key Presumed key
   * @param value Presumed value
   */
  def push(hashMap: util.HashMap[Any, Any], key: String, value: util.HashMap[Any, Any]): Unit = hashMap.put(key, value)
}
