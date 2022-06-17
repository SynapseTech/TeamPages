package dev.synapsetech.teampages.plugins

import dev.synapsetech.teampages.routes.userRoutes
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        route("/v1") {
            userRoutes()
        }
    }
}
