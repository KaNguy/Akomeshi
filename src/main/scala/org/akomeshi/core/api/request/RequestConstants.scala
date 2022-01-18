package org.akomeshi
package core.api.request

/**
 * Created by KiyonoKara - 07/22/2021
 * File org.akomeshi.core/api/request/RequestConstants.scala
 */

case object RequestConstants {
  val GET: String = "GET"
  val POST: String = "POST"
  val DELETE: String = "DELETE"
  val PUT: String = "PUT"
  val PATCH: String = "PATCH"

  val requestMethods: Set[String] = Set(GET, POST, DELETE, PUT, PATCH)
}
