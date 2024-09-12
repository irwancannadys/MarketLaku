package org.example.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.core.local.ValuesStoreManager


class NetworkClient(
    private val baseUrl: String,
    private val valuesStoreManager: ValuesStoreManager
) {
    private val ktorClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }

    suspend fun get(path: String, parameter: Map<String, String>? = null): HttpResponse {
        return ktorClient.get(baseUrl) {
            url {
                appendPathSegments(path)
                parameter?.forEach { param ->
                    parameters.append(param.key, param.value)
                }
                bearerAuth(valuesStoreManager.token)
            }
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun post(
        path: String,
        parameter: Map<String, String>? = null,
        body: Any? = null
    ): HttpResponse {
        return ktorClient.post(baseUrl) {
            url {
                appendPathSegments(path)
                parameter?.forEach { param ->
                    parameters.append(param.key, param.value)
                }
                bearerAuth(valuesStoreManager.token)
            }
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }
}