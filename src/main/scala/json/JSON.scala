package org.akomeshi
package json

/**
 * Created by KaNguy - 07/04/2021
 * File json/JSON.scala
 */

// Utility
import java.util

// Convertors
import scala.jdk.CollectionConverters._

object JSON {
  def parse(json: String): Object = JSONParser.parse(json)
  def parseAsMap(json: String): Map[Any, Any] = JSONParser.parseAsMap(json).asScala.toMap
  def parseAsHashMap(json: String): util.HashMap[Any, Any] = JSONParser.parseAsHashMap(json).asInstanceOf[util.HashMap[Any, Any]]
}
