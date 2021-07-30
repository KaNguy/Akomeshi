package org.akomeshi
package core.api.request

/**
 * Created by KaNguy - 07/22/2021
 * File core/api/request/Request.scala
 */

// Networking & Web
import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

object Request {
  /**
   * General request function for making requests
   * @param url Provide a valid URL
   * @param method Provide a valid method
   * @param headers Provide valid headers
   * @param data Provide valid data if a writable request is occurring
   * @return Request output as a String
   */
  def request(url: String, method: String = RequestConstants.GET, headers: Iterable[(String, String)] = Iterable.empty[(String, String)], data: String = null): String = {
    val uMethod: String = method.toUpperCase
    val client: HttpClient = HttpClient.newHttpClient()

    val request: HttpRequest.Builder = HttpRequest.newBuilder(URI.create(url))
      .method(
        if (RequestConstants.requestMethods.contains(uMethod)) uMethod else RequestConstants.GET,
        if (uMethod.equals(RequestConstants.GET)) HttpRequest.BodyPublishers.noBody() else HttpRequest.BodyPublishers.ofString(if (data == null) "" else data)
      )
      .version(HttpClient.Version.HTTP_2)

    if (headers.nonEmpty) headers.foreach(k => request.header(k._1, k._2))

    val response: HttpResponse[String] = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
    response.body
  }
}
