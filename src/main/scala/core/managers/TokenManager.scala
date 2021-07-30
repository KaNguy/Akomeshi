package org.akomeshi
package core.managers

/**
 * Created by KaNguy - 07/29/2021
 * File core/structures/TokenManager.scala
 */

// Utilities
import java.util

object TokenManager {
  // Storage for the token
  val storage: util.HashMap[String, String] = new util.HashMap[String, String]()

  /**
   * Pushes a key and value to the storage
   * @param key Key
   * @param token Token or value
   */
  def push(key: String, token: String): Unit = this.storage.put(key, token)
}

