package dev.synapsetech.teampages.util

import kotlinx.serialization.Serializable

/**
 * A base implementation of FailableResponse that indicates an error and offers
 * an error message to the client.
 *
 * @see FailableResponse
 *
 * @author Liz Ainslie
 */
@Serializable
open class ResponseError(
    override val message: String?,
) : FailableResponse {
    override val success: Boolean = false
}