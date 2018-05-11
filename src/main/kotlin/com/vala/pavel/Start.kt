package com.vala.pavel

import com.natpryce.konfig.Configuration
import io.vertx.core.Vertx
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.overriding


object Start {
    @JvmStatic
    fun main(args: Array<String>) {

        val config: Configuration = systemProperties() overriding
                EnvironmentVariables() overriding
                ConfigurationProperties.fromResource("default.properties")

        val vertx = Vertx.vertx()
        val dao = CounterDaoImpl(vertx, config)
        dao.init()

        vertx.deployVerticle(CountServer(dao, config))
    }
}