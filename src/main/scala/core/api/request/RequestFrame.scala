package org.akomeshi
package core.api.request

/**
 * Created by KaNguy - 07/31/2021
 * File core/api/request/RequestFrame.scala
 */

// Akomeshi
import core.managers.TokenManager
import utility.Constants
import json.JSONString

object RequestFrame {
  /**
   * Makes a POST request on the client's behalf with the needed authorization headers
   * @param url URL
   * @param data Data as a Map
   * @return Output as a String
   */
  def post(url: String, data: Map[Any, Any]): String = {
    Request.request(
      url,
      RequestConstants.POST,
      Map(
        "Authorization" -> s"Bot ${TokenManager.getToken}",
        "Content-Type" -> "application/json",
        "User-Agent" -> Constants.userAgent,
        "Accept" -> "*/*"
      ),
      JSONString.encode(data),
    )
  }

  /**
   * Makes a GET request with the client's needed authorization headers
   * @param url URL
   * @return Output as a String
   */
  def get(url: String): String = {
    Request.request(
      url,
      RequestConstants.GET,
      Map(
        "Authorization" -> s"Bot ${TokenManager.getToken}",
        "Content-Type" -> "application/json",
        "User-Agent" -> Constants.userAgent,
        "Accept" -> "*/*"
      )
    )
  }
}
