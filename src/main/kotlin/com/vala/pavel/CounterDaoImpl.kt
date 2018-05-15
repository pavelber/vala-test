package com.vala.pavel

import com.natpryce.konfig.Configuration
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.kotlin.core.json.get
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

class CounterDaoImpl(vertx: Vertx, config: Configuration) : CounterDao {
    var dbconfig = json {
        obj(
                "url" to config[db.url],
                "driver_class" to "org.hsqldb.jdbcDriver",
                "max_pool_size" to 30
        )
    }

    var client = JDBCClient.createNonShared(vertx, dbconfig)

    override fun get(response: HttpServerResponse) {
        client.getConnection({ res ->
            if (res.succeeded()) {

                val connection = res.result()

                connection.query("SELECT counter FROM counter", { res2 ->
                    if (res2.succeeded()) {

                        val counter = res2.result().results[0].get<Int>(0)
                        response.end(counter.toString())
                    } else
                        response.statusCode = 500
                    connection.close()
                })
            } else {
                response.statusCode = 500
            }
        })
    }

    override fun inc(response: HttpServerResponse) {
        client.getConnection({ res ->
            if (res.succeeded()) {

                val connection = res.result()

                connection.execute("UPDATE counter SET counter = counter + 1", { res2 ->
                    if (res2.succeeded()) {
                    } else
                        response.statusCode = 500
                })
                connection.close()
            } else {
                response.statusCode = 500
            }
        })
    }

    override fun init() {
        client.getConnection({ res ->
            if (res.succeeded()) {

                val connection = res.result()

                connection.execute("CREATE TABLE COUNTER (COUNTER INT)", { res2 ->
                    if (res2.succeeded()) {
                        connection.execute("INSERT INTO COUNTER VALUES (0)", { res3 ->
                            if (res3.succeeded()) {
                                println("ok")
                            } else throw Error("Can't init db")
                        })
                    } else {} // DB already exists
                })
                connection.close()
            } else {
               throw Error("Can't init db")
            }
        })
    }
}