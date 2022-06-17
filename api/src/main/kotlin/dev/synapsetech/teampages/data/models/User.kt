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

/**
 * A user object. This class represents a single user, and its companion holds
 * lookup methods.
 *
 * @author Liz Ainslie
 */
@Serializable
data class User(
    val _id: Long = snowflake.nextId(),
    var username: String,
    var email: String,
    var passwordHash: String,
    var admin: Boolean = false,
    var displayName: String = "",
) {
    /**
     * Check a plaintext password against this user's password hash.
     *
     * @author Liz Ainslie
     */
    fun checkPassword(password: String): Boolean = BCrypt.checkpw(password, passwordHash)

    /**
     * Update this user's password. This function internally hashes its input.
     *
     * @author Liz Ainslie
     */
    fun updatePassword(newPassword: String) {
        passwordHash = BCrypt.hashpw(newPassword, genSalt())
    }

    /**
     * Save this user to the database. If the user already exists, it is
     * replaced (inefficient), if not it is created.
     *
     * @author Liz Ainslie
     */
    fun save() {
        val col = getCollection()
        val possibleUser = findById(_id)
        if (possibleUser == null) col.insertOne(this)
        else col.replaceOneById(_id, this)
    }

    /**
     * Delete this user.
     *
     * @author Liz Ainslie
     */
    fun delete() {
        getCollection().deleteOneById(_id)
    }

    /**
     * Get an API-ready JSON object of this user.
     *
     * @param privateFields A boolean specifying whether to include private
     * fields in the JSON.
     *
     * @author Liz Ainslie
     */
    fun toApiJson(privateFields: Boolean = false) =
        if (privateFields) Json(_id, username, displayName, admin, email)
        else Json(_id, username, displayName, admin)

    /**
     * An API-ready JSON object of this user, sensitive fields omitted.
     *
     * @author Liz Ainslie
     */
    @Serializable data class Json(
        val id: Long,
        val username: String,
        val displayName: String,
        val admin: Boolean,
        val email: String? = null,
    )

    /**
     * A user patch object. Password field omitted as there will be a separate
     * route for that.
     *
     * @author Liz Ainslie
     */
    @Serializable data class Patch(
        val displayName: String?,
        val email: String?,
        val username: String?,
        val admin: Boolean?
    )

    companion object {
        private const val COLLECTION_NAME = "users"

        /**
         * Get the users collection.
         *
         * @author Liz Ainslie
         */
        fun getCollection() = Mongo.database.getCollection<User>(COLLECTION_NAME)

        /**
         * Find a user by the _id property.
         *
         * @author Liz Ainslie
         */
        fun findById(id: Long) = getCollection().findOneById(id)

        /**
         * Find a user by the email property.
         *
         * @author Liz Ainslie
         */
        fun findByEmail(email: String) = getCollection().findOne(User::email eq email)

        /**
         * Find a user by the username property.
         *
         * @author Liz Ainslie
         */
        fun findByUsername(username: String) = getCollection().findOne(User::username eq username)

        /**
         * Generate a BCrypt salt for hashing user passwords.
         *
         * @author Liz Ainslie
         */
        fun genSalt(): String = BCrypt.gensalt(12)
    }
}

/**
 * Get The currently authorized user, or return null if not authorized or unable
 * to fetch the user.
 *
 * @author Liz Ainslie
 */
fun PipelineContext<Unit, ApplicationCall>.getUser(): User? {
    val principal = call.principal<JWTPrincipal>()
    val userId = principal!!.payload.getClaim("userId").asLong()
    return User.findById(userId)
}