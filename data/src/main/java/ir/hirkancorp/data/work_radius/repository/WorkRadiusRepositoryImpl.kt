package ir.hirkancorp.data.work_radius.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.EmptyDataHttpResponseModel
import ir.hirkancorp.data.work_radius.remote.WorkRadiusRemote
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.work_radius.repository.WorkRadiusRepository
import kotlinx.coroutines.flow.Flow

class WorkRadiusRepositoryImpl(private val client: WorkRadiusRemote): WorkRadiusRepository {

    override suspend fun updateWorkRadius(radius: Int): Flow<ApiResult<String>> = commonRequest(
        httpResponse = { client.updateWorkradius(radius) },
        errorCodes = listOf(Unauthorized, Forbidden, UnprocessableEntity),
        successAction = Pair(OK) { response ->
            response.body<EmptyDataHttpResponseModel>().statusMessage
        }

    )
}