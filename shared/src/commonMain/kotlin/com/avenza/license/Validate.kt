package com.avenza.license

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers

suspend fun validate(request: Request) : Response {

    val client = HttpClient()
    suspend fun register() = client.get(request.url) {
        headers {
            request.headers.forEach {
                append(it.key, it.value)
            }
        }
        url {
            parameters.append("device", request.device)
        }
    }.body<Response>()

    return register()
}

data class Response(val message: String)

interface Request {
    val device: String
    val url: String
    val headers: Map<String, String>
}