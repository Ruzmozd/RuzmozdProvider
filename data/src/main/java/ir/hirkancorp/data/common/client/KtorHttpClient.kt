package ir.hirkancorp.data.common.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import ir.hirkancorp.core.LoggerUtil
import ir.hirkancorp.core.LoggerUtil.logI
import ir.hirkancorp.data.preferences.repository.LocalServiceRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


private const val BASE_URL = "https://dev.ruzmozd.ir/api/"

fun httpClient(localServiceRepository: LocalServiceRepository) = HttpClient(Android) {

    defaultRequest {
        url(BASE_URL)
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                logI(LoggerUtil.HTTP_CLIENT, message)
            }
        }
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        })
    }

    install(Auth) {
        bearer {
            loadTokens {
                val accessToken = localServiceRepository.getAccessToken().orEmpty()
                BearerTokens(
                    accessToken = accessToken,
                    refreshToken = accessToken
                )
            }

            refreshTokens {
                if (this.response.status.value == 401) {
                    var accessToken = localServiceRepository.getAccessToken().orEmpty()
                    val bodyAsText = this.response.bodyAsText()
                    val parseToJsonElement = Json.parseToJsonElement(bodyAsText).jsonObject["data"]
                    val token = parseToJsonElement?.jsonObject?.get("refresh_token")
                    token?.let { jsonElement ->
                        accessToken = jsonElement.jsonPrimitive.content
                    }
                    localServiceRepository.saveAccessToken(accessToken)
                    BearerTokens(
                        accessToken = accessToken,
                        refreshToken = accessToken
                    )
                }
                else null
            }
        }
    }
}