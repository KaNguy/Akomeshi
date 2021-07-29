package org.akomeshi
package core.managers

import java.util

object TokenHolder {
  val storage: util.HashMap[String, String] = new util.HashMap[String, String]()
}
