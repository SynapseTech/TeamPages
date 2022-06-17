package dev.synapsetech.teampages.data

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import dev.synapsetech.teampages.config.DatabaseConfigPart
import org.litote.kmongo.KMongo
import xyz.downgoon.snowflake.Snowflake

object Mongo {
    lateinit var mongoClient: MongoClient
    lateinit var database: MongoDatabase

    fun init(dbConfig: DatabaseConfigPart) {
        mongoClient = KMongo.createClient(dbConfig.mongoUri)
        database = mongoClient.getDatabase(dbConfig.mongoDatabase)
    }
}

val snowflake = Snowflake(0, 0)