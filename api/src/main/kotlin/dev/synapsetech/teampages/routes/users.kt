package dev.synapsetech.teampages.routes

import dev.synapsetech.teampages.data.models.User
import dev.synapsetech.teampages.data.models.requireUser
import dev.synapsetech.teampages.plugins.genJwt
import dev.synapsetech.teampages.util.FailableResponse
import dev.synapsetech.teampages.util.ResponseError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class CreateAccountRequest(
    val email: String,
    val username: String,
    val password: String,
)

@Serializable
data class CreateAccountResponseSuccess(
    val user: User.Json,
) : FailableResponse {
    override val success = false
}

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class LoginResponseSuccess(
    val token: String,
) : FailableResponse {
    override val success = true
}

@Serializable
data class UpdatePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)

@Serializable
data class MeResponse(
    val user: User.Json,
) : FailableResponse {
    override val success = true
}

@Serializable
data class UpdatePasswordResponse(
    val passwordChanged: Boolean,
    override val success: Boolean,
    val message: String? = null,
) : FailableResponse

fun Route.userRoutes() {
    route("/users") {
        post("createAccount") {
            val request: CreateAccountRequest = call.receive()

            val emailUser = User.findByEmail(request.email)
            if (emailUser != null) {
                call.respond(HttpStatusCode.BadRequest, ResponseError("Email taken"))
                return@post
            }

            val usernameUser = User.findByUsername(request.username)
            if (usernameUser != null) {
                call.respond(HttpStatusCode.BadRequest, ResponseError("Username taken"))
                return@post
            }

            // todo: validate username

            val user = User(
                username = request.username,
                email = request.email,
                passwordHash = BCrypt.hashpw(request.password, User.genSalt())
            )
            user.save()

            call.respond(HttpStatusCode.OK, CreateAccountResponseSuccess(user.toApiJson(true)))
        }

        post("login") {
            val request: LoginRequest = call.receive()

            val emailUser = User.findByEmail(request.email)

            if (emailUser == null) {
                call.respond(HttpStatusCode.NotFound, ResponseError("No user exists with that email."))
                return@post
            }

            val passwordValid = emailUser.checkPassword(request.password)

            if (passwordValid) call.respond(LoginResponseSuccess(genJwt(emailUser._id)))
            else call.respond(ResponseError("Password incorrect"))
        }

        authenticate("auth-jwt") {
            get("/me") {
                val user = requireUser()
                call.respond(MeResponse(user.toApiJson(true)))
            }

            post("/me/updatePassword") {
                val user = requireUser()
                val request: UpdatePasswordRequest = call.receive()

                val oldPasswordValid = user.checkPassword(request.oldPassword)

                if (!oldPasswordValid) {
                    call.respond(HttpStatusCode.BadRequest, UpdatePasswordResponse(
                        passwordChanged = false,
                        success = false,
                        message = "Old password invalid"
                    ))
                    return@post
                }

                user.updatePassword(request.newPassword)

                call.respond(UpdatePasswordResponse(
                    passwordChanged = true,
                    success = true,
                    message = "Password changed"
                ))
            }
        }
    }
}