package org.akomeshi
package core.managers

/**
 * Created by KaNguy - 07/29/2021
 * File core/structures/TokenManager.scala
 */

// Utilities
import java.util

case class TokenManager(token: String) {
  val storage: util.HashMap[String, String] = new util.HashMap[String, String]()
  def push(key: String, token: String = this.token): Unit = this.storage.put(key, token)
}

