package ir.hirkancorp.data.common.api_utils

import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import ir.hirkancorp.core.LoggerUtil
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


fun <T> commonRequest(
    httpResponse: suspend ()->HttpResponse,
    errorCodes: List<HttpStatusCode>,
    successActions: List<Pair<HttpStatusCode, suspend (HttpResponse) -> T>>,
):Flow<ApiResult<T>> = flow {
    emit(ApiResult.Loading())
    val response = httpResponse()
    try {
        val isFailure = errorCodes.any { httpStatusCode -> response.status == httpStatusCode }
        if (isFailure) {
            emit(ApiResult.Error(response.status.description))
        } else {
            val actionIndex: Int =
                successActions.map { it.first }
                    .indexOfFirst { httpStatusCode -> response.status == httpStatusCode }
            emit(ApiResult.Success(successActions[actionIndex].second(response)))
        }
    } catch (exception: Exception) {
        LoggerUtil.logE("common-http-request", exception.message ?: "Unknown error!")
        emit(ApiResult.Error(exception.localizedMessage?:"Unknown error!"))
    }
}

fun <T> commonRequest(
    httpResponse: suspend ()->HttpResponse,
    errorCodes: List<HttpStatusCode>,
    successAction: Pair<HttpStatusCode, suspend (HttpResponse) -> T>,
): Flow<ApiResult<T>> = flow {
    emit(ApiResult.Loading())
    val response = httpResponse()
    try {
        val isFailure = errorCodes.any { httpStatusCode ->
            response.status == httpStatusCode
        }
        if (isFailure) {
            emit(ApiResult.Error(response.status.description))
        } else {
            emit(ApiResult.Success(successAction.second(response)))
        }
    } catch (exception: Exception) {
        LoggerUtil.logE("common-http-request", exception.message ?: "Unknown error!")
        emit(ApiResult.Error(exception.localizedMessage?:"Unknown error!"))
    }
}


