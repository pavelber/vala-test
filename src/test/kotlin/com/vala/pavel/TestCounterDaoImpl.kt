package com.vala.pavel

import io.vertx.core.http.HttpServerResponse

class TestCounterDaoImpl: CounterDao {
    private var c = 0
    override fun get(response: HttpServerResponse) {
        return response.end(c.toString())
    }

    override fun inc(response: HttpServerResponse) {
        c++
    }

    override fun init() {
    }
}