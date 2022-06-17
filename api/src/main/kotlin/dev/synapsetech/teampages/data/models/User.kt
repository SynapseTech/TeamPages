package dev.synapsetech.teampages.data.models

import dev.synapsetech.teampages.data.Mongo
import dev.synapsetech.teampages.data.snowflake
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable
import org.litote.kmongo.*
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class User(
    val _id: Long = snowflake.nextId(),
    var username: String,
    var email: String,
    var passwordHash: String,
    var admin: Boolean = false,
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
        if (privateFields) Json(_id, username, displayName, admin, email)
        else Json(_id, username, displayName, admin)

    @Serializable data class Json(
        val id: Long,
        val username: String,
        val displayName: String,
        val admin: Boolean,
        val email: String? = null,
    )

    @Serializable data class Patch(
        val displayName: String?,
        val email: String?,
        val username: String?,
        val admin: Boolean?
    )

    companion object {
        private const val COLLECTION_NAME = "users"

        fun getCollection() = Mongo.database.getCollection<User>(COLLECTION_NAME)
        fun findById(id: Long) = getCollection().findOneById(id)
        fun findByEmail(email: String) = getCollection().findOne(User::email eq email)
        fun findByUsername(username: String) = getCollection().findOne(User::username eq username)

        fun genSalt(): String = BCrypt.gensalt(12)
    }
}

fun PipelineContext<Unit, ApplicationCall>.getUser(): User? {
    val principal = call.principal<JWTPrincipal>()
    val userId = principal!!.payload.getClaim("userId").asLong()
    return User.findById(userId)
}