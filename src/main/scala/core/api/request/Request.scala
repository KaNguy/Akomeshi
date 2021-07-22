package org.akomeshi
package core.api.request

/**
 * Created by KaNguy - 07/22/2021
 * File core/Request.scala
 */

// Networking & Web
import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

object Request {
  def request(url: String, method: String = RequestConstants.GET, headers: Iterable[(String, String)], data: String = null): String = {
    val client: HttpClient = HttpClient.newHttpClient()

    val request: HttpRequest.Builder = HttpRequest.newBuilder(URI.create(url))
      .method(
        if (RequestConstants.requestMethods.contains(method.toUpperCase)) method.toUpperCase else RequestConstants.GET,
        if (method.toUpperCase.equals(RequestConstants.GET)) HttpRequest.BodyPublishers.noBody() else HttpRequest.BodyPublishers.ofString(if (data == null) "" else data)
      )
      .version(HttpClient.Version.HTTP_2)

    if (headers.nonEmpty) headers.foreach(k => request.header(k._1, k._2))

    val response: HttpResponse[String] = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
    response.body
  }
}
