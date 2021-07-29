package org.akomeshi
package core.managers

import json.JSONString
import websocket.AkoWebSocket

import org.akomeshi.core.structures.PayloadModels

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

object Heartbeat {
  val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

  def sendHeartbeat(heartbeat_interval: Int, connection: AkoWebSocket, executor: ScheduledExecutorService = this.executor): Unit = {
    executor.scheduleAtFixedRate(() => {
      connection.send(JSONString.encode(PayloadModels.heartbeatPayload), last = true)
    }, 0, heartbeat_interval, TimeUnit.MILLISECONDS)
  }
}
