package org.akomeshi
package json

/**
 * Created by KaNguy - 07/04/2021
 * File org.akomeshi.json/JSON.scala
 */

// Utility
import java.util

// Convertors
import scala.jdk.CollectionConverters._

object JSON {
  /**
   * Parses JSON as an Object.
   * @param json JSON String.
   * @return Object.
   */
  def parse(json: String): Object = JSONParser.parse(json)

  /**
   * Parses JSON as a Scala Map.
   * @param json JSON String.
   * @return Scala Map.
   */
  def parseAsMap(json: String): Map[String, Any] = JSONParser.parseAsMap(json).asScala.toMap

  /**
   * Parses JSON as a Java HashMap
   * @param json JSON String.
   * @return Java HashMap
   */
  def parseAsHashMap(json: String): util.HashMap[String, Any] = JSONParser.parseAsHashMap(json).asInstanceOf[util.HashMap[String, Any]]
}
