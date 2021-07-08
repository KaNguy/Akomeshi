package org.akomeshi
package core

/**
 * Created by KaNguy - 07/08/2021
 * File core/Heartbeat.scala
 */

// Akomeshi
import websocket.AkoWebSocket

// Utility
import java.util.concurrent.{ScheduledExecutorService, TimeUnit, Executors}

// JSON
import json.JSONString

object Heartbeat {
  def sendHeartbeat(heartbeat_interval: Int, executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1), connection: AkoWebSocket): Unit = {
    executor.scheduleAtFixedRate(() => {
      connection.send(JSONString.encode(PayloadModels.heartbeatPayload), last = true)
    }, 0, heartbeat_interval, TimeUnit.MILLISECONDS)
  }
}
