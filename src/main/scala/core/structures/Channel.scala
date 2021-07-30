package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/29/2021
 * File core/structures/Channel.scala
 */

// Akomeshi
import core.managers.TokenManager
import core.api.request.{Request, RequestConstants}
import utility.Constants
import json.JSONString

case class Channel(id: String) {
  // TODO: Requests from the request object have output, change the type of this method
  def send(content: String, tts: Boolean = false): Unit = {
    Request.request(
      Constants.formatAPIURL + s"/channels/${this.id}/messages",
      RequestConstants.POST,
      Map(
        "Authorization" -> s"Bot ${TokenManager.getToken}",
        "Content-Type" -> "application/json",
        "User-Agent" -> Constants.userAgent,
        "Accept" -> "*/*"
      ),
      JSONString.encode(
        Map(
          "content" -> content,
          "tts" -> tts
        )
      )
    )
  }
}
