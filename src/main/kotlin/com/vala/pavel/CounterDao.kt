package com.vala.pavel

import io.vertx.core.http.HttpServerResponse

interface CounterDao {
    fun get(response: HttpServerResponse)
    fun inc(response: HttpServerResponse)
    fun init()
}