package dev.synapsetech.teampages.util

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val message: String,
) : FailableResponse {
    override val success: Boolean = false
}