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

  def self: Self = Self(selfMap)

  case class Self(user: util.HashMap[Any, Any]) {
    def id: String = user.get("id").toString
    def username: String = user.get("username").toString
    def discriminator: String = user.get("discriminator").toString
    def bannerColor: String = user.get("banner_color").toString
    def accentColor: String = user.get("accent_color").toString
    def bannerHash: String = user.get("banner").toString
    def avatarHash: String = user.get("avatar").toString
    def userFlags: List[String] = Utilities.getUserFlags(user.get("public_flags").toString.toInt)
    def createdTimestamp: String = Utilities.snowFlakeToDate(this.id.toLong)
    def bot: Boolean = Utilities.strToBool(user.get("bot"))
    def verified: Boolean = Utilities.strToBool(user.get("verified"))
    def locale: String = user.get("locale").toString
    def bio: String = user.get("bio").toString
  }

  def get(id: String): UserProperties = {
    val userMap: util.HashMap[Any, Any] = JSON.parseAsHashMap(RequestFrame.get(s"${Constants.apiURL}/v${Constants.APIVersion}/users/$id"))
    UserProperties(userMap)
  }

  case class UserProperties(user: util.HashMap[Any, Any]) {
    def id: String = user.get("id").toString
    def username: String = user.get("username").toString
    def discriminator: String = user.get("discriminator").toString
    def bannerColor: String = user.get("banner_color").toString
    def accentColor: String = user.get("accent_color").toString
    def bannerHash: String = user.get("banner").toString
    def avatarHash: String = user.get("avatar").toString
    def userFlags: List[String] = Utilities.getUserFlags(user.get("public_flags").toString.toInt)
    def createdTimestamp: String = Utilities.snowFlakeToDate(this.id.toLong)
  }
}
