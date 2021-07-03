package org.akomeshi
package utility

/**
 * Created by KaNguy - 07/02/2021
 * File utility/JSONString.scala
 * Software reused from the HTTPS-Requests-Scala repository
 */

// Collections
import scala.collection.mutable.ListBuffer

case object JSONString {
  def CollectionsToJSON(collections: Any): String = {
    val JSON = new ListBuffer[String]()
    collections match {
      case map: Map[_, _] =>
        for ((k, v) <- map) {
          val key = k.asInstanceOf[String].replaceAll("\"" , "\\\\\"")
          v match {
            case map: Map[_, _] => JSON += s""""$key": ${CollectionsToJSON(map)}""";
            case list: List[_] => JSON += s""""$key": ${CollectionsToJSON(list)}""";
            case int: Int => JSON += s""""$key": $int""";
            case boolean: Boolean => JSON += s""""$key": $boolean""";
            case string: String => JSON += s""""$key": "${string.replaceAll("\"" , "\\\\\"")}""""
            case _ => ();
          }
        };

      case theList: List[_] =>
        val list = new ListBuffer[String]()
        for (listing <- theList) {
          listing match {
            case map: Map[_, _] => list += CollectionsToJSON(map);
            case caseList: List[_] => list += CollectionsToJSON(caseList);
            case int: Int => list += int.toString;
            case boolean: Boolean => list += boolean.toString;
            case string: String => list += s""""${string.replaceAll("\"" , "\\\\\"")}"""";
            case _ => ();
          }
        }

        return "[" + list.mkString(",") + "]"

      case _ => ();
    }

    val JSONString: String = "{" + JSON.mkString(",") + "}"
    JSONString
  }
}
