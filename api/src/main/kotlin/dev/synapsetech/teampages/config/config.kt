package dev.synapsetech.teampages.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Configuration options specific to MongoDB.
 *
 * @author Liz Ainslie
 */
@Serializable
data class DatabaseConfigPart(
    val mongoUri: String,
    val mongoDatabase: String,
)

/**
 * Configuration options specific to JWT creation and validation.
 *
 * @author Liz Ainslie
 */
@Serializable
data class JwtConfigPart(
    val secret: String,
    val audience: String,
    val realm: String,
    val domain: String,
)

/**
 * The main application configuration object.
 *
 * @author Liz Ainslie
 */
@Serializable
data class MainConfig(
    val webUrl: String,
    val port: Int,
    val database: DatabaseConfigPart,
    val jwt: JwtConfigPart,
) {
    companion object {
        lateinit var instance: MainConfig
        private val json = Json { ignoreUnknownKeys = true }

        /**
         * Load the application configuration from a file.
         *
         * @param file The file to load configuration data from.
         *
         * @author Liz Ainslie
         */
        fun loadFile(file: File) {
            instance = json.decodeFromString(file.readText())
        }
    }
}