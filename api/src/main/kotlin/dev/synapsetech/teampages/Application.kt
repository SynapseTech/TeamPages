package dev.synapsetech.teampages

import dev.synapsetech.teampages.config.MainConfig
import dev.synapsetech.teampages.data.Mongo
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.synapsetech.teampages.plugins.*
import java.io.File

fun main(args: Array<String>) {
    val configFile = if (args.isEmpty()) {
        println("No config specified, defaulting to ./config.json")
        "./config.json"
    } else args[0]
    MainConfig.loadFile(File(configFile))
    Mongo.init(MainConfig.instance.database)

    embeddedServer(Netty, port = MainConfig.instance.port, host = "0.0.0.0") {
        configureHTTP()
        configureRouting()
        configureSecurity()
        configureSerialization()
    }.start(wait = true)
}
