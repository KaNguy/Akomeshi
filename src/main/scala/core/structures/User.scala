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
    def avatarURL(size: Int = 1024): String = createAvatarURL(this.id, this.avatarHash, size)
  }

  def get(id: String): UserProperties = {
    val userMap: util.HashMap[Any, Any] = JSON.parseAsHashMap(RequestFrame.get(s"${Constants.apiURL}/v${Constants.APIVersion}/users/$id"))
    UserProperties(userMap)
  }

  /**
   * Creates an avatar URL from the avatar hash of a user.
   * @see [[https://discord.com/developers/docs/reference#image-formatting-cdn-endpoints]]
   * @param id User ID
   * @param avatarHash User's avatar hash
   * @param size Image size
   * @return String with the user's avatar.
   */
  private def createAvatarURL(id: String, avatarHash: String, size: Int = 1024): String = {
    s"${Constants.cdnURL}/avatars/$id/${if (avatarHash.substring(0, 2).equals("a_")) avatarHash + ".gif" else avatarHash + ".png"}${if (size == -1) "" else s"?size=$size"}"
  }

  /**
   * Properties for any user.
   * @param user User HashMap
   */
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
    def avatarURL(size: Int = 1024): String = createAvatarURL(this.id, this.avatarHash, size)
  }
}
