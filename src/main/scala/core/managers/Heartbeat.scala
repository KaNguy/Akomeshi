package org.akomeshi
package core.managers

/**
 * Created by KaNguy - 07/08/2021
 * File core/managers/Heartbeat.scala
 */

// Akomeshi
import json.JSONString
import websocket.AkoWebSocket
import core.structures.PayloadModels

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

object Heartbeat {
  val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

  def sendHeartbeat(heartbeat_interval: Int, connection: AkoWebSocket, executor: ScheduledExecutorService = this.executor): Unit = {
    executor.scheduleAtFixedRate(() => {
      connection.send(JSONString.encode(PayloadModels.heartbeatPayload), last = true)
    }, 0, heartbeat_interval, TimeUnit.MILLISECONDS)
  }
}
