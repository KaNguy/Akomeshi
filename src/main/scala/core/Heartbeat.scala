package org.akomeshi
package core

/**
 * Created by KaNguy - 07/08/2021
 * File core/Heartbeat.scala
 */

// Akomeshi
import websocket.AkoWebSocket

// Utility
import java.util.concurrent.ScheduledExecutorService

// JSON
import json.JSONString

object Heartbeat {
  def sendHeartbeat(heartbeat_interval: Int, executor: ScheduledExecutorService, connection: AkoWebSocket): Unit = {
    connection.send(JSONString.encode(PayloadModels.heartbeatPayload), last = true)
  }
}
