package org.akomeshi
package json

/**
 * Created by KaNguy - 7/02/2021
 * File org.akomeshi.json/JSONString.scala
 */

// Collections
import scala.collection.mutable.ListBuffer

// Utilities
import java.util.{HashMap => JHashMap}

case object JSONString {
  /**
   * Serializes collections that have Object and primitive types into JSON data
   * @param collections Preferably map-based collections
   * @return Serialized JSON String
   */
  def encode(collections: Any): String = {
    val JSON = new ListBuffer[String]()
    collections match {
      case map: Map[_, _] =>
        for ((k, v) <- map) {
          val key = k.asInstanceOf[String].replaceAll("\"", "\\\\\"")
          v match {
            case map: Map[_, _] => JSON += s""""$key": ${encode(map)}""";
            case list: List[_] => JSON += s""""$key": ${encode(list)}""";
            case jHashMap: JHashMap[_, _] => JSON += s""""$key": ${encode(jHashMap)}""";
            case int: Int => JSON += s""""$key": $int""";
            case boolean: Boolean => JSON += s""""$key": $boolean""";
            case string: String => JSON += s""""$key": "${string.replaceAll("\"", "\\\\\"")}""""
            case _ => JSON += s"""$key: null""";
          }
        };

      case theList: List[_] =>
        val list = new ListBuffer[String]()
        for (listing <- theList) {
          listing match {
            case map: Map[_, _] => list += encode(map);
            case caseList: List[_] => list += encode(caseList);
            case int: Int => list += int.toString;
            case boolean: Boolean => list += boolean.toString;
            case string: String => list += s""""${string.replaceAll("\"", "\\\\\"")}"""";
            case _ => list += "null";
          }
        }

        return "[" + list.mkString(",") + "]";

      case jHashMap: JHashMap[_, _] =>
        jHashMap.forEach((key, value) => {
          value match {
            case map: Map[_, _] => JSON += s""""$key": ${encode(map)}""";
            case list: List[_] => JSON += s""""$key": ${encode(list)}""";
            case jHashMap: JHashMap[_, _] => JSON += s""""$key": ${encode(jHashMap)}""";
            case int: Int => JSON += s""""$key": $int""";
            case boolean: Boolean => JSON += s""""$key": $boolean""";
            case string: String => JSON += s""""$key": "${string.replaceAll("\"", "\\\\\"")}""""
            case _ => JSON += s"""$key: null""";
          }
        });

      case _ => ();
    }

    val JSONString: String = "{" + JSON.mkString(",") + "}"
    JSONString
  }
}
