package org.akomeshi
package utility

case object Constants {
  val packageName: String = "Akomeshi"
  val packageVersion: String = "0.0.1"

  private val APIVersion: Int = 9
  private val portNumber: Int = 443

  val gatewayURL: String = s"wss://gateway.discord.gg:${portNumber.toString}?v=${APIVersion.toString}&encoding=json"
  val apiURL: String = "https://discord.com/api"
  val cdnURL: String = "https://cdn.discordapp.com"
  val inviteURL: String = "https://discord.gg"
  val templateURL: String = "https://discord.new"

  val userAgent: String = s"DiscordBot (${packageName}, ${packageVersion})"
}
