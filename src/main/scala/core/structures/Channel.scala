package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/29/2021
 * File core/structures/Channel.scala
 */

// Akomeshi
import core.api.request.RequestFrame
import utility.Constants

case class Channel(id: String) {
  // TODO: Requests from the request object have output, change the type of this method
  // TODO: Move this to a GuildChannel class instead
  def send(content: String, tts: Boolean = false): String = {
    RequestFrame.post(
      url = Constants.formatAPIURL + s"/channels/${this.id}/messages",
      data = Map("content" -> content, "tts" -> tts)
    )
  }

  def mention: String = s"<#${this.id}>"
}
