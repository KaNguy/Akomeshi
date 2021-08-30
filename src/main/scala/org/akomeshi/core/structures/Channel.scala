package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 07/29/2021
 * File org.akomeshi.core/structures/Channel.scala
 */

// Akomeshi
import core.api.request.RequestFrame
import json.JSON
import org.akomeshi.utility.Constants

// Utilities
import java.util

case class Channel(id: String) {
  def send(content: String, tts: Boolean = false): util.HashMap[Any, Any] = {
    val request = RequestFrame.post(
      url = Constants.formatAPIURL + s"/channels/${this.id}/messages",
      data = Map("content" -> content, "tts" -> tts)
    )
    JSON.parseAsHashMap(request)
  }

  def mention: String = s"<#${this.id}>"
}
