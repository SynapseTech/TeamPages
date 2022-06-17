package dev.synapsetech.teampages.util

/**
 * A base interface for responses that are expected to fail in some way.
 *
 * @author Liz Ainslie
 */
interface FailableResponse {
    val success: Boolean
}