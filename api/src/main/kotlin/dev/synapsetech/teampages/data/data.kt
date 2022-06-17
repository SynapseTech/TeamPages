package dev.synapsetech.teampages.data

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import dev.synapsetech.teampages.config.DatabaseConfigPart
import org.litote.kmongo.KMongo
import xyz.downgoon.snowflake.Snowflake

/**
 * Manages the connection to MongoDB.
 *
 * @author Liz Ainslie
 */
object Mongo {
    lateinit var mongoClient: MongoClient
    lateinit var database: MongoDatabase

    /**
     * Initialize and connect to MongoDB.
     *
     * @param dbConfig The database configuration to utilize.
     *
     * @author Liz Ainslie
     */
    fun init(dbConfig: DatabaseConfigPart) {
        mongoClient = KMongo.createClient(dbConfig.mongoUri)
        database = mongoClient.getDatabase(dbConfig.mongoDatabase)
    }
}

/**
 * A singleton instance of the Snowflake ID generator.
 *
 * @author Liz Ainslie
 */
val snowflake = Snowflake(0, 0)