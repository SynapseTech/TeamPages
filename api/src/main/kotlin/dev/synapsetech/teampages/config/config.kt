package dev.synapsetech.teampages.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class DatabaseConfigPart(
    val mongoUri: String,
    val mongoDatabase: String,
)

@Serializable
data class JwtConfigPart(
    val secret: String,
    val audience: String,
    val realm: String,
    val domain: String,
)

@Serializable
data class MainConfig(
    val webUrl: String,
    val port: Int,
    val database: DatabaseConfigPart,
    val jwt: JwtConfigPart,
) {
    companion object {
        lateinit var INSTANCE: MainConfig
        private val json = Json { ignoreUnknownKeys = true }

        fun loadFile(file: File) {
            INSTANCE = json.decodeFromString(file.readText())
        }
    }
}