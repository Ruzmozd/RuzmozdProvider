package ir.hirkancorp.data.common.api_utils

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

suspend fun HttpResponse.statusMessage(): String? {
    val element = Json.parseToJsonElement(bodyAsText()).jsonObject["status_message"]
    return element?.jsonPrimitive?.content
}