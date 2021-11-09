package org.akomeshi.core.structures

/**
 * Created by KaNguy - 10/29/2021
 * File org.akomeshi.core/structures/InteractionCreate.scala
 */

// Akomeshi
import org.akomeshi.utility.{Utilities => Util}

// Java Utilities
import java.util

case class InteractionCreate(interaction: util.HashMap[String, Any] = new util.HashMap[String, Any]()) {
  def applicationID: String = interaction.get("application_id").toString
}
