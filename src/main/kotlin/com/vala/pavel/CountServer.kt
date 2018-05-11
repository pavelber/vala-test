package com.vala.pavel

import com.natpryce.konfig.Configuration
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler


class CountServer(val dao:CounterDao, val config: Configuration): AbstractVerticle() { // dependency injection for poor people

    override fun start() {
        val router = Router.router(vertx)!!

        router.route().handler(BodyHandler.create())
        router.get("/click").handler(this::handleGetCount)
        router.post("/click").handler(this::handlePostCount)
        router.route("/*").handler(StaticHandler.create(config[server.static_html]))

        vertx.createHttpServer().requestHandler(router::accept).listen(config[server.port].toInt())
    }

    private fun handleGetCount(routingContext: RoutingContext) {
        val response = routingContext.response().putHeader("content-type", "text/plain").
                putHeader("Access-Control-Allow-Origin","*")
        dao.get(response)
    }

    private fun handlePostCount(routingContext: RoutingContext) {
        dao.inc(routingContext.response())
        routingContext.response().
                putHeader("Access-Control-Allow-Origin","*").end()
    }
}


