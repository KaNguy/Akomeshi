package org.akomeshi
package core.managers

import java.util

case class TokenManager(token: String) {
  def push(token: String = this.token): Unit = TokenHolder.storage.put("token", token)
}

