package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/31/2021
 * File core/structures/User.scala
 */

// Akomeshi
import core.managers.Cache
import utility.Utilities

// Utilities
import java.util

// TODO: Implement a user class for getting the client's own user info
case class User() {
  var emptyReadyCache: Boolean = true
  if (Cache.readyCache.get("READY") != null) {
    this.emptyReadyCache = false
  }

  def self: Self = Self(Utilities.toHashMap(Cache.readyCache.get("READY").asInstanceOf[util.HashMap[Any, Any]].get("user")))

  case class Self(user: util.HashMap[Any, Any]) {
    def bot: Boolean = Utilities.strToBool(user.get("bot"))
    def verified: Boolean = Utilities.strToBool(user.get("verified"))
    def id: String = user.get("id").toString
    def avatarHash: String = user.get("avatar").toString
    def username: String = user.get("username").toString
    def discriminator: String = user.get("discriminator").toString
  }
}
