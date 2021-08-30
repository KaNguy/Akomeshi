package org.akomeshi
package core.api.request

/**
 * Created by KaNguy - 07/31/2021
 * File org.akomeshi.core/api/request/RequestFrame.scala
 */

// Akomeshi
import core.managers.TokenManager
import json.JSONString
import org.akomeshi.utility.Constants

/**
 * A request frame for simplifying the making of the requests.
 * Advisory to not use this outside of the API.
 */
object RequestFrame {
  private val clientHeaders: Map[String, String] = Map(
    "Authorization" -> s"Bot ${TokenManager.getToken}",
    "Content-Type" -> "application/json",
    "User-Agent" -> Constants.userAgent,
    "Accept" -> "*/*"
  )

  /**
   * Makes a POST request on the client's behalf with the needed authorization headers
   * @param url URL
   * @param data Data as a Map
   * @return Output as a String
   */
  def post(url: String, data: Map[Any, Any]): String = {
    Request.request(
      url = url,
      method = RequestConstants.POST,
      headers = clientHeaders,
      data = JSONString.encode(data),
    )
  }

  /**
   * Makes a GET request with the client's needed authorization headers
   * @param url URL
   * @return Output as a String
   */
  def get(url: String): String = {
    Request.request(
      url = url,
      method = RequestConstants.GET,
      headers = clientHeaders
    )
  }

  /**
   * Makes a DELETE rquest with the client's needed authorization headers
   * @param url - URL
   * @return Output as a String
   */
  def delete(url: String): String = {
    Request.request(
      url = url,
      method = RequestConstants.DELETE,
      headers = clientHeaders
    )
  }
}
