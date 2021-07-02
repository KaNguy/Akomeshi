package org.akomeshi
package utility

// Utilities
import java.util
import java.util.Scanner

// Collections
import scala.jdk.CollectionConverters._

/**
 * Created by KaNguy - 07/01/2021
 * File utility/AkoJSON.scala
 * Alternate JSON parser
 */

object AkoJSON {
  def parse(json: String): Map[Any, Any] = {
    this.parseJSON(json.toString).asInstanceOf[java.util.HashMap[Any, Any]].asScala.toMap
  }
  
  private def parseJSON(data: String): Any = {
    val input = removeWhitespace(data)
    val i0 = input(0)
    if (i0 == '{') {
      var(result, index) = loadsColon(input.substring(1))
      index += 1
      if (index > data.length) {
        index = data.length
      }

      if (removeWhitespace(data.substring(index)).nonEmpty) {
        throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
      }

      return result
    } else if (i0 == '[') {
      var (result: Array[Object], index) = loadsList(input.substring(1))
      index += 1

      if (index > data.length) {
        index = data.length
      }
      if (removeWhitespace(data.substring(index)).nonEmpty) {
        throw new Exception("Parser Error: Extra data was found --> " + input.substring(index))
      }

      return result
    } else if (i0 == '"') {
      val endOfString = input.indexOf("\"", 1)
      return endOfString
    } else if (i0 == 'n') {
      if (input.substring(0, 4).equals("null")) {
        if (removeWhitespace(input.substring(4)).nonEmpty) {
          throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
        }
        return null
      }
    } else if (i0 == 't') {
      if (input.substring(0, 4).equals("true")) {
        if (removeWhitespace(input.substring(4)).nonEmpty) {
          throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
        }

        return true
      }
    } else if (i0 == 'f') {
      if (input.substring(0, 5).equals("false")) {
        if (removeWhitespace(input.substring(5)).nonEmpty) {
          throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
        }

        return false
      }
    } else if ("0123456789.-".contains(i0)) {
      val (x, y) = loadsNumber(input.substring(0))

      if (removeWhitespace(data.substring(y)).nonEmpty) {
        throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
      }

      return x
    }
    throw new Exception("JSON Parser Error: No JSON data was found")
  }

  private def loadsColon(input: String): (util.HashMap[String, Any], Integer) = {
    var index = 0
    val result = new util.HashMap[String, Any]

    var loop = true

    var v: Object = null

    while ((index < input.length) && loop) {
      if(input(index) == '}') {
        loop = false
      } else if (input(index) == '"') {
        val (k: String, i) = loadsString(input.substring(index + 1))
        index += (i + 1)

        while(
          (index < input.length) &&
          (
            (input(index) == ' ' ) ||
            (input(index) == '\t') ||
            (input(index) == '\n') ||
            (input(index) == '\r')
          )
        ) {
          index += 1
        }

        if (input(index) != ':') {
          throw new Exception("JSON Parser Error: Could not find the colon --> " + input.substring(index))
        }

        index += 1

        while(
          (index < input.length) &&
          (
            (input(index) == ' ' ) ||
            (input(index) == '\t') ||
            (input(index) == '\n') ||
            (input(index) == '\r')
            )
          ) {
          index += 1
        }

        if (input(index) == '"') {
          val (v: String, i) = loadsString(input.substring(index+1))
          index += i + 1
          result.put(k, v)
        } else if (input(index) == '{') {
          index += 1
          val (v: util.HashMap[String, Any], i) = loadsColon(input.substring(index))
          index += i
          result.put(k, v)
        } else if (input(index) == '[') {
          index+=1
          val (v: Array[Object], i) = loadsList(input.substring(index))
          index += i
          result.put(k, v)
        } else if (input(index) == 't') {
          if (input.substring(index, index+4).equals("true")) {
            index += 4
            result.put(k, true)
          } else {
            throw new Exception("Parser Error: Malformed value")
          }
        } else if (input(index) == 'f') {
          if (input.substring(index, index + 5).equals("false")) {
            index += 5
            result.put(k, false)
          } else {
            throw new Exception("Parser Error: Malformed value")
          }
        } else if (input(index) == 'n') {
          if (input.substring(index, index + 4).equals("null")) {
            index += 4
            v = null
          } else {
            throw new Exception("Parser Error: Malformed value")
          }
          result.put(k, v)
        } else if ("-.0123456789".contains(input(index))) {
          val(v: Any, i) = loadsNumber(input.substring(index))
          index += i
          result.put(k, v)
        } else if (" \t\n\r".contains(input(index))) {
          index += 1
        } else {
          throw new Exception("Parser Error: Malformed value")
        }

        while (index < input.length && (
            (input(index) == ' ') ||
            (input(index) == '\t') ||
            (input(index) == '\n') ||
            (input(index) == '\r'))) {
              index+=1
        }

        if (index < input.length) {
          if (input(index) == '}') {
            loop = false
          } else if (input(index) == ',') {
            index += 1
          } else {
            throw new Exception("Parser Error: Terminating bracket or comma is missing")
          }
        } else {
          throw new Exception("Parser Error: Terminating bracket is missing")
        }
      } else if (" \t\n\r".contains(input(index))) {
        index += 1
      } else {
        throw new Exception("Parser Error: There is extra data, either because of a malformed key-type or poor JSON syntax")
      }
    }

    if (input(index) == '}') {
      while((index < input.length) && (
          (input.charAt(index) == ' ' ) ||
          (input.charAt(index) == '\t') ||
          (input.charAt(index) == '\n') ||
          (input.charAt(index) == '\r'))) {
        index = index + 1
      }
      return (result, index + 1)
    }

    throw new Exception("Parser Error: Terminating bracket is missing")
  }

  private def loadsList(input: String): (Array[Object], Integer) = {
    val result: util.ArrayList[Any] = new util.ArrayList[Any]
    var index: Int = 0
    var next: Char = input(index)
    var loop: Boolean = true
    var isNextComma: Boolean = false

    while (loop && (index < input.length)) {
      if (isNextComma) {
        while(
          (index < input.length) && (
            (input(index) == ' ' ) ||
            (input(index) == '\t') ||
            (input(index) == '\n') ||
            (input(index) == '\r'))) {
              index += 1
        }

        if (input(index) != ']') {
          if (input(index) != ',') {
            throw new Exception("Parser Error: Could not find a comma for separating")
          }

          index += 1
          isNextComma = false
        } else {
          loop = false
        }
      } else if(next == ']') {
        loop = false
      } else if (next == '"') {
        val (x, y) = loadsString(input.substring(index + 1))
        result.add(x)
        index += y + 1
        isNextComma = true
      } else if (next == '{') {
        index += 1
        var(x: Object, y: Integer) = loadsColon(input.substring(index))
        result.add(x)
        index += y
        isNextComma = true
      } else if (next == '['){
        index += 1
        val(x: Array[Object], y: Integer) = loadsList(input.substring(index + 1))
        result.add(x)
        index += y + 1
        isNextComma = true
      } else if ("1234567890.-".contains(next)) {
        val (x: Object, y) = loadsNumber(input.substring(index))
        result.add(x)
        index += y
        isNextComma = true
      } else if (" \t\n\r".contains(next)) {
        index += 1
      } else if (next == 'n') {
        if (input.substring(index, index+4).equals("null")) {
          index += 4
          result.add(null)
          isNextComma = true
        } else {
          throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
        }
      } else if (next == 't') {
        if (input.substring(index, index + 4).equals("true")) {
          index += 4
          result.add(true)
        } else {
          throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
        }

        isNextComma = true
      } else if (next == 'f') {
        if (input.substring(index, index + 5).equals("false")) {
          index += 5
          result.add(false)
        } else {
          throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
        }

        isNextComma = true
      } else {
        throw new Exception("Parser Error: There is extra data, either because of an extra comma or poor JSON syntax")
      }
      if(index < input.length){
        next = input(index)
      }
    }
    if (index > input.length - 1) {
      if (input(index - 1) != ']') {
        throw new Exception("Parser Error: List has no closure")
      }
    } else {
      if (input(index) != ']') {
        throw new Exception("Parser Error: List has no closure")
      }
    }
    (result.toArray(), index+1)
  }

  private def loadsNumber(input: String): (Any, Integer) = {
    var index = 0
    if (input(0).equals('-')) {
      index = 1
    }

    while ((index < input.length) && ("0123456789." contains input(index))) {
      index += 1
    }

    val in = new Scanner(input.substring(0, index))

    var result: Any = null

    if (in.hasNextInt) {
      result = in.nextInt
    } else if (in.hasNextDouble) {
      result = in.nextDouble
    } else if (in.hasNextBigInteger) {
      result = in.nextBigInteger
    } else if (in.hasNextFloat) {
      result = in.nextFloat
    } else if (in.hasNextLong) {
      result = in.nextLong
    } else if (in.hasNextShort) {
      result = in.nextShort
    } else {
      if (input.indexOf(".") != -1) {
        if (input.substring(input.indexOf(".")).indexOf(".") != -1) {
          throw new Exception("JSON Parser Error: Multiple decimals found in the number")
        } else {
          throw new Exception("JSON Parser Error: Number could not be parsed")
        }
      } else {
        throw new Exception("JSON Parser Error: Number could not be parsed")
      }
    }

    (result, index)
  }

  def loadsString(input: String): (String, Integer) = {
    var index = 0
    var loop = true

    while ((index < input.length) && loop) {
      if (input(index) == '\\') {
        if ((index+1 < input.length) && (input(index+1) == '"')) {
          index += 2
        } else {
          index += 1
        }
      } else if (input(index) == '"') {
        loop = false
      } else {
        index += 1
      }
    }

    if (input(index) != '"') {
      throw new Exception("Error: String has no closure")
    }

    (input.substring(0, index), index + 1)
  }

  private def removeWhitespace(input: String): String = {
    if (input.isEmpty) {
      return input
    }

    var i = 0

    while (
      (i < input.length) && (
        (input.charAt(i) == ' ' ) ||
        (input.charAt(i) == '\t') ||
        (input.charAt(i) == '\n') ||
        (input.charAt(i) == '\r'))) {
          i = i + 1
    }

    input.substring(i)
  }
}
