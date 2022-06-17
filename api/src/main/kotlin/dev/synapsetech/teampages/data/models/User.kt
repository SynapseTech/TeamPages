package dev.synapsetech.teampages.data.models

import dev.synapsetech.teampages.data.Mongo
import dev.synapsetech.teampages.data.snowflake
import kotlinx.serialization.Serializable
import org.litote.kmongo.*
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class User(
    val _id: Long = snowflake.nextId(),
    val username: String,
    val email: String,
    var passwordHash: String,
    var displayName: String = "",
) {
    fun checkPassword(password: String): Boolean = BCrypt.checkpw(password, passwordHash)
    fun updatePassword(newPassword: String) {
        passwordHash = BCrypt.hashpw(newPassword, genSalt())
    }

    fun save() {
        val col = getCollection()
        val possibleUser = findById(_id)
        if (possibleUser == null) col.insertOne(this)
        else col.replaceOneById(_id, this)
    }

    fun delete() {
        getCollection().deleteOneById(_id)
    }

    fun toApiJson(privateFields: Boolean = false) =
        if (privateFields) Json(_id, username, displayName, email)
        else Json(_id, username, displayName)

    @Serializable data class Json(
        val id: Long,
        val username: String,
        var displayName: String,
        val email: String? = null,
    )

    @Serializable data class Patch(
        val displayName: String?,
        val email: String?
    )

    companion object {
        private const val COLLECTION_NAME = "users"

        fun getCollection() = Mongo.database.getCollection<User>(COLLECTION_NAME)
        fun findById(id: Long) = getCollection().findOneById(id)
        fun findByEmail(email: String) = getCollection().findOne(User::email eq email)

        fun genSalt(): String = BCrypt.gensalt(12)
    }
}