package org.akomeshi
package core.managers

/**
 * Created by KiyonoKara - 07/29/2021
 * File org.akomeshi.core/managers/TokenManager.scala
 */

// Utilities
import java.util

object TokenManager {
  // Storage for the token
  private val storage: util.HashMap[String, String] = new util.HashMap[String, String]()

  /**
   * Pushes a key and value to the storage
   * @param key Key
   * @param token Token or value
   */
  def push(key: String, token: String): Unit = {
    // Prevents anything but the token from being pushed
    if (!key.equals("token")) return
    this.storage.put(key, token)
  }

  /**
   * Gets the token since the storage is private
   * @return Token String
   */
  def getToken: String = {
    if (this.storage.get("token") != null) this.storage.get("token")
    else null
  }
}

