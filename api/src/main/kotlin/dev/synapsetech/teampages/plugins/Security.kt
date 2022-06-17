package dev.synapsetech.teampages.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import dev.synapsetech.teampages.config.MainConfig
import io.ktor.server.application.*
import java.util.*

fun genJwt(userId: Long): String = JWT.create()
    .withAudience(MainConfig.instance.jwt.audience)
    .withIssuer(MainConfig.instance.jwt.domain)
    .withClaim("userId", userId)
    .withExpiresAt(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // a week
    .sign(Algorithm.HMAC256(MainConfig.instance.jwt.secret))

val jwtVerifier: JWTVerifier = JWT
    .require(Algorithm.HMAC256(MainConfig.instance.jwt.secret))
    .withAudience(MainConfig.instance.jwt.audience)
    .withIssuer(MainConfig.instance.jwt.domain)
    .build()

fun Application.configureSecurity() {

    authentication {
        jwt {
            val jwtAudience = MainConfig.instance.jwt.audience
            realm = MainConfig.instance.jwt.realm
            verifier(jwtVerifier)
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience) && credential.payload.getClaim("userId").asLong() != null)
                    JWTPrincipal(credential.payload)
                else null
            }
        }
    }
}
