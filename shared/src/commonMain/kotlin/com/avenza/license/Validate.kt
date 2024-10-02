package com.avenza.license

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

suspend fun validate(request: Request) : Response? {

    var parsedResponse: Response? = null
    try {
        val client = HttpClient()
        val response = client.get(request.url) {
            headers {
                request.headers.forEach {
                    append(it.key, it.value)
                }
            }
            url {
                parameters.append("device", request.device)
            }
        }
        if (response.status.value in 200..299) {
            parsedResponse = Json.decodeFromString(response.body())
        }
    } catch (e : Exception) {
        throw (e)
    }

    return parsedResponse
}

@Serializable
data class Response(val message: String)

interface Request {
    val device: String
    val url: String
    val headers: Map<String, String>
}