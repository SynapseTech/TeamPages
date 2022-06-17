package dev.synapsetech.teampages.plugins

import dev.synapsetech.teampages.config.MainConfig
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import java.net.URI

fun Application.configureHTTP() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)

        allowHeader(HttpHeaders.Authorization)
        allowHeaders { true }

//        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
        allowHost(URI(MainConfig.instance.webUrl).host)
    }
}
