package com.vala.pavel

import com.natpryce.konfig.ConfigurationMap
import com.natpryce.konfig.Location
import io.vertx.core.Vertx
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.net.ServerSocket


@RunWith(VertxUnitRunner::class)
class CountServerTest {
    private val vertx: Vertx = Vertx.vertx()
    private var port: Int = 0

    /**
     * Before executing our test, let's deploy our verticle.
     *
     *
     * This method instantiates a new Vertx and deploy the verticle. Then, it waits in the verticle has successfully
     * completed its start sequence (thanks to `context.asyncAssertSuccess`).
     *
     * @param context the test context.
     */
    @Before
    @Throws(IOException::class)
    fun setUp(context: TestContext) {
        val socket = ServerSocket(0)
        port = socket.localPort
        socket.close()
        val map = mapOf("server.port" to port.toString(),"server.static-html" to "")
        val config = ConfigurationMap(map, Location("from-map"))


        vertx.deployVerticle(CountServer(TestCounterDaoImpl(), config))
    }

    /**
     * This method, called after our test, just cleanup everything by closing the vert.x instance
     *
     * @param context the test context
     */
    @After
    fun tearDown(context: TestContext) {
        vertx.close(context.asyncAssertSuccess())
    }

    /**
     * Let's ensure that our application behaves correctly.
     *
     * @param context the test context
     */
    @Test
    fun testCounter(context: TestContext) {
        val async = context.async()

        val httpClient = vertx.createHttpClient()

        httpClient.getNow(port, "localhost", "/click") { response ->
            context.assertEquals(200, response.statusCode())
            response.handler { body ->
                context.assertTrue(body.toString() == "0")
                async.complete()
            }
        }

        httpClient.post(port, "localhost", "/click") { response ->
            context.assertEquals(200, response.statusCode())
            httpClient.getNow(port, "localhost", "/click") { response ->
                response.handler { body ->
                    context.assertTrue(body.toString() == "1")
                    async.complete()
                }
            }
        }


    }
}