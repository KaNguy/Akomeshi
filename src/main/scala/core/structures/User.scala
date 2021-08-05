package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/31/2021
 * File core/structures/User.scala
 */

// Akomeshi
import utility.{Utilities, Constants}
import core.api.request.RequestFrame
import json.JSON

// Utilities
import java.util

case class User() {

  val selfMap: util.HashMap[Any, Any] = JSON.parseAsHashMap(RequestFrame.get(s"${Constants.apiURL}/v${Constants.APIVersion}/users/@me"))
  val userMap: util.HashMap[Any, Any] = JSON.parseAsHashMap(RequestFrame.get(s"${Constants.apiURL}/v${Constants.APIVersion}/users/"))

  def self: Self = Self(selfMap)

  case class Self(user: util.HashMap[Any, Any]) {
    // TODO: Add public flags parsing
    def bot: Boolean = Utilities.strToBool(user.get("bot"))
    def verified: Boolean = Utilities.strToBool(user.get("verified"))
    def id: String = user.get("id").toString
    def avatarHash: String = user.get("avatar").toString
    def username: String = user.get("username").toString
    def discriminator: String = user.get("discriminator").toString
    def bannerHash: String = user.get("banner").toString
    def bannerColor: String = user.get("banner_color").toString
    def accentColor: String = user.get("accent_color").toString
    def locale: String = user.get("locale").toString
    def bio: String = user.get("bio").toString
  }

  case class GetUser(user: util.HashMap[Any, Any]) {
    // TODO: Add public flags parsing
    def id: String = user.get("id").toString
    def username: String = user.get("username").toString
    def discriminator: String = user.get("discriminator").toString
    def bannerColor: String = user.get("banner_color").toString
    def accentColor: String = user.get("accent_color").toString
    def bannerHash: String = user.get("banner").toString
    def avatarHash: String = user.get("avatar").toString
  }
}
